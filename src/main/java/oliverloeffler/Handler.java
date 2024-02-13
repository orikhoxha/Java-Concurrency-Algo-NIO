package oliverloeffler;

import java.util.function.Consumer;

public interface Handler {

    void handle(Request request, Consumer<Response> callback);
}
