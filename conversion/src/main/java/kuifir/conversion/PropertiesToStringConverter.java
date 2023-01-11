package kuifir.conversion;

import org.springframework.core.convert.TypeDescriptor;
import org.springframework.core.convert.converter.ConditionalGenericConverter;

import java.util.Collections;
import java.util.Map;
import java.util.Properties;
import java.util.Set;

/**
 * Package: kuifir.conversion
 * <p>
 * Description： {@link org.springframework.core.convert.converter.ConditionalGenericConverter}
 * <p>
 * Author: baci
 * <p>
 * Date: Created in 2023/1/8 22:38
 * <p>
 * Version: 0.0.1
 */
public class PropertiesToStringConverter implements ConditionalGenericConverter {

    @Override
    public boolean matches(TypeDescriptor sourceType, TypeDescriptor targetType) {
        return Properties.class.equals(sourceType.getObjectType()) && String.class.equals(targetType.getObjectType());
    }

    @Override
    public Set<ConvertiblePair> getConvertibleTypes() {
        return Collections.singleton(new ConvertiblePair(Properties.class,String.class));
    }

    @Override
    public Object convert(Object source, TypeDescriptor sourceType, TypeDescriptor targetType) {
        if (null == source) return null;
        Properties properties = (Properties) source;
        StringBuilder textBuilder = new StringBuilder();
        for (Map.Entry<Object, Object> entry : properties.entrySet()) {
            textBuilder.append(entry.getKey()).append(" = ")
                    .append(entry.getValue())
                    .append(System.lineSeparator());
        }
        return textBuilder.toString();
    }
}
