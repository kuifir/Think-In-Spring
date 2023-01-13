package kuifir.event;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.ApplicationEventPublisherAware;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import javax.annotation.PostConstruct;

/**
 * Package: kuifir.event
 * <p>
 * Description： 注入{@link org.springframework.context.ApplicationEventPublisher} 示例
 * <p>
 * Author: baci
 * <p>
 * Date: Created in 2023/1/12 15:52
 * <p>
 * Version: 0.0.1
 */
public class InjectingApplicationEventPublisherDemo implements ApplicationEventPublisherAware,
        ApplicationContextAware {

    @Autowired
    private ApplicationContext applicationContext;

    @Autowired
    private ApplicationEventPublisher applicationEventPublisher;

    @PostConstruct
    public void init(){
        applicationContext.publishEvent(new MySpringEvent("The event from @Autowired ApplicationContext")); //3
        applicationEventPublisher.publishEvent(new MySpringEvent("The event from @Autowired applicationEventPublisher")); //4
    }
    public static void main(String[] args) {
        AnnotationConfigApplicationContext applicationContext = new AnnotationConfigApplicationContext();
        applicationContext.register(InjectingApplicationEventPublisherDemo.class);
        applicationContext.addApplicationListener(new MySpringEventListener());
        applicationContext.refresh();
        applicationContext.close();
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        applicationContext.publishEvent(new MySpringEvent("The event from ApplicationContextAware#setApplicationContext"));// 2
    }

    @Override
    public void setApplicationEventPublisher(ApplicationEventPublisher applicationEventPublisher) {
        applicationEventPublisher.publishEvent(new MySpringEvent("The event from ApplicationEventPublisherAware#setApplicationEventPublisher"));// 1
    }
}
