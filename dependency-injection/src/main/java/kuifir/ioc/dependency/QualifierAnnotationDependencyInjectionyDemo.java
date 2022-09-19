package kuifir.ioc.dependency;

import kuifir.ioc.dependency.annotation.UserGroup;
import kuifir.ioc.overview.domain.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.xml.XmlBeanDefinitionReader;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;

import java.util.Collection;

/**
 * Package: kuifir.ioc.dependency
 * <p>
 * Description： {@link Qualifier} 依赖注入
 * @see Qualifier
 * <p>
 * Author: baci
 * <p>
 * Date: Created in 2022/9/12 22:44
 * <p>
 * Version: 0.0.1
 */
public class QualifierAnnotationDependencyInjectionyDemo {

    @Autowired
    private User user; // superUser -> primary =true
    @Autowired
    @Qualifier("user") // 指定Bean 名称或者ID
    private User namedUser;
    @Autowired
    private Collection<User> allUsers; // 2 Beans = user +superUser

    @Autowired
    @Qualifier
    private Collection<User> qualifierUsers; // 4Bean = user1+user2 + user3+user4
    @Autowired
    @UserGroup
    private Collection<User> groupedUsers; // 2Bean = user3 +user4

    @Bean
    @Qualifier //进行逻辑分组
    public User user1(){
        User user = new User();
        user.setId(7L);
        return user;
    }
    @Bean
    @Qualifier // 进行分组
    public User user2(){
        User user = new User();
        user.setId(8L);
        return user;
    }

    @Bean
    @UserGroup // 进行分组
    public User user3(){
        User user = new User();
        user.setId(9L);
        return user;
    }
    @Bean
    @UserGroup // 进行分组
    public User user4(){
        User user = new User();
        user.setId(10L);
        return user;
    }

    public static void main(String[] args) {
        // 新建BeanFactory容器
        AnnotationConfigApplicationContext applicationContext = new AnnotationConfigApplicationContext();
        // 添加Configuration Class -> 也是 Spring Bean
        applicationContext.register(QualifierAnnotationDependencyInjectionyDemo.class);

        XmlBeanDefinitionReader beanDefinitionReader = new XmlBeanDefinitionReader(applicationContext);

        // 加载xml资源，解析并且生成BeanDefinition
        String xmlResourcePath = "classpath:/META-INF\\dependency-lookup-context.xml";
        beanDefinitionReader.loadBeanDefinitions(xmlResourcePath);

        // 启动容器上下文
        applicationContext.refresh();
        // 依赖查找
        QualifierAnnotationDependencyInjectionyDemo demo = applicationContext.getBean(QualifierAnnotationDependencyInjectionyDemo.class);

        System.out.println(demo.user); //输出superUser
        System.out.println(demo.namedUser);// 输出user

        System.out.println(demo.allUsers); // 输出 user+superUser
        System.out.println(demo.qualifierUsers); // 输出 user1+user2 +  user3+user4
        System.out.println(demo.groupedUsers); // 输出 user3+user4
        // 关闭容器
        applicationContext.close();
    }

}
