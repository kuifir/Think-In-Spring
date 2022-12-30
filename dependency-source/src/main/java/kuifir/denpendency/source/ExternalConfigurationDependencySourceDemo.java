package kuifir.denpendency.source;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.io.Resource;

/**
 * Package: kuifir.denpendency.source
 * <p>
 * Description： 外部化配置作为依赖源
 * <p>
 * Author: baci
 * <p>
 * Date: Created in 2022/12/30 17:10
 * <p>
 * Version: 0.0.1
 */
@Configuration
@PropertySource(value = "classpath:/META-INF/default.properties", encoding = "UTF-8")
public class ExternalConfigurationDependencySourceDemo {
    @Value("${user.id:-1}")
    private Long id;
    @Value("${usr.name:kui}")
    private String name;
    @Value("${user.resource:classpath:/default.properties}")
    private Resource resource;

    public static void main(String[] args) {
        AnnotationConfigApplicationContext applicationContext = new AnnotationConfigApplicationContext();
        applicationContext.register(ExternalConfigurationDependencySourceDemo.class);
        applicationContext.refresh();
        ExternalConfigurationDependencySourceDemo demo = applicationContext.getBean(ExternalConfigurationDependencySourceDemo.class);
        System.out.println(demo.id);
        System.out.println(demo.name);
        System.out.println(demo.resource);
        applicationContext.close();
    }
}
