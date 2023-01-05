package kuifir.resource;

import kuifir.resource.utils.ResourceUtils;
import org.springframework.core.io.FileSystemResourceLoader;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.core.io.support.ResourcePatternResolver;

import java.io.IOException;
import java.util.stream.Stream;

/**
 * Package: kuifir.resource
 * <p>
 * Description： {@link ResourcePatternResolver} 示例
 * <p>
 * Author: baci
 * <p>
 * Date: Created in 2023/1/5 16:56
 * <p>
 * Version: 0.0.1
 */
public class CustomizedResourcePatternResolverDemo {
    public static void main(String[] args) throws IOException {
        // 读取当前package 下所有的java文件
        String currentJavaFiePath = "/" + System.getProperty("user.dir") + "/resource/src/main/java/kuifir/resource/";
        String locationPattern = currentJavaFiePath +"**/*.java";
        PathMatchingResourcePatternResolver pathMatchingResourcePatternResolver = new PathMatchingResourcePatternResolver(new FileSystemResourceLoader());
        final Resource[] resources = pathMatchingResourcePatternResolver.getResources(locationPattern);
        Stream.of(resources).map(ResourceUtils::getContent).forEach(System.out::println);

    }
}
