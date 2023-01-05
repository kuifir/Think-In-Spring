package kuifir.resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ResourceLoaderAware;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.support.AbstractApplicationContext;
import org.springframework.core.io.ResourceLoader;

import javax.annotation.PostConstruct;

/**
 * Package: kuifir.resource
 * <p>
 * Description： 依赖注入{@link org.springframework.core.io.ResourceLoader}示例
 * <p>
 * Author: baci
 * <p>
 * Date: Created in 2023/1/5 17:25
 * <p>
 * Version: 0.0.1
 */
public class InjectingResourceLoaderDemo implements ResourceLoaderAware {

    @Autowired
    private ResourceLoader autowiredResourceLoader;

    private ResourceLoader resourceLoader;

    @Autowired
    private AbstractApplicationContext applicationContext;

    @PostConstruct
    public void init(){
        System.out.println(autowiredResourceLoader == resourceLoader);
        System.out.println(applicationContext == resourceLoader);
    }
    public static void main(String[] args) {
        AnnotationConfigApplicationContext applicationContext = new AnnotationConfigApplicationContext();
        applicationContext.register(InjectingResourceLoaderDemo.class);
        applicationContext.refresh();
        applicationContext.close();
    }

    @Override
    public void setResourceLoader(ResourceLoader resourceLoader) {
        this.resourceLoader = resourceLoader;
    }
}
