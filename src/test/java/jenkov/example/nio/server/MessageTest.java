package jenkov.example.nio.server;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNotSame;

import javarealworldchallenges.threads.ThreadPoolWorkStealing_LinkedBlockingQueue.AssemblyLine;
import org.junit.jupiter.api.Test;

import java.nio.ByteBuffer;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Logger;

public class MessageTest {

    private static final Logger logger = Logger.getLogger(MessageTest.class.getName());

    @Test
    public void testWriteToMessage() {



     /*   Message message = messageBuffer.getMessage();
        ByteBuffer byteBuffer = ByteBuffer.allocate(1024 * 1024);

        fill(byteBuffer, 4096);
        
        int written = message.writeToMessage(byteBuffer);
        assertEquals(4096, written);
        assertEquals(4096, message.length);
        assertSame(messageBuffer.smallMessageBuffer, message.sharedArray);*/

/*
        Message message2 = messageBuffer.getMessage();

        fill(byteBuffer, 4096);

        written = message2.writeToMessage(byteBuffer);
        assertEquals(4096, written);
        assertEquals(4096, message2.length);
        assertSame(messageBuffer.smallMessageBuffer, message.sharedArray);*/


        // Same connection
        // 1024 messages
        // 1st message 8kb
        // 2 - 1024 4kb

        MessageBuffer messageBuffer = new MessageBuffer();
        ByteBuffer byteBuffer = ByteBuffer.allocate(1024 * 1024);
        fill(byteBuffer, 4096 * 2); // 8kb

        Message message = messageBuffer.getMessage();
        message.writeToMessage(byteBuffer);

        // 1024 messages each 4kb
        Map<Integer, Message> messageMap = new HashMap<>();
        for (int i = 0; i < 1024; i++) {
            fill(byteBuffer, 4096);
            Message message2 = messageBuffer.getMessage();

            logger.info(() -> "read: " + messageBuffer.smallMessageBufferFreeBlocks.readPos  +
                    ", write: " + messageBuffer.smallMessageBufferFreeBlocks.writePos + ", flip: " + messageBuffer.smallMessageBufferFreeBlocks.flipped);

            message2.writeToMessage(byteBuffer);
            messageMap.put(i, message2);

        }


        assertSame(messageBuffer.mediumMessageBuffer, message.sharedArray);

        messageMap.forEach((k,msg) -> {
            assertSame(messageBuffer.smallMessageBuffer, msg.sharedArray);
        });

    }

    private void fill(ByteBuffer byteBuffer, int length) {
        byteBuffer.clear();
        for (int i = 0; i < length; i++) {
            byteBuffer.put((byte) (i % 128));
        }
        byteBuffer.flip();
    }
}
