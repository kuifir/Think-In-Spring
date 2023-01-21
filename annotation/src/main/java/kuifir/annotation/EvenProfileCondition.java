package kuifir.annotation;

import org.springframework.context.annotation.Condition;
import org.springframework.context.annotation.ConditionContext;
import org.springframework.core.env.Profiles;
import org.springframework.core.type.AnnotatedTypeMetadata;

/**
 * Package: kuifir.annotation
 * <p>
 * Description： 偶数 Profile 条件
 * <p>
 * Author: baci
 * <p>
 * Date: Created in 2023/1/22 0:56
 * <p>
 * Version: 0.0.1
 */
public class EvenProfileCondition implements Condition {
    @Override
    public boolean matches(ConditionContext context, AnnotatedTypeMetadata metadata) {
        return context.getEnvironment().acceptsProfiles(Profiles.of("even"));
    }
}
