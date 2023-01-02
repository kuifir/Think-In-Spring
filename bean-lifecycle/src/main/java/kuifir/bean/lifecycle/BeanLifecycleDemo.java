package kuifir.bean.lifecycle;

import kuifir.ioc.overview.domain.SuperUser;
import kuifir.ioc.overview.domain.User;
import org.springframework.beans.BeansException;
import org.springframework.beans.MutablePropertyValues;
import org.springframework.beans.PropertyValues;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.beans.factory.config.DestructionAwareBeanPostProcessor;
import org.springframework.beans.factory.config.InstantiationAwareBeanPostProcessor;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.beans.factory.xml.XmlBeanDefinitionReader;
import org.springframework.context.annotation.CommonAnnotationBeanPostProcessor;
import org.springframework.util.ObjectUtils;

/**
 * Package: kuifir.bean.lifecycle
 * <p>
 * Description： TODO
 * <p>
 * Author: baci
 * <p>
 * Date: Created in 2023/1/2 21:53
 * <p>
 * Version: 0.0.1
 */
public class BeanLifecycleDemo {
    public static void main(String[] args) {
        // 创建beanFactory容器
        DefaultListableBeanFactory beanFactory = new DefaultListableBeanFactory();

        // 实例化 前中后
        beanFactory.addBeanPostProcessor(new InstantiationAwareBeanPostProcessor() {
            // 实例化前的回调
            @Override
            public Object postProcessBeforeInstantiation(Class<?> beanClass, String beanName) throws BeansException {
                if (ObjectUtils.nullSafeEquals("superUser", beanName) && SuperUser.class.equals(beanClass)) {
                    return new SuperUser();
                }
                return null; // 保持Spring IoC容器的实例化操作
            }
            // 实例化后的回调
            @Override
            public boolean postProcessAfterInstantiation(Object bean, String beanName) throws BeansException {
                if (ObjectUtils.nullSafeEquals("user", beanName) && User.class.equals(bean.getClass())) {
                    // 此时属性还没有赋值，返回false 表示对象不允许属性赋值（将元信息填入到属性指）
                    return false;
                }
                return true;
            }

            // postProcessBeforeInstantiation()在bean实例化前回调,返回实例则不对bean实例化,返回null则进行spring bean实例化(doCreateBean);
            // postProcessAfterInstantiation()在bean实例化后在填充bean属性之前回调,返回true则进行下一步的属性填充,返回false:则不进行属性填充
            // postProcessProperties在属性赋值前的回调在applyPropertyValues之前操作可以对属性添加或修改等操作最后在通过applyPropertyValues应用bean对应的wapper对象
            @Override
            public PropertyValues postProcessProperties(PropertyValues pvs, Object bean, String beanName) throws BeansException {
                // superUser 万群跳过Bean实例化
                // user 跳过Bean属性填入
                // userHolder可以进入这个生命周期
                if (ObjectUtils.nullSafeEquals("userHolder", beanName) && UserHolder.class.equals(bean.getClass())) {
                    final MutablePropertyValues mutablePropertyValues;
                    if(pvs instanceof MutablePropertyValues){
                        mutablePropertyValues = (MutablePropertyValues) pvs;
                    }else {
                        mutablePropertyValues = new MutablePropertyValues();
                    }
                    // 等价于 <property name="number" value="1"/>
                    mutablePropertyValues.add("number","1");
                    // 修改配置 <property name="description" value="the User Holder"/>
                    if (mutablePropertyValues.contains("description")) {
                        mutablePropertyValues.removePropertyValue("description");
                        mutablePropertyValues.addPropertyValue("description","the User Holder V2");
                    }

                }
                return null;
            }
        });
        // 初始化 前中后
        beanFactory.addBeanPostProcessor(new BeanPostProcessor() {
            @Override
            public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
                if(ObjectUtils.nullSafeEquals("userHolder", beanName) && bean.getClass().equals(UserHolder.class)){
                    UserHolder userHolder = (UserHolder)bean;
                    userHolder.setDescription("the User Holder V3");
                }
                // 返回null 没有执行User的初始化的@PostConstruct标注的方法
                return bean;
            }

            @Override
            public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
                if(ObjectUtils.nullSafeEquals("userHolder", beanName) && bean.getClass().equals(UserHolder.class)){
                    UserHolder userHolder = (UserHolder)bean;
                    // 自定义初始化方法 V6 -> postProcessAfterInitialization V7
                    userHolder.setDescription("the User Holder V7");
                    System.out.println(userHolder.getDescription());
                }
                return bean;
            }
        });

        // 销毁前
        beanFactory.addBeanPostProcessor((DestructionAwareBeanPostProcessor) (bean, beanName) -> {
            if(ObjectUtils.nullSafeEquals("userHolder", beanName) && bean.getClass().equals(UserHolder.class)){
                UserHolder userHolder = (UserHolder)bean;
                // SmartInitializingSingleton#afterSingletonsInstantiated V8 -> DestructionAwareBeanPostProcessor#postProcessBeforeDestruction V9
                userHolder.setDescription("the User Holder V9");
                System.out.println(userHolder.getDescription());
            }
        });
        // 添加 CommonAnnotationBeanPostProcessor 解决 @PostConstruct @PreDestroy
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
        // 销毁Bean 并不意味这个Bean 被垃圾回收了，在当前容器内被销毁
        beanFactory.destroyBean("userHolder",userHolder);
        System.out.println(userHolder);
    }
}
