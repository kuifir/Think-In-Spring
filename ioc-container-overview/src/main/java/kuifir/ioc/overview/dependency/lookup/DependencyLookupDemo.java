package kuifir.ioc.overview.dependency.lookup;

import kuifir.ioc.overview.annotation.Super;
import kuifir.ioc.overview.domain.User;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.ListableBeanFactory;
import org.springframework.beans.factory.ObjectFactory;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.util.Map;

/**
 * Package: kuifir.ioc.overview.dependency.lookup
 * <p>
 * Description：
 * 依赖查找示例
 * 1、名称查找
 * 2、类型查找
 * <p>
 * Author: baci
 * <p>
 * Date: Created in 2022/8/21 20:58
 * <p>
 * Version: 0.0.1
 */
public class DependencyLookupDemo {
    public static void main(String[] args) {
        BeanFactory beanFactory = new ClassPathXmlApplicationContext("classpath:\\META-INF\\dependency-lookup-context.xml");
        // 名称查找
//        lookupInRealTime(beanFactory);
//        lookupInLazyTime(beanFactory);
        // 类型查找
        lookupByType(beanFactory);
        // 类型查找集合
        lookupCollectionByType(beanFactory);
        // 通过注解查找
        lookupByAnnotationType(beanFactory);

    }

    private static void lookupByAnnotationType(BeanFactory beanFactory) {
        if(beanFactory instanceof ListableBeanFactory){
            ListableBeanFactory listableBeanFactory = (ListableBeanFactory) beanFactory;
            Map<String, User> users = (Map) listableBeanFactory.getBeansWithAnnotation(Super.class);
            System.out.println("查找到所有的 User 集合对象"+users);
        }
    }

    private static void lookupCollectionByType(BeanFactory beanFactory) {
        if(beanFactory instanceof ListableBeanFactory){
            ListableBeanFactory listableBeanFactory = (ListableBeanFactory) beanFactory;
            Map<String, User> users = listableBeanFactory.getBeansOfType(User.class);
            System.out.println("查找到所有的 User 集合对象"+users);
        }
    }

    private static void lookupByType(BeanFactory beanFactory) {
        final User user = beanFactory.getBean(User.class);
        System.out.println("实时查找"+user);

    }

    private static void lookupInLazyTime(BeanFactory beanFactory) {
        ObjectFactory<User> objectFactory = beanFactory.getBean("objectFactory",ObjectFactory.class);
        final User user = objectFactory.getObject();
        System.out.println("延迟查找"+user);
    }

    private static void lookupInRealTime(BeanFactory beanFactory){
        User user = beanFactory.getBean("user", User.class);
        System.out.println("实时查找"+user);
    }
}
