package kuifir.configuration.metadata;

import kuifir.ioc.overview.domain.User;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.PropertySource;

import java.util.Map;

/**
 * Package: kuifir.configuration.metadata
 * <p>
 * Description： 基于Java注解的 YAML 外部化配置
 * <p>
 * Author: baci
 * <p>
 * Date: Created in 2023/1/4 21:56
 * <p>
 * Version: 0.0.1
 */
@PropertySource(name = "yamlPropertySource",
        value = "classpath:/META-INF/user.yaml",
        factory = YamlPropertySourceFactory.class)
public class AnnotatedYamlPropertySourceDemo {
    @Bean
    public User user(@Value("${user.id}") Long id, @Value("${user.name}") String name){
        User user = new User();
        user.setId(id);
        user.setName(name);
        return user;
    }
    public static void main(String[] args) {
        AnnotationConfigApplicationContext applicationContext = new AnnotationConfigApplicationContext();
        // 注册当前类为Configuration Class
        applicationContext.register(AnnotatedYamlPropertySourceDemo.class);
        // 启动Spring 应用上下文
        applicationContext.refresh();

        User user = applicationContext.getBean("user", User.class);
        System.out.println(user);
        // 关闭应用上下文
        applicationContext.close();
    }
}
