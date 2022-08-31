package kuifir.ioc.overview.container;

import kuifir.ioc.overview.domain.User;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.ListableBeanFactory;
import org.springframework.beans.factory.support.BeanDefinitionReader;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.beans.factory.xml.XmlBeanDefinitionReader;

import java.util.Map;

/**
 * Package: kuifir.ioc.overview.container
 * <p>
 * {@link BeanFactory} 作为IoC容器示例
 * <p>
 * @author: baci
 * <p>
 * Date: Created in 2022/8/24 22:10
 * <p>
 * Version: 0.0.1
 */
public class BeanFactoryAsIoCContainerDemo {
    public static void main(String[] args) {
        // 使用BeanFactory容器而不用ApplicationContext 没有事件，资源管理等其他复杂特性

        // 创建BeanFactory容器
        DefaultListableBeanFactory beanFactory = new DefaultListableBeanFactory();

        // 加载配置
        XmlBeanDefinitionReader reader = new XmlBeanDefinitionReader(beanFactory);
//        xml配置文件的一个classpath路径
        String location = "classpath:\\META-INF\\dependency-lookup-context.xml";
        int beanDefinitionCount = reader.loadBeanDefinitions(location);

        System.out.println("Bean 定义加载数量"+beanDefinitionCount);

        // 类型查找集合
        lookupCollectionByType(beanFactory);
    }
    private static void lookupCollectionByType(BeanFactory beanFactory) {
        if(beanFactory instanceof ListableBeanFactory){
            ListableBeanFactory listableBeanFactory = (ListableBeanFactory) beanFactory;
            Map<String, User> users = listableBeanFactory.getBeansOfType(User.class);
            System.out.println("查找到所有的 User 集合对象"+users);
        }
    }
}
