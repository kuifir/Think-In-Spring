package kuifir.resource.springx;

import org.springframework.util.StreamUtils;
import sun.net.www.protocol.x.XURLConnection;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLStreamHandler;
import java.nio.charset.Charset;

/**
 * Package: sun.net.www.protocol.x
 * <p>
 * Description： x协议{@link URLStreamHandler} 实现
 * <p>
 * Author: baci
 * <p>
 * Date: Created in 2023/1/5 21:04
 * <p>
 * Version: 0.0.1
 */
public class Handler extends sun.net.www.protocol.x.Handler {
    // -Djava.protocol.handler.pkgs=kuifir.resource
    public static void main(String[] args) throws IOException {
        URL url = new URL("springx:///META-INF/default.properties");// 类似于 classpath:/META-INF/default.properties
        InputStream inputStream = url.openStream();
        System.out.println(StreamUtils.copyToString(inputStream, Charset.forName("GBK")));
    }
}
