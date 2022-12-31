package kuifir.ioc.bean.scope;

import kuifir.ioc.overview.domain.User;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Scope;

/**
 * Package: kuifir.ioc.bean.scope
 * <p>
 * Description： 自定义 Scope {@link ThreadLocalScope} 示例
 * <p>
 * Author: baci
 * <p>
 * Date: Created in 2022/12/31 17:39
 * <p>
 * Version: 0.0.1
 */
public class ThreadLocalScopeDemo {

    @Bean
    @Scope(ThreadLocalScope.SCOPE_NAME)
    private static User user() {
        return createUser();
    }

    private static User createUser() {
        User user = new User();
        user.setId(System.nanoTime());
        return user;
    }
    public static void main(String[] args) {
        // 创建BeanFactory 容器
        AnnotationConfigApplicationContext applicationContext = new AnnotationConfigApplicationContext();
        // 注册Configuration 配置类信息
        applicationContext.register(ThreadLocalScopeDemo.class);
        applicationContext.addBeanFactoryPostProcessor(beanFactory -> {
            beanFactory.registerScope(ThreadLocalScope.SCOPE_NAME,new ThreadLocalScope());
        });
        // 启动应用上下文
        applicationContext.refresh();

        scopedBeanByLookup(applicationContext);
        // 关闭应用上下文
        applicationContext.close();
    }

    private static void scopedBeanByLookup(AnnotationConfigApplicationContext applicationContext) {

        for (int i = 0; i < 3; i++) {
            final Thread thread = new Thread(() -> {
                for (int j = 0; j < 3; j++) {
                    User user = applicationContext.getBean("user", User.class);
                    System.out.printf("[Thread id + %d ] user = %s%n", Thread.currentThread().getId(), user);
                }
            });
            thread.start();
            try {
                thread.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

        }
    }
}
