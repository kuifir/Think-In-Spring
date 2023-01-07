package kuifir.data.binding;

import kuifir.ioc.overview.domain.Company;
import kuifir.ioc.overview.domain.User;
import org.springframework.beans.MutablePropertyValues;
import org.springframework.beans.PropertyValues;
import org.springframework.validation.BindingResult;
import org.springframework.validation.DataBinder;

import java.util.Properties;

/**
 * Package: kuifir.data.binding
 * <p>
 * Description： {@link org.springframework.validation.DataBinder}
 * <p>
 * Author: baci
 * <p>
 * Date: Created in 2023/1/8 0:19
 * <p>
 * Version: 0.0.1
 */
public class DataBinderDemo {
    public static void main(String[] args) {
        User user = new User();
        DataBinder dataBinder = new DataBinder(user);

        Properties properties = new Properties();
        properties.setProperty("id","1");
        properties.setProperty("name","测试DataBinder");

        // 默认放入User 不存在的属性不会报错
        properties.setProperty("age","18");
        // 默认放入User的嵌套属性会创建新对象并且注入属性
        properties.setProperty("company.name","dataBinderCompany");

        PropertyValues propertyValues = new MutablePropertyValues(properties);
//        1. 调整 IgnoreUnknownFields true（默认） -> false（抛出异常，age 字段不存在于 User 类）
//        dataBinder.setIgnoreUnknownFields(false);

//        2. 调整自动增加嵌套路径 true（默认） —> false
        user.setCompany(new Company()); // 须先设置好company属性后才能设置company的属性
//        dataBinder.setAutoGrowNestedPaths(false);
        // 3. 调整 ignoreInvalidFields false(默认） -> true（默认情况调整不变化，需要调增 AutoGrowNestedPaths 为 false）
        dataBinder.setIgnoreInvalidFields(true);
        // 4.设置必传属性,不会报错。获取绑定结果（结果包含错误文案 code，不会抛出异常）
        dataBinder.setRequiredFields("id","name","city");
        BindingResult bindingResult = dataBinder.getBindingResult();
        dataBinder.bind(propertyValues);

        System.out.println(user);
        System.out.println(bindingResult);
    }
}
