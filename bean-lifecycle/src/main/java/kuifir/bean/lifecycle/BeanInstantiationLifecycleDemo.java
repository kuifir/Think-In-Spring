package kuifir.bean.lifecycle;

import kuifir.ioc.overview.domain.SuperUser;
import kuifir.ioc.overview.domain.User;
import org.springframework.beans.BeansException;
import org.springframework.beans.MutablePropertyValues;
import org.springframework.beans.PropertyValues;
import org.springframework.beans.factory.config.InstantiationAwareBeanPostProcessor;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.beans.factory.xml.XmlBeanDefinitionReader;
import org.springframework.util.ObjectUtils;

/**
 * Package: kuifir.bean.lifecycle
 * <p>
 * Description： Bean实例化生命周期示例
 * <p>
 * Author: baci
 * <p>
 * Date: Created in 2023/1/1 15:18
 * <p>
 * Version: 0.0.1
 */
public class BeanInstantiationLifecycleDemo {
    public static void main(String[] args) {
        // 创建beanFactory容器
        DefaultListableBeanFactory beanFactory = new DefaultListableBeanFactory();

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
        String[] xmlLocation = {"classpath:/META-INF\\dependency-lookup-context.xml","META-INF\\bean-constructor-dependency-injection.xml"};
        XmlBeanDefinitionReader xmlBeanDefinitionReader = new XmlBeanDefinitionReader(beanFactory);
        int xmlBeanNumber = xmlBeanDefinitionReader.loadBeanDefinitions(xmlLocation);
        System.out.println("加载XML 中 Bean数量 " + xmlBeanNumber);
        User user = beanFactory.getBean("user", User.class);
        System.out.println(user);
        User superUser = beanFactory.getBean("superUser", User.class);
        System.out.println(superUser);
        UserHolder userHolder = beanFactory.getBean("userHolder", UserHolder.class);
        System.out.println(userHolder);
    }
}
