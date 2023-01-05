package kuifir.resource;

import kuifir.resource.utils.ResourceUtils;
import org.apache.commons.io.IOUtils;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.support.EncodedResource;

import java.io.File;
import java.io.IOException;
import java.io.Reader;

/**
 * Package: kuifir.resource
 * <p>
 * Description： 带有字符编码的{@link FileSystemResource} 示例
 * <p>
 * Author: baci
 * <p>
 * Date: Created in 2023/1/5 16:04
 * <p>
 * Version: 0.0.1
 * @see FileSystemResource
 * @see EncodedResource
 */
public class EncodedFileSystemResourceDemo {
    public static void main(String[] args) throws IOException {
        String currentJavaFiePath = System.getProperty("user.dir")+"/resource/src/main/java/kuifir/resource/EncodedFileSystemResourceDemo.java";
        File file = new File(currentJavaFiePath);
        FileSystemResource fileSystemResource = new FileSystemResource(file);
        ResourceUtils.getContent(fileSystemResource);
    }
}
