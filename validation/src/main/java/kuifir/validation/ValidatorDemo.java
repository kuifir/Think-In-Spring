package kuifir.validation;

import kuifir.ioc.overview.domain.User;
import org.springframework.context.support.StaticMessageSource;
import org.springframework.validation.*;

import java.util.List;
import java.util.Locale;
import java.util.Objects;

/**
 * Package: kuifir.validation
 * <p>
 * Description： 自定义 {@link Validator}
 * <p>
 * Author: baci
 * <p>
 * Date: Created in 2023/1/7 16:49
 * <p>
 * Version: 0.0.1
 */
public class ValidatorDemo {
    public static void main(String[] args) {
        Validator validator = new UserValodator();
        User user = new User();
        user.setName("ValidatorDemo");
        // 选择Errors的实现类
        Errors errors = new BeanPropertyBindingResult(user,"user");
        validator.validate(user,errors);

        List<ObjectError> allErrors = errors.getAllErrors();
        // 通过ObjectError 的 code 和 argument 关联 MessageSource
        StaticMessageSource messageSource = new StaticMessageSource();
        messageSource.addMessage("invalid.null", Locale.getDefault(),"{0}对象不能为空");
        messageSource.addMessage("invalid.null.input.value",Locale.getDefault(),"{0}属性不能为空");
        for (ObjectError objectError : allErrors) {
            System.out.println(messageSource.getMessage(Objects.requireNonNull(objectError.getCode()), objectError.getArguments(), Locale.getDefault()));
        }

    }
    static class UserValodator implements Validator{

        @Override
        public boolean supports(Class<?> clazz) {
            return User.class.equals(clazz);
        }

        @Override
        public void validate(Object target, Errors errors) {
            User user = (User) target;
            ValidationUtils.rejectIfEmptyOrWhitespace(errors,"id","invalid.null.input.value",new Object[]{"id"},"{0}不能为空");
            ValidationUtils.rejectIfEmptyOrWhitespace(errors,"name","invalid.null.input.value",new Object[]{"name"},"{0}不能为空");
        }
    }
}
