package kuifir.bean.lifecycle;

import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.context.annotation.AnnotatedBeanDefinitionReader;

/**
 * Package: kuifir.bean.lifecycle
 * <p>
 * Description： 注解BeanDefinition解析示例
 * <p>
 * Author: baci
 * <p>
 * Date: Created in 2022/12/31 21:36
 * <p>
 * Version: 0.0.1
 */
public class AnnotatedBeanDefinitionPasringDemo {
    public static void main(String[] args) {
        // 创建beanFactory容器
        DefaultListableBeanFactory beanFactory = new DefaultListableBeanFactory();
        // 基于注解的 BeanDefinitionReader ->AnnotatedBeanDefinitionReader
        AnnotatedBeanDefinitionReader annotatedBeanDefinitionReader = new AnnotatedBeanDefinitionReader(beanFactory);
        // 注册当前类（不要求 @Component 注解标注的类）
        int registerBefore = beanFactory.getBeanDefinitionCount();
        annotatedBeanDefinitionReader.register(AnnotatedBeanDefinitionPasringDemo.class);
        int registerAfter = beanFactory.getBeanDefinitionCount();
        System.out.println("加载 注解 Bean数量 " + (registerAfter-registerBefore));
        // 普通class 作为 Component 注册到Spring IOC 容器后，通常 Bean名称为 类名首字母小写-> annotatedBeanDefinitionPasringDemo
        AnnotatedBeanDefinitionPasringDemo demo = beanFactory.getBean("annotatedBeanDefinitionPasringDemo",AnnotatedBeanDefinitionPasringDemo.class);

        System.out.println(demo);
    }
}
