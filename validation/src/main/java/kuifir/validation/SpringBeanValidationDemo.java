package kuifir.validation;

import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.stereotype.Component;
import org.springframework.validation.annotation.Validated;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

/**
 * Package: kuifir.validation
 * <p>
 * Description： Spring Bean Validation 整合示例
 * <p>
 * Author: baci
 * <p>
 * Date: Created in 2023/1/7 17:25
 * <p>
 * Version: 0.0.1
 */
public class SpringBeanValidationDemo {
    public static void main(String[] args) {
        ConfigurableApplicationContext applicationContext =
                new ClassPathXmlApplicationContext("classpath:/META-INF\\bean-validation-context.xml");
        LocalValidatorFactoryBean localValidatorFactoryBean = applicationContext.getBean(LocalValidatorFactoryBean.class);
        System.out.println(localValidatorFactoryBean);
        UserProcessor userProcessor = applicationContext.getBean(UserProcessor.class);
        userProcessor.processUser(new User());
        applicationContext.close();
    }
    @Component
    @Validated
    static class UserProcessor{
        public void processUser(@Valid User user){
            System.out.println(user);
        }
    }
    static class User{
        @NotNull
        private String name;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        @Override
        public String toString() {
            return "User{" +
                    "name='" + name + '\'' +
                    '}';
        }
    }
}
