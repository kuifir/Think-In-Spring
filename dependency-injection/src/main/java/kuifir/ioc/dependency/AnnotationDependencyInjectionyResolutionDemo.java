package kuifir.ioc.dependency;

import kuifir.ioc.dependency.annotation.InjectedUser;
import kuifir.ioc.dependency.annotation.MyAutowired;
import kuifir.ioc.overview.domain.User;
import org.springframework.beans.factory.ObjectFactory;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.AutowiredAnnotationBeanPostProcessor;
import org.springframework.beans.factory.xml.XmlBeanDefinitionReader;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.AnnotationConfigUtils;
import org.springframework.context.annotation.Bean;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;

import javax.inject.Inject;
import java.lang.annotation.Annotation;
import java.util.*;

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
                        // 是否必要（primary = true）

    @Autowired
    private Map<String,User> users;

//    @Autowired
    @MyAutowired
    private Optional<User> userOptional;

    @Inject
    private User injectUser;

    @InjectedUser
    private User myInjectedUser;

//    @Bean(AnnotationConfigUtils.AUTOWIRED_ANNOTATION_PROCESSOR_BEAN_NAME)
//    public static AutowiredAnnotationBeanPostProcessor beanPostProcessor(){
//        AutowiredAnnotationBeanPostProcessor beanPostProcessor = new AutowiredAnnotationBeanPostProcessor();
//        // 替换原有注解处理，使用新注解 @InjectedUser
////        beanPostProcessor.setAutowiredAnnotationType(InjectedUser.class);
//        // @Autowired + @Inject + 新注解 @InjectedUser
//        Set<Class<? extends Annotation>> autowiredAnnotationTypes = new LinkedHashSet<>(Arrays.asList(Autowired.class,Inject.class,InjectedUser.class));
//        beanPostProcessor.setAutowiredAnnotationTypes(autowiredAnnotationTypes);
//        return beanPostProcessor;
//    }
    @Bean
    @Order(Ordered.LOWEST_PRECEDENCE-3)
    public static AutowiredAnnotationBeanPostProcessor beanPostProcessor(){
        AutowiredAnnotationBeanPostProcessor beanPostProcessor = new AutowiredAnnotationBeanPostProcessor();
        beanPostProcessor.setAutowiredAnnotationType(InjectedUser.class);
        return beanPostProcessor;
    }

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
        System.out.println(demo.injectUser); //输出superUser
        System.out.println(demo.users);
        System.out.println(demo.userOptional); //输出superUser
        System.out.println(demo.myInjectedUser); //输出superUser

        // 关闭容器
        applicationContext.close();
    }

}
