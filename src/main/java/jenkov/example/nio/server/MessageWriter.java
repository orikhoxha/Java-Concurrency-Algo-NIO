package jenkov.example.nio.server;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.List;

public class MessageWriter {

    private List<Message> writeQueue = new ArrayList<>();
    private Message messageInProgress;
    private int bytesWritten = 0;

    public MessageWriter(){}

    public void enqueue(Message message) {
        if (this.messageInProgress == null) {
            this.messageInProgress = message;
        } else {
            this.writeQueue.add(message);
        }
    }

    public void write(Socket socket, ByteBuffer byteBuffer) throws IOException {
        byteBuffer.put(this.messageInProgress.sharedArray, this.messageInProgress.offset, this.messageInProgress.length);
        byteBuffer.flip();

        this.bytesWritten += socket.write(byteBuffer);
        byteBuffer.clear();


        if (bytesWritten >= this.messageInProgress.length) {
            if (this.writeQueue.size() > 0) {
                this.writeQueue.remove(0);
            } else {
                this.messageInProgress = null;
            }
        }
    }

    public boolean isEmpty() {
        return this.writeQueue.isEmpty() && this.messageInProgress == null;
    }
}
