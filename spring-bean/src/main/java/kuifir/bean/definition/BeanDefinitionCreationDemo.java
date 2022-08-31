package kuifir.bean.definition;

import kuifir.ioc.overview.domain.User;
import org.springframework.beans.MutablePropertyValues;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.support.AbstractBeanDefinition;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.support.GenericBeanDefinition;

/**
 * Package: kuifir.bean.definition
 * <p>
 * {@link BeanDefinition}构建示例
 * <p>
 * Author: baci
 * <p>
 * Date: Created in 2022/8/25 22:48
 * <p>
 * Version: 0.0.1
 */
public class BeanDefinitionCreationDemo {
    public static void main(String[] args) {
        // 1、通过BeanDefinitionBuilder方式构建
        BeanDefinitionBuilder beanDefinitionBuilder = BeanDefinitionBuilder.genericBeanDefinition(User.class);
        // 通过属性设置
//        beanDefinitionBuilder.addPropertyValue("id",1);
//        beanDefinitionBuilder.addPropertyValue("name","kuifir");
        beanDefinitionBuilder.addPropertyValue("id", 1).addPropertyValue("name", "kuifir");
        // 获取BeanDefinition实例
        BeanDefinition beanDefinition = beanDefinitionBuilder.getBeanDefinition();
        // BeanDefinition 并非Bean最终状态，可以自定义修改

        // 2.直接构建 通过AbstractBeanDefinition 以及派生类
        GenericBeanDefinition genericBeanDefinition = new GenericBeanDefinition();
        // 设置Bean类别
        genericBeanDefinition.setBeanClass(User.class);
        // 通过MutablePropertyValues 批量操作属性
        MutablePropertyValues propertyValues = new MutablePropertyValues();
//        propertyValues.addPropertyValue("id",1);
//        propertyValues.addPropertyValue("name","kuifir");
        propertyValues.add("id", 1).add("name", "kuifir");
        // 设置set MutablePropertyValues属性
        genericBeanDefinition.setPropertyValues(propertyValues);
    }
}
