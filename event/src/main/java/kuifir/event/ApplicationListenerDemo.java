package kuifir.event;

import org.springframework.context.*;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.event.*;
import org.springframework.core.annotation.Order;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.EnableAsync;

/**
 * Package: kuifir.event
 * <p>
 * Description： {@link org.springframework.context.ApplicationListener} 示例
 *               {@link org.springframework.context.event.EventListener} 示例
 * <p>
 * Author: baci
 * <p>
 * Date: Created in 2023/1/11 22:36
 * <p>
 * Version: 0.0.1
 */
@EnableAsync
public class ApplicationListenerDemo implements ApplicationEventPublisherAware {
    public static void main(String[] args) {
        AnnotationConfigApplicationContext applicationContext = new AnnotationConfigApplicationContext();
        applicationContext.register(ApplicationListenerDemo.class);
        // 方法一：基于 Spring 接口：向 Spring 应用上下文注册事件
        //   a 方法：基于 ConfigurableApplicationContext API 实现
        //   b 方法：基于 ApplicationListener 注册为 Spring Bean
        //          通过 Configuration Class 来注册
        // 方法二：基于 Spring 注解：@org.springframework.context.event.EventListener
        applicationContext.addApplicationListener(event -> {
            printable(event.toString());
        });
        applicationContext.register(MyApplicationListener.class);

        applicationContext.refresh();
        applicationContext.start();
        applicationContext.stop();
        applicationContext.close();
    }

    private static void printable(Object object){
        System.out.printf("[线程 %s] : %s %n" , Thread.currentThread().getName(),object);
    }

    // 基于注解实现
    @EventListener
    @Order(2)
    public void onApplicationEvent(ContextRefreshedEvent event){
        printable("@EventListener(onApplicationEvent) - 接收到Spring ContextRefreshedEvent");
    }
    @EventListener
    @Order(1)
    public void onApplicationEvent1(ContextRefreshedEvent event){
        printable("@EventListener(onApplicationEvent1) - 接收到Spring ContextRefreshedEvent");
    }
    @EventListener
    @Order(1)
    @Async
    public void onApplicationEventAsync(ContextRefreshedEvent event){
        printable("@EventListener(onApplicationEvent1) - 接收到Spring ContextRefreshedEvent");
    }
    @EventListener
    public void onApplicationEvent(ContextStartedEvent event){
        printable("@EventListener(onApplicationEvent) - 接收到Spring ContextStartedEvent");
    }
    @EventListener
    public void onApplicationEvent(ContextStoppedEvent event){
        printable("@EventListener(onApplicationEvent) - 接收到Spring ContextStoppedEvent");
    }
    @EventListener
    public void onApplicationEvent(ContextClosedEvent event){
        printable("@EventListener(onApplicationEvent) - 接收到Spring ContextClosedEvent");
    }

    @Override
    public void setApplicationEventPublisher(ApplicationEventPublisher applicationEventPublisher) {
        applicationEventPublisher.publishEvent(new ApplicationEvent("hello world"){
        });
        applicationEventPublisher.publishEvent("hello world");
        applicationEventPublisher.publishEvent(new MyPayloadApplicationListener(this,"Payload"));
    }

    private static class MyApplicationListener implements ApplicationListener<ContextRefreshedEvent> {
        @Override
        public void onApplicationEvent(ContextRefreshedEvent event) {
            printable("MyApplicationListener - 接收到 Spring 事件："+event.toString());
        }
    }
//    private static class MyPayloadApplicationListener extends PayloadApplicationEvent<String> {
    private static class MyPayloadApplicationListener<String> extends PayloadApplicationEvent<String> {
        /**
         * Create a new PayloadApplicationEvent.
         *
         * @param source  the object on which the event initially occurred (never {@code null})
         * @param payload the payload object (never {@code null})
         */
        public MyPayloadApplicationListener(Object source, String payload) {
            super(source, payload);
        }

    }
}
