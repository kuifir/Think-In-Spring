package kuifir.ioc.overview.container;

import kuifir.ioc.overview.domain.User;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.ListableBeanFactory;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;

import java.util.Map;

/**
 * Package: kuifir.ioc.overview.container
 * <p>
 * Description： TODO
 * <p>
 * Author: baci
 * <p>
 * Date: Created in 2022/8/24 22:59
 * <p>
 * Version: 0.0.1
 */
public class AnnotationAsIoCContainer {
    public static void main(String[] args) {
        DefaultListableBeanFactory beanFactory = new DefaultListableBeanFactory();
        AnnotationConfigApplicationContext applicationContext = new AnnotationConfigApplicationContext(beanFactory);

        applicationContext.register(AnnotationAsIoCContainer.class);
        applicationContext.refresh();
        // 类型查找集合
        lookupCollectionByType(beanFactory);
    }

    /**
     * 通过java注解定义了一个Bean
     * @return
     */
    @Bean
    public User user(){
        User user = new User();
        user.setId(11L);
        user.setName("kuifir");
        return user;
    }
    private static void lookupCollectionByType(BeanFactory beanFactory) {
        if(beanFactory instanceof ListableBeanFactory){
            ListableBeanFactory listableBeanFactory = (ListableBeanFactory) beanFactory;
            Map<String, User> users = listableBeanFactory.getBeansOfType(User.class);
            System.out.println("查找到所有的 User 集合对象"+users);
        }
    }
}
