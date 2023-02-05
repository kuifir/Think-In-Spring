package kuifir.event;

import org.springframework.context.ApplicationListener;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.event.ApplicationEventMulticaster;
import org.springframework.context.event.ContextClosedEvent;
import org.springframework.context.event.SimpleApplicationEventMulticaster;
import org.springframework.context.support.AbstractApplicationContext;
import org.springframework.scheduling.concurrent.CustomizableThreadFactory;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Package: kuifir.event
 * <p>
 * Description： 异步事件处理示例
 * <p>
 * Author: baci
 * <p>
 * Date: Created in 2023/1/13 21:41
 * <p>
 * Version: 0.0.1
 */
public class AsyncEventHandlerDemo {
    public static void main(String[] args) {
        AnnotationConfigApplicationContext applicatFionContext = new AnnotationConfigApplicationContext();

        // 添加自定义 Spring 事件监听器
        applicationContext.register(MySpringEventListener.class);
        // 启动 Spring 应用上下文
        applicationContext.refresh();

        // 依赖查找 ApplicationEventMulticaster
        ApplicationEventMulticaster applicationEventMulticaster =
                applicationContext.getBean(AbstractApplicationContext.APPLICATION_EVENT_MULTICASTER_BEAN_NAME, ApplicationEventMulticaster.class);
        if(applicationEventMulticaster instanceof SimpleApplicationEventMulticaster){
            SimpleApplicationEventMulticaster simpleApplicationEventMulticaster
                    = (SimpleApplicationEventMulticaster) applicationEventMulticaster;
            ExecutorService taskExecutor = Executors.newSingleThreadExecutor(new CustomizableThreadFactory("my-spring-event-thread-pool"));
            // 同步 -> 异步
            simpleApplicationEventMulticaster.setTaskExecutor(taskExecutor);

            // 添加 ContextClosedEvent 事件处理
            applicationEventMulticaster.addApplicationListener((ApplicationListener<ContextClosedEvent>) event -> {
               if(!taskExecutor.isShutdown()){
                   taskExecutor.shutdown();
               }
            });
            simpleApplicationEventMulticaster.setErrorHandler(e->{
                System.err.println("Spring 发生异常，原因：" + e.getMessage());
            });
            // 添加故意抛出异常
            applicationContext.addApplicationListener((ApplicationListener<MySpringEvent>)event ->{
                throw new RuntimeException("故意抛出异常");
            });
        }

        // 发布自定义 Spring 事件
        applicationContext.publishEvent(new MySpringEvent("MySpringEvent"));
        // 关闭 Spring 应用上下文
        applicationContext.close();
    }
}
