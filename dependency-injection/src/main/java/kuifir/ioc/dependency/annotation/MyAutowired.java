package kuifir.ioc.dependency.annotation;

import org.springframework.beans.factory.annotation.Autowired;

import java.lang.annotation.*;

/**
 * Package: kuifir.ioc.dependency.annotation
 * <p>
 * Description： 自定义注解(原标注 @Autowired)
 * <p>
 * Author: baci
 * <p>
 * Date: Created in 2022/9/27 21:40
 * <p>
 * Version: 0.0.1
 */
@Target({ElementType.CONSTRUCTOR, ElementType.METHOD,ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Autowired
public @interface MyAutowired {

    /**
     * Declares whether the annotated dependency is required.
     * <p>Defaults to {@code true}.
     */
    boolean required() default true;
}
