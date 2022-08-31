package kuifir.ioc.overview.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Package: kuifir.ioc.overview.annotation
 * <p>
 * Description： 超级
 * <p>
 * Author: baci
 * <p>
 * Date: Created in 2022/8/21 21:58
 * <p>
 * Version: 0.0.1
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface Super {
}
