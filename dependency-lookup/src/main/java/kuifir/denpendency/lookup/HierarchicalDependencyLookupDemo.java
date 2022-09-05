package kuifir.denpendency.lookup;

import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.HierarchicalBeanFactory;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.beans.factory.xml.XmlBeanDefinitionReader;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

/**
 * Package: kuifir.denpendency.lookup
 * <p>
 * Description： 层次性依赖查找示例
 * <p>
 * Author: baci
 * <p>
 * Date: Created in 2022/9/5 21:32
 * <p>
 * Version: 0.0.1
 */
public class HierarchicalDependencyLookupDemo {
    public static void main(String[] args) {
        AnnotationConfigApplicationContext applicationContext = new AnnotationConfigApplicationContext();
        applicationContext.register(ObjectProviderDemo.class);

        //1. 获取HierarchicalBeanFactory <-ConfigurableBeanFactory<-ConfigurableListableBeanFactory
        ConfigurableListableBeanFactory beanFactory = applicationContext.getBeanFactory();
        System.out.println("当前BeanFactory 的Parent BeanFactory:" + beanFactory.getParentBeanFactory());
        //2. 设置 Parent BeanFactory(HierarchicalBeanFactory)
        HierarchicalBeanFactory parentBeanFactory = createParentBeanFactory();
        beanFactory.setParentBeanFactory(parentBeanFactory);
        System.out.println("当前BeanFactory 的Parent BeanFactory:" + beanFactory.getParentBeanFactory());
        // 尝试一下LocalBean指的是啥（Local 指的当前Bean）
        displayContainsLocalBean(beanFactory,"user");
        displayContainsLocalBean(parentBeanFactory,"user");

        displayContainsBean(beanFactory,"user");
        displayContainsBean(parentBeanFactory,"user");

        applicationContext.refresh();
        applicationContext.close();
    }

    private static void displayContainsBean(HierarchicalBeanFactory beanFactory, String beanName) {
        System.out.printf("当前 BeanFactory[%s] 是否包含bean[name: %s] : %s\n",beanFactory, beanName ,containsBean(beanFactory,beanName));
    }
    private static Boolean containsBean(HierarchicalBeanFactory beanFactory, String beanName){
        BeanFactory parentBeanFactory = beanFactory.getParentBeanFactory();
        if(parentBeanFactory instanceof HierarchicalBeanFactory){
            HierarchicalBeanFactory parentHierarchicalBeanFactory = (HierarchicalBeanFactory) parentBeanFactory;
            if (containsBean(parentHierarchicalBeanFactory,beanName)) {
                return true;
            }
        }
        return beanFactory.containsLocalBean(beanName);
    }

    private static void displayContainsLocalBean(HierarchicalBeanFactory beanFactory, String beanName) {
        System.out.printf("当前 BeanFactory[%s] 是否包含bean[name: %s] : %s\n",beanFactory,beanName,beanFactory.containsLocalBean(beanName));
    }


    private static HierarchicalBeanFactory createParentBeanFactory() {
        // 创建BeanFactory容器
        DefaultListableBeanFactory beanFactory = new DefaultListableBeanFactory();
        // 加载配置
        XmlBeanDefinitionReader reader = new XmlBeanDefinitionReader(beanFactory);
        // xml配置文件的一个classpath路径
        String location = "classpath:\\META-INF\\dependency-lookup-context.xml";
        // 加载配置
        reader.loadBeanDefinitions(location);

        return beanFactory;
    }

}
