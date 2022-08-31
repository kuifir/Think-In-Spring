package kuifir.bean.definition;

import kuifir.ioc.overview.container.AnnotationApplicationContextAsIoCContainerDemo;
import kuifir.ioc.overview.domain.User;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.support.BeanDefinitionReaderUtils;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * Package: kuifir.bean.definition
 * <p>
 * Description： TODO
 * <p>
 * Author: baci
 * <p>
 * Date: Created in 2022/8/26 23:21
 * <p>
 * Version: 0.0.1
 */
// 3. 通过@Import导入
@Import(AnnotationBeanDefinitionDemo.Config.class)//通过@Import来进行导入
public class AnnotationBeanDefinitionDemo {
    public static void main(String[] args) {
        // 创建容器
        AnnotationConfigApplicationContext applicationContext = new AnnotationConfigApplicationContext();
        // 注册 Configuration Class (配置类)这个类相当于代替了xml文件
        applicationContext.register(AnnotationBeanDefinitionDemo.class);
        // 启动运用上下文
        applicationContext.refresh();
        // 一、通过注解 注册BeanDefinition
        //       1. 通过@Bean 方式定义
        //       2. 通过@Component
        //       3. 通过@Import导入

        // 二、 通过BeanDefinition 注册 API 实现
        //       1.命名Bean 的注册方式
        registerBeanDefinition(applicationContext,"kui-user",User.class);
        //       2.非命名Bean 的注册方式
        registerBeanDefinition(applicationContext,User.class);

        // 进行依赖查找
        final Map<String, Config> configBeans = applicationContext.getBeansOfType(Config.class);
        System.out.println("Config 类型的所有的Bean" + configBeans);
         Map<String, User> userBeans = applicationContext.getBeansOfType(User.class);
        System.out.println("User 类型的所有的Bean" + userBeans);
        // 显式关闭Spring应用上下文
        applicationContext.close();
    }

    /**
     * 命名Bean 的注册方式
     * @param registry
     * @param beanName
     * @param beanClass
     */
    public static void registerBeanDefinition(BeanDefinitionRegistry registry,String beanName, Class<?> beanClass){
        BeanDefinitionBuilder beanDefinitionBuilder = BeanDefinitionBuilder.genericBeanDefinition(beanClass);
        beanDefinitionBuilder.addPropertyValue("id",1).addPropertyValue("name","kuifir");
        // 注册BeanDefinition
        registry.registerBeanDefinition(beanName,beanDefinitionBuilder.getBeanDefinition());
    }

    /**
     * 非命名 Bean 的注册方式
     * @param registry
     * @param beanClass
     */
    public static void registerBeanDefinition(BeanDefinitionRegistry registry, Class<?> beanClass){
        BeanDefinitionBuilder beanDefinitionBuilder = BeanDefinitionBuilder.genericBeanDefinition(beanClass);
        beanDefinitionBuilder.addPropertyValue("id",1).addPropertyValue("name","kuifir");
        //注册BeanDefinition
        BeanDefinitionReaderUtils.registerWithGeneratedName(beanDefinitionBuilder.getBeanDefinition(),registry);
    }


    // 2. 通过@Component
    @Component // 可以定义当前类作为SpringBean(组件)
    public static class Config{
        // 1. 通过@Bean 方式定义
        @Bean(name = {"user", "kuifir-user"})
        public User user(){
            User user = new User();
            user.setId(11L);
            user.setName("kuifir");
            return user;
        }
    }

}
