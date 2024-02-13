package jenkov.example.nio.server.http;

import org.junit.jupiter.api.Test;

import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNotSame;


public class HttpUtilTest {

    @Test
    public void testResolveHttpMethod() throws UnsupportedEncodingException {
        assertHttpMethod("GET / HTTP/1.1\r\n", HttpHeaders.HTTP_METHOD_GET);
        assertHttpMethod("POST / HTTP/1.1\r\n", HttpHeaders.HTTP_METHOD_POST);
        assertHttpMethod("PUT / HTTP/1.1\r\n", HttpHeaders.HTTP_METHOD_PUT);
        assertHttpMethod("HEAD / HTTP/1.1\r\n", HttpHeaders.HTTP_METHOD_HEAD);
        assertHttpMethod("DELETE / HTTP/1.1\r\n", HttpHeaders.HTTP_METHOD_DELETE);
    }

    private void assertHttpMethod(String httpRequest, int httpMethod) throws UnsupportedEncodingException {
        byte[] src = httpRequest.getBytes(StandardCharsets.UTF_8);
        HttpHeaders httpHeaders = new HttpHeaders();

        HttpUtil.resolveHttpMethod(src, 0, httpHeaders);
        assertEquals(httpMethod, httpHeaders.httpMethod);
    }

    @Test
    public void testParseHttpRequest() throws UnsupportedEncodingException {
        String httpRequest = "GET / HTTP/1.1\r\n\r\n";

        byte[] source = httpRequest.getBytes(StandardCharsets.UTF_8);
        HttpHeaders httpHeaders = new HttpHeaders();

        HttpUtil.parseHttpRequest(source, 0, source.length, httpHeaders);

        assertEquals(0, httpHeaders.contentLength);

        httpRequest = "GET / HTTP/1.1\r\n" +
                      "Content-Length: 5\r\n" +
                      "\r\n1234";
        source = httpRequest.getBytes(StandardCharsets.UTF_8);
        assertEquals(-1, HttpUtil.parseHttpRequest(source, 0, source.length, httpHeaders));
        assertEquals(5, httpHeaders.contentLength);


        httpRequest = "GET / HTTP/1.1\r\n" +
                      "Content-Length: 5\r\n" +
                      "\r\n12345";
        source = httpRequest.getBytes(StandardCharsets.UTF_8);
        assertEquals(42, HttpUtil.parseHttpRequest(source, 0, source.length, httpHeaders));
        assertEquals(5, httpHeaders.contentLength);
        assertEquals(37, httpHeaders.bodyStartIndex);
        assertEquals(42, httpHeaders.bodyEndIndex);
    }
}
