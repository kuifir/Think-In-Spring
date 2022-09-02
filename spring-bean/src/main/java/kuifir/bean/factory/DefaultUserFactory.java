package kuifir.bean.factory;

import org.springframework.beans.factory.InitializingBean;

import javax.annotation.PostConstruct;

/**
 * Package: kuifir.bean.factory
 * <p>
 * Description： 默认 {@link UserFactory}实现
 * <p>
 * Author: baci
 * <p>
 * Date: Created in 2022/9/1 20:05
 * <p>
 * Version: 0.0.1
 */
public class DefaultUserFactory implements UserFactory, InitializingBean {

    // 1.基于 @PostConstruct 注解
    @PostConstruct
    public void init(){
        System.out.println("@PostConstruct: UserFactory 初始化中");
    }

    public void initUserFactory(){
        System.out.println("自定义初始化方法 initUserFactory()：UserFactory 初始化中");
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        System.out.println("InitializingBean#afterPropertiesSet：UserFactory 初始化中");
    }
}
