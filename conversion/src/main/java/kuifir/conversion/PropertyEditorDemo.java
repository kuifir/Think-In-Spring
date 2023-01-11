package kuifir.conversion;

import java.beans.PropertyEditor;

/**
 * Package: kuifir.conversion
 * <p>
 * Description： {@link java.beans.PropertyEditor} 示例
 * <p>
 * Author: baci
 * <p>
 * Date: Created in 2023/1/8 14:50
 * <p>
 * Version: 0.0.1
 */
public class PropertyEditorDemo {
    public static void main(String[] args) {
        String text = "name = 测试PropertyEditor";
        PropertyEditor propertyEditor = new StringToPropertiesPropertyEditor();
        System.out.println(propertyEditor.getAsText());
        propertyEditor.setAsText(text);
        Object value = propertyEditor.getValue();
        System.out.println(value);
        System.out.println(value.getClass());
        System.out.println(propertyEditor.getAsText());
    }
}
