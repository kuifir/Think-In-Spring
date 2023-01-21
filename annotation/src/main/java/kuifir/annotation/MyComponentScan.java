package kuifir.annotation;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.core.annotation.AliasFor;

import java.lang.annotation.*;

/**
 * Package: kuifir.annotation
 * <p>
 * Description： 自定义 {@link org.springframework.stereotype.Component} Scan
 * <p>
 * Author: baci
 * <p>
 * Date: Created in 2023/1/22 1:16
 * <p>
 * Version: 0.0.1
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@ComponentScan
public @interface MyComponentScan {

    @AliasFor(annotation = ComponentScan.class, attribute = "basePackages")
    String[] scanBasePackages() default {};
}
