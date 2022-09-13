package kuifir.ioc.dependency;

import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.beans.factory.xml.XmlBeanDefinitionReader;

/**
 * Package: kuifir.ioc.dependency
 * <p>
 * Description： 基于XML资源的依赖 Constructor 注入示例
 * <p>
 * Author: baci
 * <p>
 * Date: Created in 2022/9/12 21:55
 * <p>
 * Version: 0.0.1
 */
public class XmlDependencySetteConstructorInjectionDemo {
    public static void main(String[] args) {
        DefaultListableBeanFactory beanFactory = new DefaultListableBeanFactory();

        XmlBeanDefinitionReader beanDefinitionReader = new XmlBeanDefinitionReader(beanFactory);

        // 加载xml资源，解析并且生成BeanDefinition
        String xmlResourcePath = "classpath:/META-INF\\dependency-constructor-injection.xml";
        beanDefinitionReader.loadBeanDefinitions(xmlResourcePath);
        // 创建以来查找（过程中会创建Bean）
        UserHolder userHolder = beanFactory.getBean(UserHolder.class);
        System.out.println(userHolder);
    }
}
