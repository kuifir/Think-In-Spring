package kuifir.ioc.dependency;

import kuifir.ioc.overview.domain.User;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.xml.XmlBeanDefinitionReader;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;

/**
 * Package: kuifir.ioc.dependency
 * <p>
 * Description： 基于API注解方式实现依赖setter注入
 * <p>
 * Author: baci
 * <p>
 * Date: Created in 2022/9/12 22:44
 * <p>
 * Version: 0.0.1
 */
public class ApiDependencySetterInjectionDemo {
    public static void main(String[] args) {
        // 新建BeanFactory容器
        AnnotationConfigApplicationContext applicationContext = new AnnotationConfigApplicationContext();

        // 生成UserHolder 的 BeanDefinition
        BeanDefinition userHolderBeanDefinition = createUserHolderBeanDefinition();
        // 注册UserHolder 的 BeanDefinition
        applicationContext.registerBeanDefinition("userHolder",userHolderBeanDefinition);

        XmlBeanDefinitionReader beanDefinitionReader = new XmlBeanDefinitionReader(applicationContext);
        // 加载xml资源
        String xmlResourcePath = "classpath:/META-INF\\dependency-lookup-context.xml";
        beanDefinitionReader.loadBeanDefinitions(xmlResourcePath);

        // 启动容器上下文
        applicationContext.refresh();
        // 依赖查找
        UserHolder userHolder = applicationContext.getBean(UserHolder.class);
        System.out.println(userHolder);
        // 关闭容器
        applicationContext.close();
    }

    /**
     * 为{@link UserHolder}生成{@link BeanDefinition}
     * @return
     */
    private static BeanDefinition createUserHolderBeanDefinition(){
        BeanDefinitionBuilder beanDefinitionBuilder = BeanDefinitionBuilder.genericBeanDefinition(UserHolder.class);
        beanDefinitionBuilder.addPropertyReference("user","superUser");

        return beanDefinitionBuilder.getBeanDefinition();
    }
//    @Bean
//    public UserHolder userHolder(User user){// superUser ->primary = true
////        return new UserHolder(user);
//        final UserHolder userHolder = new UserHolder();
//        userHolder.setUser(user);
//        return userHolder;
//    }
}
