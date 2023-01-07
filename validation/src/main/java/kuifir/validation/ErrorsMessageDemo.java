package kuifir.validation;

import kuifir.ioc.overview.domain.User;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.context.support.StaticMessageSource;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.Errors;
import org.springframework.validation.ObjectError;

import java.util.List;
import java.util.Locale;
import java.util.Objects;

/**
 * Package: kuifir.validation
 * <p>
 * Description： 错误文案示例{@link org.springframework.validation.Errors}
 * <p>
 * Author: baci
 * <p>
 * Date: Created in 2023/1/7 15:43
 * <p>
 * Version: 0.0.1
 */
public class ErrorsMessageDemo {
    public static void main(String[] args) {
        User user = new User();
        user.setName("ErrorTest");
        // 选择Errors的实现类
        BeanPropertyBindingResult errors = new BeanPropertyBindingResult(user,"user");
        // 调用reject 或rejectValue
        errors.reject("invalid.null",new Object[]{errors.getObjectName()},"对象不能为空");
        errors.rejectValue("name","invalid.null.input.value",new Object[]{"name"},"name不能为空");
        // 获取错误
        List<ObjectError> allErrors = errors.getAllErrors();
        // 通过ObjectError 的 code 和 argument 关联 MessageSource
        StaticMessageSource messageSource = new StaticMessageSource();
        messageSource.addMessage("invalid.null",Locale.getDefault(),"{0}对象不能为空");
        messageSource.addMessage("invalid.null.input.value",Locale.getDefault(),"{0}属性不能为空");
        for (ObjectError objectError : allErrors) {
            System.out.println(messageSource.getMessage(Objects.requireNonNull(objectError.getCode()), objectError.getArguments(), Locale.getDefault()));
        }
    }

}
