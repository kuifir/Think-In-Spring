package kuifir.ioc.bean.scope;

import org.springframework.beans.factory.ObjectFactory;
import org.springframework.beans.factory.config.Scope;
import org.springframework.core.NamedThreadLocal;

import java.util.HashMap;
import java.util.Map;

/**
 * Package: kuifir.ioc.bean.scope
 * <p>
 * Description： 自定义ThreadLocal级别作用域
 * <p>
 * Author: baci
 * <p>
 * Date: Created in 2022/12/31 17:22
 * <p>
 * Version: 0.0.1
 */
public class ThreadLocalScope implements Scope {

    public final static String SCOPE_NAME = "thread-local";

    private final ThreadLocal<Map<String, Object>> threadLocal =
            new NamedThreadLocal<Map<String, Object>>("SimpleThreadScope") {
                @Override
                protected Map<String, Object> initialValue() {
                    return new HashMap<>();
                }
            };

    @Override
    public Object get(String name, ObjectFactory<?> objectFactory) {
        Map<String, Object> context = threadLocal.get();
        Object object = context.get(name);
        if(object == null){
            object = objectFactory.getObject();
            context.put(name,object);
        }
        return object;
    }

    @Override
    public Object remove(String name) {
        Map<String, Object> context = threadLocal.get();
        return context.remove(name);
    }

    @Override
    public void registerDestructionCallback(String name, Runnable callback) {

    }

    @Override
    public Object resolveContextualObject(String key) {
        Map<String, Object> context = threadLocal.get();
        return context.get(key);
    }

    @Override
    public String getConversationId() {
        return String.valueOf(Thread.currentThread().getId());
    }
}
