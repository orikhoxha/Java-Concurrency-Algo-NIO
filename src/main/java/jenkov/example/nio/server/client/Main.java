package jenkov.example.nio.server.client;

import jenkov.example.nio.server.IMessageProcessor;
import jenkov.example.nio.server.Message;
import jenkov.example.nio.server.Server;
import jenkov.example.nio.server.http.HttpMessageReaderFactory;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

public class Main {

    public static void main(String[] args) throws IOException {

        String httpResponse = "HTTP/1.2 200 OK\r\n" +
                "Content-Length: 38\r\n" +
                "Content-Type: text/html\r\n" +
                "\r\n" +
                "<html><body>Hello World!</body></html>";

        byte[] httpResponseBytes = httpResponse.getBytes(StandardCharsets.UTF_8);
        IMessageProcessor messageProcessor = (request, writeProxy) -> {
            System.out.println("Message received from socket: " + request.socketId);

            Message response = writeProxy.getMessage();
            response.socketId = request.socketId;
            response.writeToMessage(httpResponseBytes);
            writeProxy.enqueue(response);
        };

        Server server = new Server(9999, new HttpMessageReaderFactory(), messageProcessor);
        server.start();
    }
}
