package kuifir.denpendency.lookup;

import org.springframework.beans.factory.BeanCreationException;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import javax.annotation.PostConstruct;

/**
 * Package: kuifir.denpendency.lookup
 * <p>
 * Description： {@link BeanCreationException} 示例
 * Bean初始化过程中抛出异常
 * <p>
 * Author: baci
 * <p>
 * Date: Created in 2022/9/8 22:43
 * <p>
 * Version: 0.0.1
 */
public class BeanCreationExceptionDemo {
    public static void main(String[] args) {
        // 创建beanFactory对象
        AnnotationConfigApplicationContext applicationContext = new AnnotationConfigApplicationContext();
        // 注册一个BeanDefinition Bean Class 是一个POJO普通类，不过初始化方法回调时抛出异常
        BeanDefinitionBuilder beanDefinitionBuilder = BeanDefinitionBuilder.genericBeanDefinition(POJO.class);
        applicationContext.registerBeanDefinition("errorBean",beanDefinitionBuilder.getBeanDefinition());
        // 应用上下文
        applicationContext.refresh();
        // 关闭上下文
        applicationContext.close();
    }

    private static class POJO implements InitializingBean {

        @PostConstruct
        public void init() throws Throwable {
            throw new Exception("init(): For purposes...");
        }
        @Override
        public void afterPropertiesSet() throws Exception {
            throw new Exception("afterPropertiesSet ： For purposes...");
        }
    }
}
