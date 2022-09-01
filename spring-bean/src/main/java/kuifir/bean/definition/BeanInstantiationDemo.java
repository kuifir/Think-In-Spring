package kuifir.bean.definition;

import kuifir.ioc.overview.domain.User;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.ListableBeanFactory;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.util.Map;

/**
 * Package: kuifir.bean.definition
 * <p>
 * Description： Bean实例化实例
 * <p>
 * Author: baci
 * <p>
 * Date: Created in 2022/8/31 23:35
 * <p>
 * Version: 0.0.1
 */
public class BeanInstantiationDemo {
    public static void main(String[] args) {
        // 配置xml配置文件
        BeanFactory beanFactory = new ClassPathXmlApplicationContext("classpath:\\META-INF\\bean-instantiation-context.xml");
        User user = beanFactory.getBean("user-by-static-method",User.class);
        System.out.println(user);
        User userByInstanceMethod = beanFactory.getBean("user-by-instance-method",User.class);
        System.out.println(userByInstanceMethod);
        System.out.println(user == userByInstanceMethod);
        User userByFactorBean = beanFactory.getBean("user-by-factory-bean",User.class);
        System.out.println(userByFactorBean);
        System.out.println(user == userByFactorBean);
        System.out.println(userByInstanceMethod == userByFactorBean);

        ListableBeanFactory listableBeanFactory = (ListableBeanFactory)beanFactory;
        final Map<String, User> beansOfType = listableBeanFactory.getBeansOfType(User.class);
        beansOfType.forEach((k,v)-> System.out.println(k+"---"+v));
    }

    public static User createUser() {
        User user = new User();
        user.setName("kuifir");
        user.setId(1L);
        return user;
    }
}
