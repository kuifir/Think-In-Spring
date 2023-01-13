package kuifir.event;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;

/**
 * Package: kuifir.event
 * <p>
 * Description： TODO
 * <p>
 * Author: baci
 * <p>
 * Date: Created in 2023/1/12 15:23
 * <p>
 * Version: 0.0.1
 */
public class CustomizedSpringEventDemo {
    public static void main(String[] args) {
        AnnotationConfigApplicationContext applicationContext = new AnnotationConfigApplicationContext();

        // 添加自定义 Spring 事件监听器
        applicationContext.register(MySpringEventListener.class);
        // 启动 Spring 应用上下文
        applicationContext.refresh();
        // 发布自定义 Spring 事件
        applicationContext.publishEvent(new MySpringEvent("MySpringEvent"));
        // 关闭 Spring 应用上下文
        applicationContext.close();
    }
}
