package kuifir.ioc.dependency.annotation;

import org.springframework.beans.factory.annotation.Qualifier;

import java.lang.annotation.*;

/**
 * Package: kuifir.ioc.dependency.annotation
 * <p>
 * Description： 用户组注解，扩展{@link Qualifier}
 * <p>
 * Author: baci
 * <p>
 * Date: Created in 2022/9/19 20:53
 * <p>
 * Version: 0.0.1
 */
@Target({ElementType.FIELD, ElementType.METHOD, ElementType.PARAMETER, ElementType.TYPE, ElementType.ANNOTATION_TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Inherited
@Documented
@Qualifier
public @interface UserGroup {
}
