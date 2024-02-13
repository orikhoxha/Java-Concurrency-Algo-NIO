package jenkov.example.nio.server;

import java.util.logging.Logger;

public class MessageBuffer {


    private static final Logger logger = Logger.getLogger(MessageBuffer.class.getName());

    public static int KB = 1024; // 1KB
    public static int MB = 1024 * KB; // 1MB

    private static final int CAPACITY_SMALL = 4 * KB; // 4KB
    private static final int CAPACITY_MEDIUM = 128 * KB; // 128 KB
    private static final int CAPACITY_LARGE = MB;

    byte[] smallMessageBuffer = new byte[1024 * 4 * KB]; // 4MB
    byte[] mediumMessageBuffer = new byte[1024 * 128 * KB]; // 16 MB
    byte[] largeMessageBuffer = new byte[16 * MB]; // 16 MB

    public QueueIntFlip smallMessageBufferFreeBlocks = new QueueIntFlip(1024); // 1024 free sections;
    QueueIntFlip mediumMessageBufferFreeBlocks = new QueueIntFlip(128); // 128 free sections
    QueueIntFlip largeMessageBufferFreeBlocks = new QueueIntFlip(16); // 16 free sections

    public MessageBuffer() {
        for (int i = 0; i < smallMessageBuffer.length; i+=CAPACITY_SMALL) {
            this.smallMessageBufferFreeBlocks.put(i);
        }

        for (int i = 0; i < mediumMessageBuffer.length; i+=CAPACITY_MEDIUM) {
            this.mediumMessageBufferFreeBlocks.put(i);
        }

        for (int i = 0; i < largeMessageBuffer.length; i+=CAPACITY_LARGE) {
            this.largeMessageBufferFreeBlocks.put(i);
        }

        /*logger.info(() -> "read: " + smallMessageBufferFreeBlocks.readPos  +
                ", write: " + smallMessageBufferFreeBlocks.writePos + ", flip: " + smallMessageBufferFreeBlocks.flipped);*/
    }


    public Message getMessage() {
        int nextFreeSmallBlock = this.smallMessageBufferFreeBlocks.take();

        if (nextFreeSmallBlock == -1) return null;

        Message message = new Message(this);

        message.sharedArray = this.smallMessageBuffer;
        message.capacity = CAPACITY_SMALL;
        message.offset = nextFreeSmallBlock;
        message.length = 0;

        return message;
    }
    public boolean expandMessage(Message message) {
        if (message.capacity == CAPACITY_SMALL) {
            return moveMessage(message, this.smallMessageBufferFreeBlocks, this.mediumMessageBufferFreeBlocks, this.mediumMessageBuffer, CAPACITY_MEDIUM);
        } else if (message.capacity ==  CAPACITY_MEDIUM) {
            return moveMessage(message, this.mediumMessageBufferFreeBlocks, this.largeMessageBufferFreeBlocks, this.largeMessageBuffer, CAPACITY_LARGE);
        } else {
            return false;
        }
    }

    private boolean moveMessage(Message message, QueueIntFlip srcBlockQueue, QueueIntFlip destBlockQueue, byte[] dest, int newCapacity) {
        int nextFreeBlock = destBlockQueue.take();
        if (nextFreeBlock == -1) return false;

        System.arraycopy(message.sharedArray, message.offset, dest, nextFreeBlock, message.length);
        srcBlockQueue.put(message.offset);

        message.sharedArray = dest;
        message.offset = nextFreeBlock;
        message.capacity = newCapacity;
        return true;
    }
}
