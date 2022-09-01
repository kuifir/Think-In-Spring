package kuifir.bean.definition;

import kuifir.bean.factory.UserFactory;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.util.Iterator;
import java.util.ServiceLoader;
import java.util.function.Consumer;

/**
 * Package: kuifir.bean.definition
 * <p>
 * Description： TODO
 * <p>
 * Author: baci
 * <p>
 * Date: Created in 2022/9/1 21:19
 * <p>
 * Version: 0.0.1
 */
public class SpecialBeanInstantiationDemo {

    public static void main(String[] args) {
        // 配置xml配置文件
        BeanFactory beanFactory = new ClassPathXmlApplicationContext("classpath:\\META-INF\\special-bean-instantiation-context.xml");
        ServiceLoader<UserFactory> serviceLoader = beanFactory.getBean("userFactoryServiceLoader",ServiceLoader.class);
        displayServiceLoader(serviceLoader, userFactory -> System.out.println(userFactory.createUser()));

        demoServiceLoder();
    }
    public static void demoServiceLoder(){
        ServiceLoader<UserFactory> serviceLoader = ServiceLoader.load(UserFactory.class, Thread.currentThread().getContextClassLoader());
        displayServiceLoader(serviceLoader, userFactory -> System.out.println(userFactory.createUser()));
    }
    private static <T> void displayServiceLoader(ServiceLoader<T> serviceLoader, Consumer<T> consumer){
        Iterator<T> iterator = serviceLoader.iterator();
        while (iterator.hasNext()){
            T t = iterator.next();
            consumer.accept(t);
        }
    }
}
