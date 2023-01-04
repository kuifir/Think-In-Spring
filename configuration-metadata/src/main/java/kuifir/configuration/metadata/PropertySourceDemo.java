package kuifir.configuration.metadata;

import kuifir.ioc.overview.domain.User;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.PropertiesPropertySource;

import java.util.Map;
import java.util.Properties;

/**
 * Package: kuifir.configuration.metadata
 * <p>
 * Description： 外部化配置示例
 * <p>
 * Author: baci
 * <p>
 * Date: Created in 2023/1/4 20:59
 * <p>
 * Version: 0.0.1
 */
@PropertySource("classpath:/META-INF/user.properties")
public class PropertySourceDemo {
    // user.name 是 java Properties 默认存在，当前用户名，而非配置中的name
    @Bean
    public User configuredUser(@Value("${user.id}") Long id, @Value("${user.name}") String name){
        User user = new User();
        user.setId(id);
        user.setName(name);
        return user;
    }
    public static void main(String[] args) {
        AnnotationConfigApplicationContext applicationContext = new AnnotationConfigApplicationContext();

        // 扩展Environment中的 PropertySource，修改获取顺序
        // 扩展PropertySource 必须在 refresh方法之前完成
        Properties property = new Properties();
        property.setProperty("user.name","PropertySource API");
        org.springframework.core.env.PropertySource propertySource =
                new PropertiesPropertySource("firs-property-source", property);
        applicationContext.getEnvironment().getPropertySources().addFirst(propertySource);

        // 注册当前类为Configuration Class
        applicationContext.register(PropertySourceDemo.class);
        // 启动Spring 应用上下文
        applicationContext.refresh();
        // beanName 和 bean 映射
        Map<String, User> userMap = applicationContext.getBeansOfType(User.class);
        for (Map.Entry<String, User> userEntry : userMap.entrySet()) {
            System.out.printf("User Bean name %s , content : %s \n" , userEntry.getKey(), userEntry.getValue());
        }
        System.out.println(applicationContext.getEnvironment().getPropertySources());
        // 关闭应用上下文
        applicationContext.close();
    }
}
