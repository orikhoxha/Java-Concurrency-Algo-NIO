package jenkov.example.nio.server.http;

import jenkov.example.nio.server.IMessageReader;
import jenkov.example.nio.server.Message;
import jenkov.example.nio.server.MessageBuffer;
import jenkov.example.nio.server.Socket;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.List;

public class HttpMessageReader implements IMessageReader {

    MessageBuffer messageBuffer;

    private List<Message> completeMessages = new ArrayList<>();

    private Message nextMessage;

    public HttpMessageReader(){}

    @Override
    public void init(MessageBuffer readMessageBuffer) {
        this.messageBuffer = readMessageBuffer;
        this.nextMessage = messageBuffer.getMessage();
        this.nextMessage.metadata = new HttpHeaders();
    }

    @Override
    public void read(Socket socket, ByteBuffer byteBuffer) throws IOException {
        int bytesRead = socket.read(byteBuffer);
        byteBuffer.flip();

        if (byteBuffer.remaining() == 0) { // between position and limit (position 0, limit = nr of bytes received)
            byteBuffer.clear();
            return;
        }

        this.nextMessage.writeToMessage(byteBuffer);

        int endIndex = HttpUtil.parseHttpRequest(this.nextMessage.sharedArray, this.nextMessage.offset,
                                         this.nextMessage.offset + this.nextMessage.length, (HttpHeaders) this.nextMessage.metadata);

        if (endIndex != -1) { // Full message received
            Message message = this.messageBuffer.getMessage(); // offset 4k
            message.metadata = new HttpHeaders();

            message.writePartialMessageToMessage(nextMessage, endIndex);
            completeMessages.add(nextMessage);
        }

        // Partial message received clear buffer
        byteBuffer.clear();
    }

    @Override
    public List<Message> getCompleteMessages() {
        return this.completeMessages;
    }
}
