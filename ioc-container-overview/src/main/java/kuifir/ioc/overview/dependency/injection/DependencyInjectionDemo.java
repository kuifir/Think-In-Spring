package kuifir.ioc.overview.dependency.injection;

import kuifir.ioc.overview.domain.User;
import kuifir.ioc.overview.repository.UserRepository;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.ObjectFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.core.env.Environment;

/**
 * Package: kuifir.ioc.overview.dependency.lookup
 * <p>
 * Description：
 * 依赖注入示例
 *  1、根据名称注入
 *  2、根据类型注入
 *    - 单个Bean对象
 *    - 集合Bean对象
 *  3、注入容器内建Bean对象
 *  4、注入非Bean对象
 *  5、注入类型
 *    - 实时注入
 *    - 延迟注入
 * <p>
 * Author: baci
 * <p>
 * Date: Created in 2022/8/21 20:58
 * <p>
 * Version: 0.0.1
 */
public class DependencyInjectionDemo {
    public static void main(String[] args) {
        BeanFactory beanFactory = new ClassPathXmlApplicationContext("classpath:\\META-INF\\dependency-injection-context.xml");
        // 来源一:自定义Bean
        UserRepository userRepository = beanFactory.getBean("userRepository", UserRepository.class);
        System.out.println(userRepository.getUsers());
        // 来源二:依赖注入(内建依赖)
        System.out.println(userRepository.getBeanFactory());
        // 为什么是false 
        System.out.println(userRepository.getBeanFactory() == beanFactory);
        // 依赖查找(报错)
//        System.out.println(beanFactory.getBean(BeanFactory.class));

        ObjectFactory<User> userObjectFactory = userRepository.getUserObjectFactory();
        User user = userObjectFactory.getObject();
        System.out.println(user);

        ObjectFactory<ApplicationContext> objectFactory = userRepository.getObjectFactory();
        System.out.println(objectFactory.getObject());
        System.out.println(objectFactory.getObject()==beanFactory);
        // 来源三 容器内建的Bean
        Environment environment = beanFactory.getBean(Environment.class);
        System.out.println("获取Environment 类型Bean"+environment);
    }

}
