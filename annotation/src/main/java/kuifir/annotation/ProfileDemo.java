package kuifir.annotation;

import org.springframework.context.annotation.*;
import org.springframework.core.env.ConfigurableEnvironment;

/**
 * Package: kuifir.annotation
 * <p>
 * Description： {@link Profile} 示例
 * <p>
 * Author: baci
 * <p>
 * Date: Created in 2023/1/22 0:50
 * <p>
 * Version: 0.0.1
 */
@Configuration
public class ProfileDemo {
    public static void main(String[] args) {
        AnnotationConfigApplicationContext applicationContext = new AnnotationConfigApplicationContext();
        applicationContext.register(ProfileDemo.class);
        // 获取 Environment 对象（可配置的）
        ConfigurableEnvironment environment = applicationContext.getEnvironment();
        // 默认 profiles = [ "odd" ] （兜底 profiles)
        environment.setDefaultProfiles("odd");
        // 增加活跃 profiles
        environment.setActiveProfiles("even");
        applicationContext.refresh();
        Integer number = applicationContext.getBean("number",Integer.class);
        System.out.println(number);
        applicationContext.close();
    }
    @Bean(name = "number")
    @Profile("odd") // 奇数
    public Integer odd() {
        return 1;
    }

    @Bean(name = "number")
//    @Profile("even") // 偶数
    @Conditional(EvenProfileCondition.class)
    public Integer even() {
        return 2;
    }

}
