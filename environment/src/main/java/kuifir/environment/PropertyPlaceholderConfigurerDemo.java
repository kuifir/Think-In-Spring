package kuifir.environment;

import kuifir.ioc.overview.domain.User;
import org.springframework.beans.factory.config.PropertyPlaceholderConfigurer;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * Package: kuifir.environment
 * <p>
 * Description： {@link PropertyPlaceholderConfigurer} ，示例
 * <p>
 * Author: baci
 * <p>
 * Date: Created in 2023/1/30 10:24
 * <p>
 * Version: 0.0.1
 */
public class PropertyPlaceholderConfigurerDemo {
    public static void main(String[] args) {
        String location = "classpath:/META-INF/placeholders-resolver.xml";
        ClassPathXmlApplicationContext applicationContext = new ClassPathXmlApplicationContext(location);
        User user = applicationContext.getBean("user", User.class);
        System.out.println(user);
        applicationContext.close();
    }
}
