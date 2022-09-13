package kuifir.ioc.dependency;

import kuifir.ioc.overview.domain.User;
import org.springframework.beans.factory.xml.XmlBeanDefinitionReader;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;

/**
 * Package: kuifir.ioc.dependency
 * <p>
 * Description： 基于java注解方式实现依赖Constructor注入
 * <p>
 * Author: baci
 * <p>
 * Date: Created in 2022/9/12 22:44
 * <p>
 * Version: 0.0.1
 */
public class AnnotationDependencyConstructorInjectionDemo {
    public static void main(String[] args) {
        // 新建BeanFactory容器
        AnnotationConfigApplicationContext applicationContext = new AnnotationConfigApplicationContext();
        // 添加Configuration Class
        applicationContext.register(AnnotationDependencyConstructorInjectionDemo.class);

        XmlBeanDefinitionReader beanDefinitionReader = new XmlBeanDefinitionReader(applicationContext);

        // 加载xml资源，解析并且生成BeanDefinition
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

    @Bean
    public UserHolder userHolder(User user){
        return new UserHolder(user);
    }
}
