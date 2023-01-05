package sun.net.www.protocol.x;

import java.io.IOException;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLStreamHandler;

/**
 * Package: sun.net.www.protocol.x
 * <p>
 * Description： x协议{@link java.net.URLStreamHandler} 实现
 * <p>
 * Author: baci
 * <p>
 * Date: Created in 2023/1/5 21:04
 * <p>
 * Version: 0.0.1
 */
public class Handler extends URLStreamHandler {
    @Override
    protected URLConnection openConnection(URL u) throws IOException {
        return new XURLConnection(u);
    }
}
