package kuifir.configuration.metadata;

import kuifir.ioc.overview.domain.User;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.*;

import java.util.Map;

/**
 * Package: kuifir.configuration.metadata
 * <p>
 * Description： 基于注解装载Spring IoC容器配置元信息
 * <p>
 * Author: baci
 * <p>
 * Date: Created in 2023/1/4 15:19
 * <p>
 * Version: 0.0.1
 */
@ImportResource("classpath:/META-INF/dependency-lookup-context.xml")
@Import(User.class)
@PropertySource("classpath:/META-INF/user.properties")
public class AnnotatedSpringIoCContainerMetadataConfigurationDemo {
    // user.name 是 java Properties 默认存在，当前用户名，而土匪配置中的name
    @Bean
    public User configuredUser(@Value("${user.id}") Long id,@Value("${user.name}") String name){
        User user = new User();
        user.setId(id);
        user.setName(name);
        return user;
    }
    public static void main(String[] args) {
        AnnotationConfigApplicationContext applicationContext = new AnnotationConfigApplicationContext();
        // 注册当前类为Configuration Class
        applicationContext.register(AnnotatedSpringIoCContainerMetadataConfigurationDemo.class);
        // 启动Spring 应用上下文
        applicationContext.refresh();
        // beanName 和 bean 映射
        Map<String, User> userMap = applicationContext.getBeansOfType(User.class);
        for (Map.Entry<String, User> userEntry : userMap.entrySet()) {
            System.out.printf("User Bean name %s , content : %s \n" , userEntry.getKey(), userEntry.getValue());
        }
        // 关闭应用上下文
        applicationContext.close();
    }
}
