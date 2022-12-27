package kuifir.denpendency.lookup;

import kuifir.ioc.overview.domain.User;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.ListableBeanFactory;
import org.springframework.beans.factory.ObjectFactory;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Configuration;

/**
 * Package: kuifir.denpendency.lookup
 * <p>
 * Description： 类型安全的依赖查找示例
 * <p>
 * Author: baci
 * <p>
 * Date: Created in 2022/9/6 22:15
 * <p>
 * Version: 0.0.1
 */
@Configuration
public class TypeSafetyDependencyLookupDemo {
    public static void main(String[] args) {
        // 创建 BeanFactory 容器
        AnnotationConfigApplicationContext applicationContext = new AnnotationConfigApplicationContext();
        // 将当前类作为配置类
        applicationContext.register(TypeSafetyDependencyLookupDemo.class);
        // 启动应用上下文
        applicationContext.refresh();
        // 演示BeanFactory#getBean的方法的安全性
        displayBeanFactoryGetBean(applicationContext);
        // 演示ObjectFactory#getObject的方法的安全性
        displayObjectFactoryGetObject(applicationContext);
        // 演示ObjectProvider#getIfAvailable的方法的安全性
        displayObjectProviderIfAvailable(applicationContext);
        // 演示ListableBeanFactory#getBeansOfType 方法的安全性
        displayListableBeanFactoryGetBeansOfType(applicationContext);
        // 演示ObjectProvider#Steam 安全性
        displayObjectProviderStreamOps(applicationContext);
        // 关闭应用上下文
        applicationContext.close();
    }

    public static void displayBeanFactoryGetBean(BeanFactory beanFactory) {
        printBeanException("displayBeanFactoryGetBean", () -> beanFactory.getBean(User.class));
    }

    private static void displayObjectFactoryGetObject(AnnotationConfigApplicationContext applicationContext) {
        // ObjectProvider is ObjectFactory
        ObjectFactory<User> objectFactory = applicationContext.getBeanProvider(User.class);
        printBeanException("displayObjectFactoryGetObject", () -> objectFactory.getObject());
    }

    private static void displayObjectProviderIfAvailable(AnnotationConfigApplicationContext applicationContext) {
        ObjectProvider<User> objectProvider = applicationContext.getBeanProvider(User.class);
        printBeanException("displayObjectProviderIfAvailable", () -> objectProvider.getIfAvailable());
    }

    private static void displayListableBeanFactoryGetBeansOfType(ListableBeanFactory beanFactory) {
        printBeanException("displayListableBeanFactoryBeansOfType", ()->beanFactory.getBeansOfType(User.class));
    }

    private static void displayObjectProviderStreamOps(AnnotationConfigApplicationContext applicationContext) {
        ObjectProvider<User> objectProvider = applicationContext.getBeanProvider(User.class);
        printBeanException("displayObjectProviderStreamOps", ()-> objectProvider.stream().forEach(System.out::println));
    }


    private static void printBeanException(String description, Runnable runnable) {
        System.err.println("========================================");
        System.err.println("source from: "+description);
        try {
            runnable.run();
        } catch (BeansException e) {
            e.printStackTrace();
        }
    }

}
