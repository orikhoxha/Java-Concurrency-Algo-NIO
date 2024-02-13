package javarealworldchallenges.threads.http.ProxyProject;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.*;
import java.net.*;

public class RequestHandler implements Runnable{


    private Socket clientSocket;

    BufferedReader proxyToClientBr;

    BufferedWriter proxyToClientBw;

    private Thread httpsClientToServer;



    public RequestHandler(Socket socket) {
        this.clientSocket = socket;
        try {
            // Try reading/writing for 2 sec max, otherwise throws exception, socket still valid
            this.clientSocket.setSoTimeout(2000);
            proxyToClientBr = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            proxyToClientBw = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    @Override
    public void run() {
        String requestString;

        try {
            requestString = proxyToClientBr.readLine();
        } catch (IOException ex) {
            ex.printStackTrace();
            return;
        }

        System.out.println("Request received " + requestString);

        String request = requestString.substring(0, requestString.indexOf(' '));

        String urlString = requestString.substring(requestString.indexOf(' ') + 1);
        urlString = urlString.substring(0, urlString.indexOf(' '));

        if(!urlString.substring(0,4).equals("http")){
            String temp = "http://";
            urlString = temp + urlString;
        }

        // Check if site is blocked
        if(Proxy.isBlocked(urlString)){
            System.out.println("Blocked site requested : " + urlString);
            blockedSiteRequested();
            return;
        }

        // Check request type
        if(request.equals("CONNECT")){
            System.out.println("HTTPS Request for : " + urlString + "\n");
            handleHTTPSRequest(urlString);
        } else {
            File file;
            if ((file = Proxy.getCachedPage(urlString)) != null) {
                System.out.println("Cached Copy found for : " + urlString + "\n");
                sendCachedPageToClient(file);
            } else {
                System.out.println("HTTP GET for : " + urlString + "\n");
                sendNonCachedToClient(urlString);
            }
        }

    }

    private void sendNonCachedToClient(String urlString) {
        try {

            int fileExtensionIndex = urlString.lastIndexOf(".");
            String fileExtension;

            fileExtension = urlString.substring(fileExtensionIndex, urlString.length());

            String fileName = urlString.substring(0, fileExtensionIndex);

            fileName = fileName.substring(fileName.indexOf('.') + 1);

            fileName = fileName.replace("/", "__");
            fileName = fileName.replace('.','_');

            // Trailing / result in index.html of that directory being fetched
            if(fileExtension.contains("/")){
                fileExtension = fileExtension.replace("/", "__");
                fileExtension = fileExtension.replace('.','_');
                fileExtension += ".html";
            }

            fileName = fileName + fileExtension;

            // Attempt to create File to cache to
            boolean caching = true;
            File fileToCache = null;
            BufferedWriter fileToCacheBW = null;

            try{
                // Create File to cache
                fileToCache = new File("cached/" + fileName);

                if(!fileToCache.exists()){
                    fileToCache.createNewFile();
                }

                // Create Buffered output stream to write to cached copy of file
                fileToCacheBW = new BufferedWriter(new FileWriter(fileToCache));

                // Check if file is an image
                if((fileExtension.contains(".png")) || fileExtension.contains(".jpg") ||
                        fileExtension.contains(".jpeg") || fileExtension.contains(".gif")){
                    // Create the URL
                    URL remoteURL = new URL(urlString);
                    BufferedImage image = ImageIO.read(remoteURL);

                    if(image != null) {
                        // Cache the image to disk
                        ImageIO.write(image, fileExtension.substring(1), fileToCache);

                        // Send response code to client
                        String line = "HTTP/1.0 200 OK\n" +
                                "Proxy-agent: ProxyServer/1.0\n" +
                                "\r\n";
                        proxyToClientBw.write(line);
                        proxyToClientBw.flush();

                        // Send them the image data
                        ImageIO.write(image, fileExtension.substring(1), clientSocket.getOutputStream());

                        // No image received from remote server
                    } else {
                        System.out.println("Sending 404 to client as image wasn't received from server"
                                + fileName);
                        String error = "HTTP/1.0 404 NOT FOUND\n" +
                                "Proxy-agent: ProxyServer/1.0\n" +
                                "\r\n";
                        proxyToClientBw.write(error);
                        proxyToClientBw.flush();
                        return;
                    }
                } // File is a text file
                else {

                    // Create the URL
                    URL remoteURL = new URL(urlString);
                    // Create a connection to remote server
                    HttpURLConnection proxyToServerCon = (HttpURLConnection)remoteURL.openConnection();
                    proxyToServerCon.setRequestProperty("Content-Type",
                            "application/x-www-form-urlencoded");
                    proxyToServerCon.setRequestProperty("Content-Language", "en-US");
                    proxyToServerCon.setUseCaches(false);
                    proxyToServerCon.setDoOutput(true);

                    // Create Buffered Reader from remote Server
                    BufferedReader proxyToServerBR = new BufferedReader(new InputStreamReader(proxyToServerCon.getInputStream()));


                    // Send success code to client
                    String line = "HTTP/1.0 200 OK\n" +
                            "Proxy-agent: ProxyServer/1.0\n" +
                            "\r\n";
                    proxyToClientBw.write(line);


                    // Read from input stream between proxy and remote server
                    while((line = proxyToServerBR.readLine()) != null){
                        // Send on data to client
                        proxyToClientBw.write(line);

                        // Write to our cached copy of the file
                        if(caching){
                            fileToCacheBW.write(line);
                        }
                    }

                    // Ensure all data is sent by this point
                    proxyToClientBw.flush();

                    // Close Down Resources
                    if(proxyToServerBR != null){
                        proxyToServerBR.close();
                    }
                }

                if(caching){
                    // Ensure data written and add to our cached hash maps
                    fileToCacheBW.flush();
                    Proxy.addCachedPage(urlString, fileToCache);
                }

                // Close down resources
                if(fileToCacheBW != null){
                    fileToCacheBW.close();
                }

                if(proxyToClientBw != null){
                    proxyToClientBw.close();
                }

            }
            catch (IOException e){
                System.out.println("Couldn't cache: " + fileName);
                caching = false;
                e.printStackTrace();
            } catch (NullPointerException e) {
                System.out.println("NPE opening file");
            }


        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    private void sendCachedPageToClient(File cachedFile) {
        try {
            String fileExtension = cachedFile.getName().substring(cachedFile.getName().lastIndexOf("."));

            String response;
            if (fileExtension.contains(".png") || fileExtension.contains("jpg") || fileExtension.contains("jpeg") ||
            fileExtension.contains(".gif")) {
                BufferedImage image = ImageIO.read(cachedFile);

                if (image == null) {
                    System.out.println("Image " + cachedFile.getName() + " was null");
                    response = "HTTP/1.0 404 NOT FOUND \n" +
                            "Proxy-agent: ProxyServer/1.0\n" +
                            "\r\n";
                    proxyToClientBw.write(response);
                    proxyToClientBw.flush();
                } else {
                    response = "HTTP/1.0 200 OK\n" +
                            "Proxy-agent: ProxyServer/1.0\n" +
                            "\r\n";
                    proxyToClientBw.write(response);
                    proxyToClientBw.flush();
                    ImageIO.write(image, fileExtension.substring(1), clientSocket.getOutputStream());
                }
            } else {
                BufferedReader cachedFileBufferedReader = new BufferedReader(new InputStreamReader(new FileInputStream(cachedFile)));
                response = "HTTP/1.0 200 OK\n" +
                        "Proxy-agent: ProxyServer/1.0\n" +
                        "\r\n";
                proxyToClientBw.write(response);
                proxyToClientBw.flush();

                String line;
                while ((line = cachedFileBufferedReader.readLine()) != null) {
                    proxyToClientBw.write(line);
                }
                proxyToClientBw.flush();

                if (cachedFileBufferedReader != null) {
                    cachedFileBufferedReader.close();
                }
            }
            // Close Down Resources
            if(proxyToClientBw != null){
                proxyToClientBw.close();
            }


        } catch (IOException e) {
            System.out.println("Error Sending Cached file to client");
            e.printStackTrace();
        }
    }


    private void handleHTTPSRequest(String urlString) {
        String url = urlString.substring(7);
        String[] pieces = url.split(":");
        url = pieces[0];
        int port = Integer.parseInt(pieces[1]);

        try {
            for (int i = 0; i < 5; i++) {
                proxyToClientBr.readLine();
            }
            InetAddress address = InetAddress.getByName(url);

            Socket proxyToServerSocket = new Socket(address, port);
            proxyToServerSocket.setSoTimeout(5000);

            String line = "HTTP/1.0 200 Connection established\r\n" +
                    "Proxy-Agent: ProxyServer/1.0\r\n" +
                    "\r\n";
            proxyToClientBw.write(line);
            proxyToClientBw.flush();

            // Client and Remote will both start sending data to proxy at this point
            // Proxy needs to asynchronously read data from each party and send it to the other party


            //Create a Buffered Writer betwen proxy and remote
            BufferedWriter proxyToServerBW = new BufferedWriter(new OutputStreamWriter(proxyToServerSocket.getOutputStream()));

            // Create Buffered Reader from proxy and remote
            BufferedReader proxyToServerBR = new BufferedReader(new InputStreamReader(proxyToServerSocket.getInputStream()));

            ClientToServerHttpsTransmit clientToServerHttpsTransmit = new ClientToServerHttpsTransmit(
                    clientSocket.getInputStream(), proxyToServerSocket.getOutputStream());

            httpsClientToServer = new Thread(clientToServerHttpsTransmit);
            httpsClientToServer.start();

            try {
                byte[] buffer = new byte[4096];
                int read;
                do {
                    read = proxyToServerSocket.getInputStream().read(buffer);
                    if (read > 0) {
                        clientSocket.getOutputStream().write(buffer, 0, read);
                        if (proxyToServerSocket.getInputStream().available() < 1) {
                            clientSocket.getOutputStream().flush();
                        }
                    }
                } while (read >= 0);

            } catch (SocketTimeoutException e) {
            } catch (IOException ex) {
                ex.printStackTrace();
            }

            if(proxyToServerSocket != null){
                proxyToServerSocket.close();
            }

            if(proxyToServerBR != null){
                proxyToServerBR.close();
            }

            if(proxyToServerBW != null){
                proxyToServerBW.close();
            }

            if(proxyToClientBw != null){
                proxyToClientBw.close();
            }

        } catch (SocketTimeoutException e) {
            String line = "HTTP/1.0 504 Timeout Occured after 10s\n" +
                    "User-Agent: ProxyServer/1.0\n" +
                    "\r\n";
            try {
                proxyToClientBw.write(line);
                proxyToClientBw.flush();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    static class ClientToServerHttpsTransmit implements Runnable {

        InputStream proxyToClientIS;
        OutputStream proxyToServerOS;

        public ClientToServerHttpsTransmit(InputStream proxyToClientIS, OutputStream proxyToServerOS) {
            this.proxyToClientIS = proxyToClientIS;
            this.proxyToServerOS = proxyToServerOS;
        }

        @Override
        public void run() {
            try {
                // Read byte by byte from client and send directly to server
                byte[] buffer = new byte[4096];
                int read;
                do {
                    read = proxyToClientIS.read(buffer);
                    if (read > 0) {
                        proxyToServerOS.write(buffer, 0, read);
                        if (proxyToClientIS.available() < 1) {
                            proxyToServerOS.flush();
                        }
                    }
                } while (read >= 0);
            }
            catch (SocketTimeoutException ste) {
                // TODO: handle exception
            }
            catch (IOException e) {
                System.out.println("Proxy to client HTTPS read timed out");
                e.printStackTrace();
            }
        }
    }

    private void blockedSiteRequested() {
        try {
            BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(clientSocket.getOutputStream()));
            String line = "HTTP/1.0 403 Access Forbidden \n" +
                    "User-Agent: ProxyServer/1.0\n" +
                    "\r\n";
            bufferedWriter.write(line);
            bufferedWriter.flush();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
}
