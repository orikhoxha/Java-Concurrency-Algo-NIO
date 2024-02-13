package jenkov.example.nio.server;

public interface IMessageProcessor {

    void process(Message message, WriteProxy writeProxy);
}
