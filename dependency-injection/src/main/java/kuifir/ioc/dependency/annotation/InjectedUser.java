package kuifir.ioc.dependency.annotation;

import org.springframework.beans.factory.annotation.Autowired;

import java.lang.annotation.*;

/**
 * Package: kuifir.ioc.dependency.annotation
 * <p>
 * Description： 自定义依赖注入注解
 * <p>
 * Author: baci
 * <p>
 * Date: Created in 2022/9/27 21:48
 * <p>
 * Version: 0.0.1
 */

@Target({ElementType.CONSTRUCTOR, ElementType.METHOD,ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface InjectedUser {
}
