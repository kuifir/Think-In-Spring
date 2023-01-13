package kuifir.event;

import org.springframework.context.ApplicationListener;

/**
 * Package: kuifir.event
 * <p>
 * Description： 自定义 Spring 事件监听器
 * <p>
 * Author: baci
 * <p>
 * Date: Created in 2023/1/12 15:20
 * <p>
 * Version: 0.0.1
 */
public class MySpringEventListener implements ApplicationListener<MySpringEvent> {
    @Override
    public void onApplicationEvent(MySpringEvent event) {
        System.out.printf(" [线程 %s] 监听到事件 : %s %n",Thread.currentThread().getName(),event.getMessage());
    }
}
