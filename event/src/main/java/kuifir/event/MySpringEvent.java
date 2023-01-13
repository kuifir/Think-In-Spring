package kuifir.event;

import org.springframework.context.ApplicationEvent;

/**
 * Package: kuifir.event
 * <p>
 * Description： 自定义 Spring 事件
 * <p>
 * Author: baci
 * <p>
 * Date: Created in 2023/1/12 15:17
 * <p>
 * Version: 0.0.1
 */
public class MySpringEvent extends ApplicationEvent {
    /**
     * Create a new {@code ApplicationEvent}.
     *
     * @param source the object on which the event initially occurred or with
     *               which the event is associated (never {@code null})
     */
    public MySpringEvent(String source) {
        super(source);
    }

    @Override
    public String getSource() {
        return (String)super.getSource();
    }

    public String getMessage(){
        return getSource();
    }
}
