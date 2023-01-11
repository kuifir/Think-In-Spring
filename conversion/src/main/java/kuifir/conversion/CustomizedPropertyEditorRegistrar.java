package kuifir.conversion;

import org.springframework.beans.PropertyEditorRegistrar;
import org.springframework.beans.PropertyEditorRegistry;

import java.util.Properties;

/**
 * Package: kuifir.conversion
 * <p>
 * Description： {@link org.springframework.beans.PropertyEditorRegistrar}
 * <p>
 * Author: baci
 * <p>
 * Date: Created in 2023/1/8 17:19
 * <p>
 * Version: 0.0.1
 */
public class CustomizedPropertyEditorRegistrar implements PropertyEditorRegistrar {
    @Override
    public void registerCustomEditors(PropertyEditorRegistry registry) {
        // 1. 通用类型转换
        // 2. Java Bean 属性类型转换
        registry.registerCustomEditor(Properties.class,"context",new StringToPropertiesPropertyEditor());
    }
}
