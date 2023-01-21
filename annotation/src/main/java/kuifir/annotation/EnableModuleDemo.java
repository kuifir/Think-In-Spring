package kuifir.annotation;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;

/**
 * Package: kuifir.annotation
 * <p>
 * Description：  Enable 模块驱动示例
 * <p>
 * Author: baci
 * <p>
 * Date: Created in 2023/1/22 0:32
 * <p>
 * Version: 0.0.1
 */
@EnableHelloWorld
public class EnableModuleDemo {
    public static void main(String[] args) {
        AnnotationConfigApplicationContext applicationContext = new AnnotationConfigApplicationContext();
        applicationContext.register(EnableModuleDemo.class);
        applicationContext.refresh();
        String helloWorld = applicationContext.getBean("helloWorld", String.class);
        System.out.println(helloWorld);
        applicationContext.close();
    }
}
