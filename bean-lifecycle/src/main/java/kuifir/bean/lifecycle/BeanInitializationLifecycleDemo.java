package kuifir.bean.lifecycle;

import kuifir.ioc.overview.domain.User;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.beans.factory.xml.XmlBeanDefinitionReader;
import org.springframework.context.annotation.CommonAnnotationBeanPostProcessor;
import org.springframework.util.ObjectUtils;

/**
 * Package: kuifir.bean.lifecycle
 * <p>
 * Description： Bean 初始化生命周期实例
 * <p>
 * Author: baci
 * <p>
 * Date: Created in 2023/1/2 15:06
 * <p>
 * Version: 0.0.1
 */
public class BeanInitializationLifecycleDemo {
    public static void main(String[] args) {
        // 创建beanFactory容器
        DefaultListableBeanFactory beanFactory = new DefaultListableBeanFactory();

        beanFactory.addBeanPostProcessor(new BeanPostProcessor() {
            @Override
            public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
                if(ObjectUtils.nullSafeEquals("userHolder", beanName) && bean.getClass().equals(UserHolder.class)){
                    UserHolder userHolder = (UserHolder)bean;
                    userHolder.setDescription("the User Holder V3");
                    return bean;
                }
                return null;
            }

            @Override
            public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
                if(ObjectUtils.nullSafeEquals("userHolder", beanName) && bean.getClass().equals(UserHolder.class)){
                    UserHolder userHolder = (UserHolder)bean;
                    // 自定义初始化方法 V6 -> postProcessAfterInitialization V7
                    userHolder.setDescription("the User Holder V7");
                    System.out.println(userHolder.getDescription());
                    return bean;
                }
                return null;
            }
        });
        // 添加 CommonAnnotationBeanPostProcessor 解决 @PostConstruct
        beanFactory.addBeanPostProcessor(new CommonAnnotationBeanPostProcessor());
        String[] xmlLocation = {"classpath:/META-INF\\dependency-lookup-context.xml","META-INF\\bean-constructor-dependency-injection.xml"};
        XmlBeanDefinitionReader xmlBeanDefinitionReader = new XmlBeanDefinitionReader(beanFactory);
        int xmlBeanNumber = xmlBeanDefinitionReader.loadBeanDefinitions(xmlLocation);
        // 显式调用preInstantiateSingletons
        // SmartInitializingSingleton 通常在 Spring　ApplicationContext 场景使用
        // preInstantiateSingletons 将已经注册的 BeanDefinition 初始化成 Spring Bean
        beanFactory.preInstantiateSingletons();
        System.out.println("加载XML 中 Bean数量 " + xmlBeanNumber);
        User user = beanFactory.getBean("user", User.class);
        System.out.println(user);
        User superUser = beanFactory.getBean("superUser", User.class);
        System.out.println(superUser);
        UserHolder userHolder = beanFactory.getBean("userHolder", UserHolder.class);
        System.out.println(userHolder);
    }
}
