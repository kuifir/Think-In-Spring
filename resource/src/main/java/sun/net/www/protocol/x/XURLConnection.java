package sun.net.www.protocol.x;

import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;

/**
 * Package: sun.net.www.protocol.x
 * <p>
 * Description： X协议{@link URLConnection}实现
 * <p>
 * Author: baci
 * <p>
 * Date: Created in 2023/1/5 21:06
 * <p>
 * Version: 0.0.1
 */
public class XURLConnection extends URLConnection{

    private final ClassPathResource resource;
    /**
     * Constructs a URL connection to the specified URL. A connection to
     * the object referenced by the URL is not created.
     *
     * @param url the specified URL.
     */
    // URL = X:///META-INF/default.properties
    protected XURLConnection(URL url) {
        super(url);
        this.resource = new ClassPathResource(url.getPath());
    }

    @Override
    public void connect() throws IOException {

    }

    public InputStream getInputStream() throws IOException {
        return resource.getInputStream();
    }

}
