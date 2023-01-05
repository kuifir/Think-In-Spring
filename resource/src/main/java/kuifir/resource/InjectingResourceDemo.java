package kuifir.resource;

import kuifir.resource.utils.ResourceUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.core.io.Resource;

import javax.annotation.PostConstruct;
import java.util.Collection;
import java.util.List;
import java.util.stream.Stream;

/**
 * Package: kuifir.resource
 * <p>
 * Description： 依赖注入{@link org.springframework.core.io.Resource}示例
 * <p>
 * Author: baci
 * <p>
 * Date: Created in 2023/1/5 17:25
 * <p>
 * Version: 0.0.1
 */
public class InjectingResourceDemo {
    @Value("classpath:/META-INF/default.properties")
    private Resource resource;
    @Value("classpath*:/META-INF/*.properties")
    private Resource[] resources;
//    @Value("classpath*:/META-INF/*.properties")
//    private Collection<Resource> resourceList;

    @PostConstruct
    public void init(){
        System.out.println(ResourceUtils.getContent(resource, "GBK"));

        Stream.of(resources).map(resource -> ResourceUtils.getContent(resource,"GBK")).forEach(System.out::println);

//        resourceList.stream().map(resource -> ResourceUtils.getContent(resource,"GBK")).forEach(System.out::println);

    }
    public static void main(String[] args) {
        AnnotationConfigApplicationContext applicationContext = new AnnotationConfigApplicationContext();
        applicationContext.register(InjectingResourceDemo.class);
        applicationContext.refresh();
        applicationContext.close();
    }
}
