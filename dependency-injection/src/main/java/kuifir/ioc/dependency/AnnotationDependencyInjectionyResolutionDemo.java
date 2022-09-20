package kuifir.ioc.dependency;

import kuifir.ioc.overview.domain.User;
import org.springframework.beans.factory.ObjectFactory;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.xml.XmlBeanDefinitionReader;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.util.Collection;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

/**
 * Package: kuifir.ioc.dependency
 * <p>
 * Description： 注解驱动的依赖注入处理过程
 * @see
 * <p>
 * Author: baci
 * <p>
 * Date: Created in 2022/9/12 22:44
 * <p>
 * Version: 0.0.1
 */
public class AnnotationDependencyInjectionyResolutionDemo {

    @Autowired         // 依赖查找（处理）
    private User user;  // DependencyDescriptor ->
                        // 必须(required=true)
                        // 实时注入（eager=true）
                        // 通过类型（User.class）
                        // 字段名称("user")

    @Autowired
    private Map<String,User> users;

    @Autowired
    private Optional<User> userOptional;

    public static void main(String[] args) {
        // 新建BeanFactory容器
        AnnotationConfigApplicationContext applicationContext = new AnnotationConfigApplicationContext();
        // 添加Configuration Class -> 也是 Spring Bean
        applicationContext.register(AnnotationDependencyInjectionyResolutionDemo.class);

        XmlBeanDefinitionReader beanDefinitionReader = new XmlBeanDefinitionReader(applicationContext);

        // 加载xml资源，解析并且生成BeanDefinition
        String xmlResourcePath = "classpath:/META-INF\\dependency-lookup-context.xml";
        beanDefinitionReader.loadBeanDefinitions(xmlResourcePath);

        // 启动容器上下文
        applicationContext.refresh();
        // 依赖查找
        AnnotationDependencyInjectionyResolutionDemo demo = applicationContext.getBean(AnnotationDependencyInjectionyResolutionDemo.class);

        System.out.println(demo.user); //输出superUser
        System.out.println(demo.users);
        System.out.println(demo.userOptional); //输出superUser

        // 关闭容器
        applicationContext.close();
    }

}
