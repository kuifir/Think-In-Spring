package kuifir.ioc.java.beans;

import java.beans.BeanInfo;
import java.beans.IntrospectionException;
import java.beans.Introspector;
import java.beans.PropertyEditorSupport;
import java.util.stream.Stream;

/**
 * Package: kuifir.ioc.java.beans
 * <p>
 * Description： TODO
 * <p>
 * Author: baci
 * <p>
 * Date: Created in 2022/8/20 21:35
 * <p>
 * Version: 0.0.1
 */
public class BeanInfoDemo {
    public static void main(String[] args) throws IntrospectionException {
//        BeanInfo beanInfo = Introspector.getBeanInfo(Person.class);
        BeanInfo beanInfo = Introspector.getBeanInfo(Person.class,Object.class);
//        Stream.of(beanInfo.getPropertyDescriptors()).forEach(System.out::println);
        Stream.of(beanInfo.getPropertyDescriptors()).forEach(propertyDescriptor -> {
            String name = propertyDescriptor.getName();
            if ("age".equals(name)){
                propertyDescriptor.setPropertyEditorClass(StringToIntegerPropertyEditor.class);
            }
        });
    }
    static class StringToIntegerPropertyEditor extends PropertyEditorSupport{
        public void setAsText(String text) throws java.lang.IllegalArgumentException {
            Integer value = Integer.valueOf(text);
            setValue(value);
        }
    }
}
