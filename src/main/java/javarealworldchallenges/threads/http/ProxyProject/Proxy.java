package javarealworldchallenges.threads.http.ProxyProject;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;
import java.net.SocketTimeoutException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;


/*
*** https://github.com/stefano-lupo/Java-Proxy-Server/blob/master/src/Proxy.java
 */
public class Proxy  implements Runnable {


    private ServerSocket serverSocket;

    private volatile boolean running = true;


    //cached items key:url, file of the url
    static HashMap<String, File> cache;

    // blocked sites key: url, value url
    static HashMap<String, String> blockedSites;
    
    // Currently serving threads
    static ArrayList<Thread> servicingThreads;


    public Proxy(int port) {

        cache = new HashMap<>();
        blockedSites = new HashMap<>();
        servicingThreads = new ArrayList<>();

        new Thread(this).start();

        try {
            File cachedSites = new File("cachedSites.txt");

            if (!cachedSites.exists()) {
                System.out.println("No cached sites found - creating bew file");
                cachedSites.createNewFile();
            } else {
                FileInputStream fileInputStream = new FileInputStream(cachedSites);
                ObjectInputStream objectInputStream = new ObjectInputStream(fileInputStream);
                cache = (HashMap<String, File>) objectInputStream.readObject();
                fileInputStream.close();
                objectInputStream.close();
            }

            File blockedSitesTxtFile = new File("blockedSites.txt");
            if (!blockedSitesTxtFile.exists()) {
                System.out.println("No blocked sites found - creating new file");
                blockedSitesTxtFile.createNewFile();
            } else {
                FileInputStream fileInputStream = new FileInputStream(cachedSites);
                ObjectInputStream objectInputStream = new ObjectInputStream(fileInputStream);
                blockedSites = (HashMap<String, String>) objectInputStream.readObject();
                fileInputStream.close();
                objectInputStream.close();
            }
        } catch (IOException ex) {
            ex.printStackTrace();
        } catch (ClassNotFoundException ex) {
            ex.printStackTrace();
        }

        try {
            serverSocket = new ServerSocket(port);
            System.out.println("Waiting for client on port " + serverSocket.getLocalPort());
            running = true;
        } catch (SocketException ex) {
            ex.printStackTrace();
        } catch (SocketTimeoutException ex) {
            ex.printStackTrace();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    public void listen() {
        while (running) {
            try {
                Socket socket = serverSocket.accept();

                Thread thread = new Thread(new RequestHandler(socket));

                servicingThreads.add(thread);
            } catch (SocketException ex) {
                System.out.println("Server closed");
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
    }

    public void closeServer() {
        System.out.println("Closing server...");
        running = false;

        try {
            FileOutputStream fileOutputStream = new FileOutputStream("cachedSites.txt");
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(fileOutputStream);

            objectOutputStream.writeObject(cache);
            objectOutputStream.close();
            fileOutputStream.close();

            System.out.println("Cached Sites written");

            FileOutputStream fileOutputStream2 = new FileOutputStream("blockedSites.txt");
            ObjectOutputStream objectOutputStream2 = new ObjectOutputStream(fileOutputStream);

            objectOutputStream2.writeObject(blockedSites);
            objectOutputStream2.close();
            fileOutputStream2.close();

            System.out.println("Blocked sites list saved");

            try {
                for (Thread thread : servicingThreads) {
                    if (thread.isAlive()) {
                        System.out.print("Waiting on " + thread.getId() + " to close..");
                        thread.join();
                        System.out.println("Close");

                    }
                }
            } catch (InterruptedException ex) {
                ex.printStackTrace();
            }
        } catch (IOException ex) {
            ex.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static File getCachedPage(String url) {
        return cache.get(url);
    }

    public static void addCachedPage(String urlString, File fileToCache){
        cache.put(urlString, fileToCache);
    }

    public static boolean isBlocked (String url){
        return blockedSites.get(url) != null;
    }


    @Override
    public void run() {
        Scanner scanner = new Scanner(System.in);

        String command;

        while (running) {
            System.out.println("Enter new site to block, or type \"blocked\" to see blocked sites, \"cached\" to see cached sites, or \"close\" to close server.");
            command = scanner.nextLine();
            if (command.toLowerCase().equals("blocked")) {
                System.out.println("\nCurrently Blocked Sites");
                for(String key : blockedSites.keySet()){
                    System.out.println(key);
                }
                System.out.println();
            } else if (command.toLowerCase().equals("cached")) {
                System.out.println("\nCurrently Cached Sites");
                for(String key : cache.keySet()){
                    System.out.println(key);
                }
                System.out.println();
            } else if(command.equals("close")){
                running = false;
                closeServer();
            } else {
                blockedSites.put(command, command);
                System.out.println("\n" + command + " blocked successfully \n");
            }
        }
        scanner.close();
    }
}
