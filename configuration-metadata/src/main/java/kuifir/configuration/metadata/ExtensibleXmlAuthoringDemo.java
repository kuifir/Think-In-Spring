package kuifir.configuration.metadata;

import kuifir.ioc.overview.domain.User;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.beans.factory.xml.XmlBeanDefinitionReader;

/**
 * Package: kuifir.configuration.metadata
 * <p>
 * Description： TODO
 * <p>
 * Author: baci
 * <p>
 * Date: Created in 2023/1/4 16:53
 * <p>
 * Version: 0.0.1
 */
public class ExtensibleXmlAuthoringDemo {
    public static void main(String[] args) {
        // 创建beanFactory容器
        DefaultListableBeanFactory beanFactory = new DefaultListableBeanFactory();
        String xmlLocation = "classpath:/META-INF/users-context.xml";
        XmlBeanDefinitionReader xmlBeanDefinitionReader = new XmlBeanDefinitionReader(beanFactory);
        int xmlBeanNumber = xmlBeanDefinitionReader.loadBeanDefinitions(xmlLocation);
        System.out.println("加载XML 中 Bean数量 " + xmlBeanNumber);
        User user = beanFactory.getBean(User.class);
        System.out.println(user);
    }
}
