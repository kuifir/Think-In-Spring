package kuifir.configuration.metadata;

import kuifir.ioc.overview.domain.User;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.beans.factory.support.AbstractBeanDefinition;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.util.ObjectUtils;

/**
 * Package: kuifir.configuration.metadata
 * <p>
 * Description： Bean 配置元信息示例
 * <p>
 * Author: baci
 * <p>
 * Date: Created in 2023/1/3 15:16
 * <p>
 * Version: 0.0.1
 */
public class BeanConfigurationMetadataDemo {

    public static void main(String[] args) {
        // BeanDefinition的定义
        BeanDefinitionBuilder beanDefinitionBuilder = BeanDefinitionBuilder.genericBeanDefinition(User.class);
        beanDefinitionBuilder.addPropertyValue("name","kuifir");
        AbstractBeanDefinition beanDefinition = beanDefinitionBuilder.getBeanDefinition();
        // 附加的属性（不影响 Bean 的实例化，属性赋值，初始化阶段 -> populate,initialize）
        beanDefinition.setAttribute("name","奎");
        // 当前BeanDefinition来自何方(附加属性，辅助作用)
        beanDefinition.setSource(BeanConfigurationMetadataDemo.class);
        DefaultListableBeanFactory beanFactory = new DefaultListableBeanFactory();
        // 可以在回调阶段获取，属性进行一些设置
        beanFactory.addBeanPostProcessor(new BeanPostProcessor() {
            @Override
            public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
                if(ObjectUtils.nullSafeEquals("userTest",beanName) && bean.getClass().equals(User.class)){
                    BeanDefinition bd = beanFactory.getBeanDefinition(beanName);
                    ((User) bean).setName((String) bd.getAttribute("name"));
                }
                return bean;
            }
        });
        beanFactory.registerBeanDefinition("userTest", beanDefinition);
        User user = beanFactory.getBean("userTest",User.class);
        System.out.println(user);
    }
}
