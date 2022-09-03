package kuifir.bean.definition;

import kuifir.bean.factory.DefaultUserFactory;
import kuifir.bean.factory.UserFactory;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

/**
 * Package: kuifir.bean.definition
 * <p>
 * Description： 单体 Bean 注册示例
 * <p>
 * Author: baci
 * <p>
 * Date: Created in 2022/9/3 16:51
 * <p>
 * Version: 0.0.1
 */
public class SingletonBeanRegistrationDemo {
    public static void main(String[] args) {
        // 创建 BeanFactory 容器
        AnnotationConfigApplicationContext applicationContext = new AnnotationConfigApplicationContext();
        // 创建一个外部单例对象(对象的生命周期不由Spring管理，但是可以被托管)
        UserFactory userFactory = new DefaultUserFactory();// 这个对象的创建和Spring容器没有关系
        // 注册 外部单例对象
        ConfigurableListableBeanFactory beanFactory = applicationContext.getBeanFactory();
        beanFactory.registerSingleton("userFactory",userFactory);
        // 启动应用上下文
        applicationContext.refresh();
        // 通过依赖查找获取UserFactory
        UserFactory userFactoryByLookup = beanFactory.getBean("userFactory", UserFactory.class);
        System.out.println(userFactoryByLookup);
        System.out.println("userFactoryByLookup == userFactory : " + (userFactoryByLookup == userFactory));
        // 关闭应用上下文呢
        applicationContext.close();
    }
}
