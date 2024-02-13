package jenkov.example.nio.server.http;

import jenkov.example.nio.server.IMessageReader;
import jenkov.example.nio.server.IMessageReaderFactory;

public class HttpMessageReaderFactory implements IMessageReaderFactory {

    @Override
    public IMessageReader createMessageReader() {
        return new HttpMessageReader();
    }
}
