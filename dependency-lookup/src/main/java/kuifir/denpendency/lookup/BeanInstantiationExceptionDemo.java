package kuifir.denpendency.lookup;

import org.springframework.beans.BeanInstantiationException;
import org.springframework.beans.factory.support.AbstractBeanDefinition;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.support.BeanDefinitionReader;
import org.springframework.beans.factory.support.BeanDefinitionReaderUtils;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

/**
 * Package: kuifir.denpendency.lookup
 * <p>
 * Description： {@link BeanInstantiationException}示例
 * 当初始化接口或抽象类（非实例化）对象时会报错
 * <p>
 * Author: baci
 * <p>
 * Date: Created in 2022/9/8 22:24
 * <p>
 * Version: 0.0.1
 */
public class BeanInstantiationExceptionDemo {
    public static void main(String[] args) {
        // 创建 容器
        AnnotationConfigApplicationContext applicationContext = new AnnotationConfigApplicationContext();
        // 注册 BeanDefinition Bean Class是一个CharSequence接口
        BeanDefinitionBuilder beanDefinitionBuilder = BeanDefinitionBuilder.genericBeanDefinition(CharSequence.class);
        applicationContext.registerBeanDefinition("errorBean",beanDefinitionBuilder.getBeanDefinition());
        // 启动应用上下文
        applicationContext.refresh();
        // 关闭应用上下文
        applicationContext.close();
    }
}
