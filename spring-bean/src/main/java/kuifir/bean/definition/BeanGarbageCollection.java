package kuifir.bean.definition;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;

/**
 * Package: kuifir.bean.definition
 * <p>
 * Description： Bean 垃圾回收（GC）示例
 * <p>
 * Author: baci
 * <p>
 * Date: Created in 2022/9/3 10:55
 * <p>
 * Version: 0.0.1
 */
public class BeanGarbageCollection {
    public static void main(String[] args) throws InterruptedException {
        // 创建 BeanFactory 容器
        AnnotationConfigApplicationContext applicationContext = new AnnotationConfigApplicationContext();
        // 注册 Configuration Class （配置类）
        applicationContext.register(BeanInitializationDemo.class);
        // 启动应用上下文
        applicationContext.refresh();
        // 关闭应用上下文呢
        applicationContext.close();
        // 强制触发GC
        System.gc();
        Thread.sleep(5000L);
    }

}
