package kuifir.event;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.event.EventListener;
import org.springframework.core.task.SimpleAsyncTaskExecutor;
import org.springframework.core.task.TaskExecutor;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.CustomizableThreadFactory;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Package: kuifir.event
 * <p>
 * Description： 注解驱动异步事件处理示例
 * <p>
 * Author: baci
 * <p>
 * Date: Created in 2023/1/13 21:56
 * <p>
 * Version: 0.0.1
 */
@EnableAsync
public class AnnotatedAsyncEventHandleDemo {
    public static void main(String[] args) {
        AnnotationConfigApplicationContext applicationContext = new AnnotationConfigApplicationContext();

        applicationContext.register(AnnotatedAsyncEventHandleDemo.class);
        // 启动 Spring 应用上下文
        applicationContext.refresh();
        // 发布自定义 Spring 事件
        applicationContext.publishEvent(new MySpringEvent("MySpringEvent"));
        // 关闭 Spring 应用上下文
        applicationContext.close();
    }

    @EventListener
    @Async
    public void onEvent(MySpringEvent mySpringEvent){
        System.out.printf(" [线程 %s] 监听到事件 : %s %n",Thread.currentThread().getName(),mySpringEvent.getMessage());
    }

    @Bean
    public TaskExecutor taskExecutor(){
        return new SimpleAsyncTaskExecutor("my-spring-event-thread-pool-asyn");
    }

}
