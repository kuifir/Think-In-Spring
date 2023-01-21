package kuifir.annotation;

import org.springframework.stereotype.Component;

import java.lang.annotation.*;

/**
 * Package: kuifir.annotation
 * <p>
 * Description： {@link MyComponent} 派生类
 *
 * <p>
 * Author: baci
 * <p>
 * Date: Created in 2023/1/21 15:53
 * <p>
 * Version: 0.0.1
 */
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Component
public @interface MyComponent2 {
}
