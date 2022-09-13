package kuifir.ioc.dependency;

import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.beans.factory.xml.XmlBeanDefinitionReader;

/**
 * Package: kuifir.bean.definition
 * <p>
 * Description： byName AutoWiring 依赖 Setter 方法注入示例
 * <p>
 * Author: baci
 * <p>
 * Date: Created in 2022/9/12 23:16
 * <p>
 * Version: 0.0.1
 */
public class AutoWiringByConstructorDependencyInjectionDemo {
    public static void main(String[] args) {
        DefaultListableBeanFactory beanFactory = new DefaultListableBeanFactory();
        XmlBeanDefinitionReader beanDefinitionReader = new XmlBeanDefinitionReader(beanFactory);
        String xmlResourcePath = "classpath:/META-INF/autowiring-dependency-constructor-injection.xml";
        beanDefinitionReader.loadBeanDefinitions(xmlResourcePath);
        final UserHolder userHolder = beanFactory.getBean(UserHolder.class);
        System.out.println(userHolder);
    }
}
