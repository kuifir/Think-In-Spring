package kuifir.event;

import org.springframework.context.ApplicationListener;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.event.ApplicationContextEvent;
import org.springframework.context.event.ContextRefreshedEvent;

import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.Set;

/**
 * Package: kuifir.event
 * <p>
 * Description： 层次性 Spring 事件传播示例
 * <p>
 * Author: baci
 * <p>
 * Date: Created in 2023/1/12 12:48
 * <p>
 * Version: 0.0.1
 */
public class HierarchicalSpringEventPropagateDemo {
    public static void main(String[] args) {
        // 1. 创建 parent Spring 应用上下文
        ConfigurableApplicationContext parentContext = createSpringContext("parent-context");
        // 2. 创建 current Spring 应用上下文
        AnnotationConfigApplicationContext currentContext = createSpringContext("current-context");
        // 3. current -> parent
        currentContext.setParent(parentContext);

        // 注册 MyListener 到 parent Spring 应用上下文
        parentContext.addApplicationListener(new MyEventListener());
        currentContext.register(MyEventListener.class);

        // 4.启动 parent Spring 应用上下文
        parentContext.refresh();
        // 5.启动 current Spring 应用上下文
        currentContext.refresh();
        // 关闭所有 Spring 应用上下文
        currentContext.close();
        parentContext.close();

    }

    private static AnnotationConfigApplicationContext createSpringContext(String id){
        AnnotationConfigApplicationContext applicationContext = new AnnotationConfigApplicationContext();
        applicationContext.setId(id);
        return applicationContext;
    }

    private static class MyEventListener implements ApplicationListener<ApplicationContextEvent>{

        // 解决事件层次性传播
        private static Set<ApplicationContextEvent> applicationContextEvents = new LinkedHashSet();
        @Override
        public void onApplicationEvent(ApplicationContextEvent event) {
            if(applicationContextEvents.add(event)){
                System.out.printf("监听到Spring应用上下文[ID: %s] 发布的%s事件%n",
                        event.getApplicationContext().getId(),event.getClass().getSimpleName());
            }

        }
    }
}
