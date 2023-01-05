package kuifir.resource;

import kuifir.resource.utils.ResourceUtils;
import org.apache.commons.io.IOUtils;
import org.springframework.core.io.FileSystemResourceLoader;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.EncodedResource;

import java.io.IOException;
import java.io.Reader;

/**
 * Package: kuifir.resource
 * <p>
 * Description： 带有字符编码的{@link FileSystemResourceLoader} 示例
 * <p>
 * Author: baci
 * <p>
 * Date: Created in 2023/1/5 16:04
 * <p>
 * Version: 0.0.1
 *
 * @see FileSystemResourceLoader
 * @see EncodedResource
 */
public class EncodedFileSystemResourceLoaderDemo {
    public static void main(String[] args) throws IOException {
        String currentJavaFiePath = "/" + System.getProperty("user.dir") + "/resource/src/main/java/kuifir/resource/EncodedFileSystemResourceLoaderDemo.java";
        Resource resource = new FileSystemResourceLoader().getResource(currentJavaFiePath);
        ResourceUtils.getContent(resource);
    }
}
