package kuifir.resource.utils;

import org.apache.commons.io.IOUtils;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.EncodedResource;

import java.io.IOException;
import java.io.Reader;

/**
 * Package: kuifir.resource.utils
 * <p>
 * Description： Resource 工具类
 * <p>
 * Author: baci
 * <p>
 * Date: Created in 2023/1/5 16:49
 * <p>
 * Version: 0.0.1
 */
public class ResourceUtils {
    public static String getContent(Resource resource){
        return getContent(resource,"UTF-8");
    }

    public static String getContent(Resource resource, String encoding){
        EncodedResource encodedResource = new EncodedResource(resource,encoding);
        try (Reader reader = encodedResource.getReader()){
            return IOUtils.toString(reader);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
