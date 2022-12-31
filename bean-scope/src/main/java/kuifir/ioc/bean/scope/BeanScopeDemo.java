package kuifir.ioc.bean.scope;

import kuifir.ioc.overview.domain.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Scope;

import java.util.Map;

/**
 * Package: kuifir.ioc.bean.scope
 * <p>
 * Description： Bean的作用域示例
 * <p>
 * Author: baci
 * <p>
 * Date: Created in 2022/12/31 13:45
 * <p>
 * Version: 0.0.1
 */
public class BeanScopeDemo {

    @Autowired
    @Qualifier("singletonUser")
    private User singletonUser;

    @Autowired
    @Qualifier("singletonUser")
    private User singletonUser1;

    @Autowired
    @Qualifier("prototypeUser")
    private User prototypeUser;

    @Autowired
    @Qualifier("prototypeUser")
    private User prototypeUser1;

    @Autowired
    @Qualifier("prototypeUser")
    private User prototypeUser2;

    @Autowired
    private Map<String, User> users;

    @Bean
    private static User singletonUser() {
        return createUser();
    }

    @Bean
    @Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
    private static User prototypeUser() {
        return createUser();
    }

    private static User createUser() {
        User user = new User();
        user.setId(System.nanoTime());
        return user;
    }

    public static void main(String[] args) {
        // 创建 BeanFactory容器
        AnnotationConfigApplicationContext applicationContext = new AnnotationConfigApplicationContext();
        // 注入Configuration 配置类
        applicationContext.register(BeanScopeDemo.class);
        // 启动容器
        applicationContext.refresh();
        scopedBeanByLookup(applicationContext);
        scopedBeanByInjection(applicationContext);

        /* 结论一
        *  Singleton Bean 无论是依赖注入还是依赖查找，均为同一个对象
        *  Prototype Bean 无论是依赖注入还是依赖查找，均为新生成的一个对象
        *  结论二：
        *  如果依赖注入集合对象，Singleton Bean 和 Prototype Bean 均会存在一个
        *  Prototype Bean 有别于其他地方依赖注入的 Prototype Bean(依然是重新生成的)
        * 结论三：
        *  无论是 Singleton Bean 还是Prototype Bean 均会执行初始化方法回调
        *  不过仅有Singleton Bean 会执行 销毁方法回调
        * */
        // 关闭容器
        applicationContext.close();

    }


    private static void scopedBeanByLookup(AnnotationConfigApplicationContext applicationContext) {
        for (int i = 0; i < 3; i++) {
            // singletonUser是共享的Bean对象
            User singletonUser = applicationContext.getBean("singletonUser", User.class);
            System.out.println("singletonUser = " + singletonUser);

            // prototypeUSer每次依赖查找都生成了新的Bean对象
            User prototypeUser = applicationContext.getBean("prototypeUser", User.class);
            System.out.println("prototypeUser = " + prototypeUser);
        }
    }

    private static void scopedBeanByInjection(AnnotationConfigApplicationContext applicationContext) {
        BeanScopeDemo beanScopeDemo = applicationContext.getBean(BeanScopeDemo.class);

        System.out.println("beanScopeDemo.singletonUser" + beanScopeDemo.singletonUser);
        System.out.println("beanScopeDemo.singletonUser1" + beanScopeDemo.singletonUser1);

        System.out.println("beanScopeDemo.prototypeUser" + beanScopeDemo.prototypeUser);
        System.out.println("beanScopeDemo.prototypeUser1" + beanScopeDemo.prototypeUser1);
        System.out.println("beanScopeDemo.prototypeUser2" + beanScopeDemo.prototypeUser2);

        System.out.println("beanScopeDemo.users" + beanScopeDemo.users);
    }
}
