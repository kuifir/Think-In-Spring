package kuifir.ioc.dependency;

import kuifir.ioc.dependency.annotation.UserGroup;
import kuifir.ioc.overview.domain.User;
import org.springframework.beans.factory.ObjectFactory;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.xml.XmlBeanDefinitionReader;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;

import java.util.Collection;
import java.util.Set;

/**
 * Package: kuifir.ioc.dependency
 * <p>
 * Description： {@link ObjectProvider} 实现延迟依赖注入
 * @see ObjectProvider
 * <p>
 * Author: baci
 * <p>
 * Date: Created in 2022/9/12 22:44
 * <p>
 * Version: 0.0.1
 */
public class LazyAnnotationDependencyInjectionyDemo {

    @Autowired
    private User user; // 实时注入
    @Autowired
    private ObjectProvider<User> userObjectProvider; // 延迟注入
    @Autowired
    private ObjectFactory<Set<User>> userObjectFactory;

    public static void main(String[] args) {
        // 新建BeanFactory容器
        AnnotationConfigApplicationContext applicationContext = new AnnotationConfigApplicationContext();
        // 添加Configuration Class -> 也是 Spring Bean
        applicationContext.register(LazyAnnotationDependencyInjectionyDemo.class);

        XmlBeanDefinitionReader beanDefinitionReader = new XmlBeanDefinitionReader(applicationContext);

        // 加载xml资源，解析并且生成BeanDefinition
        String xmlResourcePath = "classpath:/META-INF\\dependency-lookup-context.xml";
        beanDefinitionReader.loadBeanDefinitions(xmlResourcePath);

        // 启动容器上下文
        applicationContext.refresh();
        // 依赖查找
        LazyAnnotationDependencyInjectionyDemo demo = applicationContext.getBean(LazyAnnotationDependencyInjectionyDemo.class);

        System.out.println(demo.user); //输出superUser
        System.out.println(demo.userObjectProvider.getObject()); //继承了ObjectFactory中的方法

        System.out.println(demo.userObjectFactory.getObject());
        demo.userObjectProvider.forEach(System.out::println);

        // 关闭容器
        applicationContext.close();
    }

}
