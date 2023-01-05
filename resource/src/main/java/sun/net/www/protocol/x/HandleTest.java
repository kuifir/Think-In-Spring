package sun.net.www.protocol.x;

import org.springframework.util.StreamUtils;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.nio.charset.Charset;

/**
 * Package: sun.net.www.protocol.x
 * <p>
 * Description： TODO
 * <p>
 * Author: baci
 * <p>
 * Date: Created in 2023/1/5 21:21
 * <p>
 * Version: 0.0.1
 */
public class HandleTest {
    public static void main(String[] args) throws IOException {
        URL url = new URL("x:///META-INF/default.properties");// 类似于 classpath:/META-INF/default.properties
        InputStream inputStream = url.openStream();
        System.out.println(StreamUtils.copyToString(inputStream, Charset.forName("GBK")));
    }
}
