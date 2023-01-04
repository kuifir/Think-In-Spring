package kuifir.configuration.metadata;

import kuifir.ioc.overview.domain.User;
import org.springframework.beans.factory.config.YamlMapFactoryBean;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.beans.factory.xml.XmlBeanDefinitionReader;

import java.util.Map;

/**
 * Package: kuifir.configuration.metadata
 * <p>
 * Description：基于XML的 YAML Property 外部化配置
 * <p>
 * Author: baci
 * <p>
 * Date: Created in 2023/1/4 21:40
 * <p>
 * Version: 0.0.1
 */
public class XmlBasedYamlPropertySourceDemo {
    public static void main(String[] args) {
        // 创建beanFactory容器
        DefaultListableBeanFactory beanFactory = new DefaultListableBeanFactory();
        String xmlLocation = "classpath:/META-INF/yaml-property-source-context.xml";
        XmlBeanDefinitionReader xmlBeanDefinitionReader = new XmlBeanDefinitionReader(beanFactory);
        int xmlBeanNumber = xmlBeanDefinitionReader.loadBeanDefinitions(xmlLocation);
        System.out.println("加载XML 中 Bean数量 " + xmlBeanNumber);
        Map<String,Object> yamlMap = beanFactory.getBean("yamlMap",Map.class);
        System.out.println(yamlMap);
    }
}
