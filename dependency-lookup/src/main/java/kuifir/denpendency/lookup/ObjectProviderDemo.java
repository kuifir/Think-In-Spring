package kuifir.denpendency.lookup;

import kuifir.ioc.overview.domain.User;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

/**
 * Package: kuifir.denpendency.lookup
 * <p>
 * Description： 通过{@link ObjectProvider}进行依赖查找
* <p>
 * Author: baci
 * <p>
 * Date: Created in 2022/9/4 22:44
 * <p>
 * Version: 0.0.1
 */
@Configuration // @Configuration 是非必须注解
public class ObjectProviderDemo {
    public static void main(String[] args) {
        // 创建容器
        AnnotationConfigApplicationContext applicationContext = new AnnotationConfigApplicationContext();
        // 加载Configuration Class（当前类作为配置类）
        applicationContext.register(ObjectProviderDemo.class);
        // 启动应用上下文
        applicationContext.refresh();
        // 依赖查找集合对象
        lookupCollectionByObjectProvider(applicationContext);
        // Bean不存在的时候怎么做？
        lookupIfAvailable(applicationContext);
        //
        lookupByStreamOps(applicationContext);
        // 关闭应用上下文
        applicationContext.close();
    }

    @Bean
    @Primary
    public String helloWorld(){ // @Bean 未定义Bean名称。Bean默认为方法名 = "helloWorld"
        return "Hello,World";
    }
    @Bean
    public String massage(){
        return "Message";
    }

    private static void lookupCollectionByObjectProvider(AnnotationConfigApplicationContext applicationContext) {
        ObjectProvider<String> beanProvider = applicationContext.getBeanProvider(String.class);
        System.out.println(beanProvider.getObject());
    }

    private static void lookupIfAvailable(AnnotationConfigApplicationContext applicationContext) {
        ObjectProvider<User> userObjectProvider = applicationContext.getBeanProvider(User.class);
        User user = userObjectProvider.getIfAvailable(User::createUser);
        System.out.println("当前User对象："+user);
    }

    private static void lookupByStreamOps(AnnotationConfigApplicationContext applicationContext) {
        ObjectProvider<String> objectProvider = applicationContext.getBeanProvider(String.class);
        for (String s : objectProvider) {
            System.out.println(s);
        }
        objectProvider.stream().forEach(System.out::println);
    }
}
