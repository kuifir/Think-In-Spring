package kuifir.bean.lifecycle;

import kuifir.ioc.overview.domain.User;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.*;
import org.springframework.context.EnvironmentAware;
import org.springframework.core.env.Environment;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

/**
 * Package: kuifir.bean.lifecycle
 * <p>
 * Description： TODO
 * <p>
 * Author: baci
 * <p>
 * Date: Created in 2023/1/1 23:58
 * <p>
 * Version: 0.0.1
 */
public class UserHolder implements BeanNameAware, BeanFactoryAware, BeanClassLoaderAware, EnvironmentAware,
        InitializingBean, SmartInitializingSingleton, DisposableBean{
    private User user;
    private Integer number;
    private String description;
    private String beanName;
    private BeanFactory beanFactory;
    private ClassLoader classLoader;
    private Environment environment;

    public UserHolder() {
    }

    public UserHolder(User user) {
        this.user = user;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Integer getNumber() {
        return number;
    }

    public void setNumber(Integer number) {
        this.number = number;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public String toString() {
        return "UserHolder{" +
                "user=" + user +
                ", number=" + number +
                ", description='" + description + '\'' +
                ", beanName='" + beanName + '\'' +
                '}';
    }

    @Override
    public void setBeanName(String name) {
        this.beanName = name;
    }

    @Override
    public void setBeanClassLoader(ClassLoader classLoader) {
        this.classLoader = classLoader;
    }

    @Override
    public void setBeanFactory(BeanFactory beanFactory) throws BeansException {
        this.beanFactory = beanFactory;
    }


    @Override
    public void setEnvironment(Environment environment) {
        this.environment = environment;
    }

    @PostConstruct
    public void initPostConstruct(){
        // postProcessBeforeInitialization V3-> initPostConstruct V4
        this.description = "The user holder V4";
        System.out.println("initPostConstruct() = "  + description);
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        // initPostConstruct V4 -> afterPropertiesSet V5
        this.description = "The user holder V5";
        System.out.println("afterPropertiesSet() = "  + description);
    }
    public void init(){
        // afterPropertiesSet V5 -> 自定义初始化方法 V6
        this.description = "The user holder V6";
        System.out.println("afterPropertiesSet() = "  + description);
    }

    @Override
    public void afterSingletonsInstantiated() {
        // ApplicationContext在refresh的操作里等beanFactory的一系列操作，
        // messageSource，注册listener等操作都完毕之后通过finishBeanFactoryInitialization开始实例化所有非懒加载的单例bean，
        // 具体是在finishBeanFactoryInitialization调用beanFactory#preInstantiateSingletons进行的，
        // preInstantiateSingletons里面就是通过beanDefinitionNames循环调用getBean来实例化bean的，
        // 这里有个细节，beanDefinitionNames是拷贝到一个副本中，循环副本，使得还能注册新的beanDefinition.
        // getBean的操作就是我们之前那么多节课分析的一顿操作的过程，最终得到一个完整的状态的bean。
        // 然后所有的非延迟单例都加载完毕之后，再重新循环副本，判断bean是否是SmartInitializingSingleton，
        // 如果是的话执行SmartInitializingSingleton#afterSingletonsInstantiated。
        // 这保证执行afterSingletonsInstantiated的时候的bean一定是完整的。

        // postProcessAfterInitialization V7 ->SmartInitializingSingleton#afterSingletonsInstantiated V8
        this.description = "The user holder V8";
        System.out.println("afterSingletonsInstantiated() = "  + description);
    }

    @PreDestroy
    public void preDistroy(){
        // DestructionAwareBeanPostProcessor#postProcessBeforeDestruction V9 -> @PreDestroy V10
        this.description = "The user holder V10";
        System.out.println("preDistroy() = "  + description);
    }

    @Override
    public void destroy() throws Exception {
        // @PreDestroy V10 -> DisposableBean V11
        this.description = "The user holder V11";
        System.out.println("destroy() = "  + description);
    }
    public void doDestroy(){
        // DisposableBean V11 -> 自定义销毁方法
        this.description = "The user holder V12";
        System.out.println("destroy() = "  + description);
    }
}
