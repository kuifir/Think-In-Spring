package kuifir.annotation;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.ComponentScan;

/**
 * Package: kuifir.annotation
 * <p>
 * Description： Spring 注解属性覆盖示例
 * <p>
 * Author: baci
 * <p>
 * Date: Created in 2023/1/22 1:14
 * <p>
 * Version: 0.0.1
 */
//@MyComponentScan(scanBasePackages = "kuifir.annotation")
@MyComponentScan2(basePackages = "kuifir.dd")
// @MyComponentScan2.scanBasePackages  <- @MyComponentScan.scanBasePackages 隐性覆盖
public class AttributeOverridesDemo {
    public static void main(String[] args) {
        AnnotationConfigApplicationContext applicationContext = new AnnotationConfigApplicationContext();
        applicationContext.register(AttributeOverridesDemo.class);
        applicationContext.refresh();

        TestClass bean = applicationContext.getBean(TestClass.class);
        System.out.println(bean);
        applicationContext.close();
    }
}
