package kuifir.bean.definition;

import kuifir.bean.factory.DefaultUserFactory;
import kuifir.bean.factory.UserFactory;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Package: kuifir.bean.definition
 * <p>
 * Description： Bean 初始化 Demo
 * <p>
 * Author: baci
 * <p>
 * Date: Created in 2022/9/2 20:50
 * <p>
 * Version: 0.0.1
 */
@Configuration //Configuration Class
public class BeanInitializationDemo {
    public static void main(String[] args) {
        // 创建BeanFactory容器
        AnnotationConfigApplicationContext applicationContext = new AnnotationConfigApplicationContext();
        //注册 Configuration Class(配置类)
        applicationContext.register(BeanInitializationDemo.class);
        // 启动Spring 应用上下文
        applicationContext.refresh();
        // 依赖查找UserFactory 此时应该自动回调@PostConsturct 方法
        UserFactory userFactory = applicationContext.getBean(UserFactory.class);

        // 关闭Spring 应用上下文
        applicationContext.close();
    }

    @Bean(initMethod = "initUserFactory")
    public UserFactory userFactory(){
        return new DefaultUserFactory();
    }
}
