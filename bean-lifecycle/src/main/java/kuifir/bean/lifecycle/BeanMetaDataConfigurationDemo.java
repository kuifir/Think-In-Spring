package kuifir.bean.lifecycle;

import kuifir.ioc.overview.domain.User;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.beans.factory.support.PropertiesBeanDefinitionReader;
import org.springframework.beans.factory.xml.XmlBeanDefinitionReader;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.EncodedResource;

/**
 * Package: kuifir.bean.lifecycle
 * <p>
 * Description： Bean 元信息配置
 * <p>
 * Author: baci
 * <p>
 * Date: Created in 2022/12/31 20:41
 * <p>
 * Version: 0.0.1
 */
public class BeanMetaDataConfigurationDemo {
    public static void main(String[] args) {
        // 创建beanFactory容器
        DefaultListableBeanFactory beanFactory = new DefaultListableBeanFactory();
        // 实例化基于Properties 资源 BeanDefinitionReader
        PropertiesBeanDefinitionReader propertiesBeanDefinitionReader = new PropertiesBeanDefinitionReader(beanFactory);
        // 加载properties 资源,为解决编码问题，用Resource进行读取
        String propertiesLocation = "META-INF/user.properties";
        Resource resource = new ClassPathResource(propertiesLocation);
        EncodedResource encodedResource = new EncodedResource(resource, "GBK");
        // 注册BeanDefinition
        int propertiesBeanNumber = propertiesBeanDefinitionReader.loadBeanDefinitions(encodedResource);
        System.out.println("加载Properties 中 Bean数量 " + propertiesBeanNumber);
        // 通过id和类型进行依赖查找
        User user = beanFactory.getBean("user", User.class);
        System.out.println(user);


        String xmlLocation = "classpath:/META-INF\\dependency-lookup-context.xml";
        XmlBeanDefinitionReader xmlBeanDefinitionReader = new XmlBeanDefinitionReader(beanFactory);
        int xmlBeanNumber = xmlBeanDefinitionReader.loadBeanDefinitions(xmlLocation);
        System.out.println("加载XML 中 Bean数量 " + xmlBeanNumber);
        user = beanFactory.getBean("user", User.class);
        System.out.println(user);
    }
}
