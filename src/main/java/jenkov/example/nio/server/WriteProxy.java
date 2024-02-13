package jenkov.example.nio.server;

import java.util.Queue;

public class WriteProxy {

    private MessageBuffer messageBuffer;
    private Queue writeQueue;

    public WriteProxy(MessageBuffer messageBuffer, Queue writeQueue) {
        this.messageBuffer = messageBuffer;
        this.writeQueue = writeQueue;
    }

    public Message getMessage() {
        return this.messageBuffer.getMessage();
    }

    public boolean enqueue(Message message) {
        return this.writeQueue.offer(message);
    }
}
