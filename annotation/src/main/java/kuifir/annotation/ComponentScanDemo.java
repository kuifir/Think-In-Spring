package kuifir.annotation;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.ComponentScan;

/**
 * Package: kuifir.annotation
 * <p>
 * Description： {@link org.springframework.stereotype.Component} 扫描示例
 * <p>
 * Author: baci
 * <p>
 * Date: Created in 2023/1/21 15:50
 * <p>
 * Version: 0.0.1
 * @see org.springframework.stereotype.Component
 * @see org.springframework.context.annotation.ComponentScan
 */

// 1、AnnotationConfigApplicationContext.register会注册启动类为AnnotatedGenericBeanDefinition，abd包括ClassMetadata和AnnotatedTypeMetadata
// 2、refresh->invokeBeanFactoryPostProcessors->PostProcessorRegistrationDelegate.invokeBeanFactoryPostProcessors
// 里面拿到ConfigurationAnnotationProcessor处理器处理（通过AnnotationConfigUtils注册）
// 3、通过过滤排除各internalXXX，确定启动类为配置类
// 4、创建ConfigurationClassParser，并调用parse分析
// 5、创建ConfigurationClass，参数AnnotationMetadata和beanName
// 6、通过ConfigurationClass,创建SourceClass,这里有一个疑惑，创建的时候传入Class，并非AnnotationMetadata，创建的时候会再次通过自省AnnotationMetadata.introspect再次分析注解元信息。实际在register生成bd时已经调用多一次。
// 7、调用doProcessConfigurationClass处理诸如@ImportResource，@PropertySources，@ComponentScans等
// 8、在处理ComponentScans时，创建ClassPathBeanDefinitionScanner。在scanner.doScan中
// findCandidateComponents(String basePackage)->scanCandidateComponents->拼接路径classpath*:+basePackage+**/*.class
// 9、GenericApplicationContext->AbstractApplicationContext->findPathMatchingResources->getResources->findAllClassPathResources....->最终通过ClassLoader.getResources(path)获取路径下的所有Url资源并生成UrlResource
// 10、创建SimpleMetadataReader，通过ClassReader读取class文件，创建AnnotationMetadataReadingVisitor(ASM class visotor)。这一块逻辑不懂，classReader已经读入文件，还需要ASM做什么？
// 11、最终生成ScannedGenericBeanDefinition
// 12、通过postProcessBeanDefinition及AnnotationConfigUtils.processCommonDefinitionAnnotations，补充bd其他属性，比如是否lazy，初始化接口名称等等
// 13、注册bd结束
@ComponentScan(basePackages = "kuifir.annotation")
public class ComponentScanDemo {
    public static void main(String[] args) {
        AnnotationConfigApplicationContext applicationContext = new AnnotationConfigApplicationContext();
        applicationContext.register(ComponentScanDemo.class);
        applicationContext.refresh();

        TestClass bean = applicationContext.getBean(TestClass.class);
        System.out.println(bean);
        applicationContext.close();
    }
}
// 1. 正常情况下，在 `Spring MVC` 中，启动的过程中会初始化一个 Root Spring WebApplicationContext（父）和一个 Spring WebApplicationContext（子），类型都为 XMLWebApplicationContext
// 2. **父** Spring 应用上下文在监听到 Servlet 容器启动事件进行创建，**子** Spring 应用上下文是在初始化 FrameworkServlet 过程，创建后都会调用其 AbstractApplicationContext#refresh() 刷新上下文
// 3. 在 Spring 应用上下文刷新过程中，AbstractApplicationContext#refresh#obtainFreshBeanFactory#refreshBeanFactory#loadBeanDefinitions 方法中会加载出 BeanDefinition 们
// 4. 例如你的 `spring-mvc.xml` 配置文件中配置了 `<context:component-scan base-package="org.springframework.mvc.demo.*" />`，那么最后会通过 ContextNamespaceHandler 进行解析，又会找到 ComponentScanBeanDefinitionParser#parse 进行解析，解析过程中你会发现是通过 **ClassPathBeanDefinitionScanner#doScan** 方法扫描出指定包路径下的 BeanDefinition 们