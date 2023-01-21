package kuifir.annotation;

import org.springframework.context.annotation.ImportSelector;
import org.springframework.core.type.AnnotationMetadata;

/**
 * Package: kuifir.annotation
 * <p>
 * Description：  HelloWorld 模块 {@link ImportSelector} 实现
 * <p>
 * Author: baci
 * <p>
 * Date: Created in 2023/1/22 0:39
 * <p>
 * Version: 0.0.1
 */
public class HelloWorldImportSelector implements ImportSelector {
    @Override
    public String[] selectImports(AnnotationMetadata importingClassMetadata) {
        return new String[]{"kuifir.annotation.HelloWorldConfiguration"};
    }
}
