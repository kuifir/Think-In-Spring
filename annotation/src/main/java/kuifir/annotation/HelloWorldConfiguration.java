package kuifir.annotation;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Package: kuifir.annotation
 * <p>
 * Description：
 * <p>
 * Author: baci
 * <p>
 * Date: Created in 2023/1/22 0:36
 * <p>
 * Version: 0.0.1
 */
@Configuration
public class HelloWorldConfiguration {
    @Bean
    public String helloWorld(){
        return "hello,world";
    }
}
