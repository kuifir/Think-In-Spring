package kuifir.i18n;

import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.WebApplicationType;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.support.AbstractApplicationContext;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;

/**
 * Package: kuifir.i18n
 * <p>
 * Description： SpringBoot 场景下自定义{@link org.springframework.context.MessageSource} Bean
 * <p>
 * Author: baci
 * <p>
 * Date: Created in 2023/1/6 16:01
 * <p>
 * Version: 0.0.1
 */
@EnableAutoConfiguration
public class CustomizedMessageSourceBeanDemo {
    @Bean
    public MessageSource messageSource(){
        return new ReloadableResourceBundleMessageSource();
    }
    public static void main(String[] args) {
        ConfigurableApplicationContext configurableApplicationContext =
                new SpringApplicationBuilder(CustomizedMessageSourceBeanDemo.class)
                .web(WebApplicationType.NONE).run(args);
        ConfigurableListableBeanFactory beanFactory = configurableApplicationContext.getBeanFactory();
        if(beanFactory.containsBean(AbstractApplicationContext.MESSAGE_SOURCE_BEAN_NAME)){
            // 查找MessageSource 的 BeanDefinition
            System.out.println(beanFactory.getBeanDefinition(AbstractApplicationContext.MESSAGE_SOURCE_BEAN_NAME));

            MessageSource messageSource = configurableApplicationContext.getBean(AbstractApplicationContext.MESSAGE_SOURCE_BEAN_NAME, MessageSource.class);
            System.out.println(messageSource);

        }
        configurableApplicationContext.close();
    }
}
