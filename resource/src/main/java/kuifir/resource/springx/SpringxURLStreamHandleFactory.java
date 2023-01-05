package kuifir.resource.springx;

import org.springframework.util.StreamUtils;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLStreamHandler;
import java.net.URLStreamHandlerFactory;
import java.nio.charset.Charset;

/**
 * Package: kuifir.resource.springx
 * <p>
 * Description： springx 协议{@link java.net.URLStreamHandlerFactory}
 * <p>
 * Author: baci
 * <p>
 * Date: Created in 2023/1/5 21:44
 * <p>
 * Version: 0.0.1
 */
public class SpringxURLStreamHandleFactory implements URLStreamHandlerFactory {
    @Override
    public URLStreamHandler createURLStreamHandler(String protocol) {
        return new Handler();
    }

    public static void main(String[] args) throws IOException {
        URL.setURLStreamHandlerFactory(new SpringxURLStreamHandleFactory());
        URL url = new URL("x:///META-INF/default.properties");// 类似于 classpath:/META-INF/default.properties
        InputStream inputStream = url.openStream();
        System.out.println(StreamUtils.copyToString(inputStream, Charset.forName("GBK")));
    }
}
