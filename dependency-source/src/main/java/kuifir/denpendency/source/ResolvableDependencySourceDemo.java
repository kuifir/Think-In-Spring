package kuifir.denpendency.source;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import javax.annotation.PostConstruct;

/**
 * Package: kuifir.denpendency.source
 * <p>
 * Description： TODO
 * <p>
 * Author: baci
 * <p>
 * Date: Created in 2022/12/30 16:46
 * <p>
 * Version: 0.0.1
 */
public class ResolvableDependencySourceDemo {

    @Autowired
    private String value;

    @PostConstruct
    public void print() {
        System.out.println(value);
    }

    public static void main(String[] args) {
        AnnotationConfigApplicationContext applicationContext = new AnnotationConfigApplicationContext();
        applicationContext.register(ResolvableDependencySourceDemo.class);
//        applicationContext.getBeanFactory().registerResolvableDependency(String.class,"hello, world");
        applicationContext.addBeanFactoryPostProcessor(beanFactory -> beanFactory.registerResolvableDependency(String.class, "hello, world"));
        applicationContext.refresh();
        applicationContext.close();
    }
}
