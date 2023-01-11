package kuifir.conversion;

import java.beans.PropertyEditor;
import java.beans.PropertyEditorSupport;
import java.io.IOException;
import java.io.StringReader;
import java.util.Map;
import java.util.Properties;

/**
 * Package: kuifir.conversion
 * <p>
 * Description： String -> Properties {@link java.beans.PropertyEditor}
 * <p>
 * Author: baci
 * <p>
 * Date: Created in 2023/1/8 14:52
 * <p>
 * Version: 0.0.1
 */
public class StringToPropertiesPropertyEditor extends PropertyEditorSupport implements PropertyEditor {

    @Override
    // 1. 实现 setAsText(String) 方法
    public void setAsText(String text) throws IllegalArgumentException {
        // 2. 将 String 类型转换成 Properties 类型
        Properties properties = new Properties();
        try {
            properties.load(new StringReader(text));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        // 3. 临时存储 Properties 对象
        setValue(properties);
        // next 获取临时 Properties 对象 #getValue();
    }

    @Override
    public String getAsText() {
        if (null == getValue()) return "";
        Properties properties = (Properties) getValue();
        StringBuilder textBuilder = new StringBuilder();
        for (Map.Entry<Object, Object> entry : properties.entrySet()) {
            textBuilder.append(entry.getKey()).append(" = ")
                    .append(entry.getValue())
                    .append(System.lineSeparator());
        }
        return textBuilder.toString();
    }
}
