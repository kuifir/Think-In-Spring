package kuifir.annotation;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.core.annotation.AliasFor;
import org.springframework.stereotype.Component;

import java.lang.annotation.*;

/**
 * Package: kuifir.annotation
 * <p>
 * Description：  自定义 {@link Component} Scan
 * <p>
 * Author: baci
 * <p>
 * Date: Created in 2023/1/22 1:19
 * <p>
 * Version: 0.0.1
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@MyComponentScan
public @interface MyComponentScan2 {

    @AliasFor(annotation = MyComponentScan.class, attribute = "scanBasePackages") // 隐性别名
            String[] basePackages() default {};

    // @MyComponentScan2.basePackages
    // -> @MyComponentScan.scanBasePackages
    // -> @ComponentScan.value
    // -> @ComponentScan.basePackages


    /**
     * 与元注解 @MyComponentScan 同名属性
     *
     * @return
     */
    String[] scanBasePackages() default {};


    @AliasFor("scanBasePackages")
    String[] packages() default {}; // packages 覆盖了 scanBasePackages 覆盖了元注解 scanBasePackages
}
