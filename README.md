# Think-In-Spring
Follow the lessons of the geekbang 

## Spring Bean
### Bean的实例化 
#### 常规方式
  1. 通过构造器（配置元信息：XML、Java 注解和 Java API ）
  2. 通过静态工厂方法（配置元信息：XML 和 Java API ）  

    示例：  
     - 通过xml定义一个bean ，通过class 和 factory-method 确定 作为工厂方法的静态方法
        ```xml
        <bean id="user-by-static-method" class="kuifir.bean.definition.BeanInstantiationDemo"
        factory-method="createUser"/>
        ```
     - 然后输出一下，看是否成功创建
        ```java
          BeanFactory beanFactory = new ClassPathXmlApplicationContext("classpath:\\META-INF\\bean-instantiation-context.xml");
          User user = beanFactory.getBean("user-by-static-method",User.class);
          System.out.println(user);
        ```
  3. 通过 Bean 工厂方法（配置元信息：XML和 Java API ）  
     示例：
     - 创建一个工厂，通过工厂实例和产生实例的方法产生目标实例
       ```xml
       <!--实例(Bean)方法实例化 Bean-->
           <bean id="user-by-instance-method" factory-bean="userFactory" factory-method="createUser"/>
       
           <bean id="userFactory" class="kuifir.bean.factory.DefaultUserFactory"/>
       ```
     - 输出一下看能否创建成功，并且看一下是否和静态方法创建的对象是同一个对象(否)
       ```java
         User userByInstanceMethod = beanFactory.getBean("user-by-instance-method",User.class);
         System.out.println(userByInstanceMethod);
         System.out.println(user == userByInstanceMethod);
       ```
  4. 通过 FactoryBean（配置元信息：XML、Java 注解和 Java API ）
     示例：
     - 创建一个实现FactoryBean接口的类，并实现其中的方法，创建这个类的示例，就可以产生一个链接，获取到这个类的示例
       ```java
         public class UserFactoryBean implements FactoryBean {
             @Override
             public Object getObject() throws Exception {
                 return BeanInstantiationDemo.createUser();
             }
         
             @Override
             public Class<?> getObjectType() {
                 return User.class;
             }
         }
       ```
       ```xml
           <!--FactoryBean 实例化 Bean-->
           <bean id="user-by-factory-bean" class="kuifir.bean.factory.UserFactoryBean"/>
       ```
#### 特殊方式
   1. 通过 ServiceLoaderFactoryBean（配置元信息：XML、Java 注解和 Java API ）  
      - 原生ServiceLoader示例
        - 创建java.util.ServiceLoader 类中第一行的定义的目录
          ```java
            private static final String PREFIX = "META-INF/services/";
          ```
        - 在上面创建的目录下创建一个文件，文件名为接口的全类名
        - 把实现类的类名写到上面的文件中（相同的类写多个只会生成一个，会去重）
        - 通过静态方法 ``` ServiceLoader.load(Class<S> service, ClassLoader loader)```
        来获取同类型的一个ServiceLoader,然后通过迭代器方法获取并作出输出。
          ```java
            ServiceLoader<UserFactory> serviceLoader = ServiceLoader.load(UserFactory.class, Thread.currentThread().getContextClassLoader());
            Iterator<UserFactory> iterator = serviceLoader.iterator();
            while (iterator.hasNext()){
                UserFactory userFactory = iterator.next();
                System.out.println(userFactory.createUser());
            }
          ```
      - spring ServieLoaderFactoryBean示例
        - 实例化一个ServiceLoaderFactoryBean对象，其中配置一个servecieType属性，
            ```java
              <bean id="userFactoryServiceLoader" class="org.springframework.beans.factory.serviceloader.ServiceLoaderFactoryBean">
                  <property name="serviceType" value="kuifir.bean.factory.UserFactory"/>
              </bean>
            ```
        - 依赖查找获取ServiceLoader
            ```java
              ServiceLoader<UserFactory> serviceLoader = beanFactory.getBean("userFactoryServiceLoader",ServiceLoader.class);
            ```
      
   2. 通过 AutowireCapableBeanFactory#createBean(java.lang.Class, int, boolean) 
      
      示例
      
      - 先通过ApplicationContext 获取 AutowireCapableBeanFactory,通过creatBean方法获取factoryBean
        ```java
          ApplicationContext applicationContext = new ClassPathXmlApplicationContext("classpath:\\META-INF\\special-bean-instantiation-context.xml");
          // 通过applicationContext 获取 AutowireCapableBeanFactory
          AutowireCapableBeanFactory beanFactory = applicationContext.getAutowireCapableBeanFactory();
        ```
      - 通过FactoryBean创建示例，参数类型必须是实现类不能是接口
        ```java 
           UserFactory userFactory = beanFactory.createBean(DefaultUserFactory.class);// 类型不能使用接口，要使用实现类
            System.out.println(userFactory.createUser());
        ```
      
   3. 通过 BeanDefinitionRegistry#registerBeanDefinition(String,BeanDefinition)
      示例
      - 创建容器
      ```java         
      AnnotationConfigApplicationContext applicationContext = new AnnotationConfigApplicationContext();
      ```
      - 注册 Configuration Class (配置类)这个类相当于代替了xml文件
      ```java
              applicationContext.register(AnnotationBeanDefinitionDemo.class); 
      ```
      - 启动运用上下文
      ```java
      applicationContext.refresh(); 
      ```
      - 注册BeanDefinition
        - 通过注解 注册BeanDefinition
          - 通过@Bean 方式定义
            ```java
            // 1. 通过@Bean 方式定义
            @Bean(name = {"user", "kuifir-user"})
            public User user(){
                User user = new User();
                user.setId(11L);
                user.setName("kuifir");
                return user;
            } 
            ```
          - 通过@Component
            ```java
               // 2. 通过@Component
               @Component // 可以定义当前类作为SpringBean(组件)
               public static class Config{ 
            ```
          - 通过@Import导入
            ```java 
                // 3. 通过@Import导入
                @Import(AnnotationBeanDefinitionDemo.Config.class)//通过@Import来进行导入
            ```
        - 通过BeanDefinition 注册 API 实现
          - 1.命名Bean 的注册方式
            ```java
            BeanDefinitionBuilder beanDefinitionBuilder = BeanDefinitionBuilder.genericBeanDefinition(beanClass);
            beanDefinitionBuilder.addPropertyValue("id",1).addPropertyValue("name","kuifir");
            // 注册BeanDefinition
            registry.registerBeanDefinition(beanName,beanDefinitionBuilder.getBeanDefinition()); 
            ```
          - 2.非命名Bean 的注册方式
             ```java
             BeanDefinitionBuilder beanDefinitionBuilder = BeanDefinitionBuilder.genericBeanDefinition(beanClass);
             beanDefinitionBuilder.addPropertyValue("id",1).addPropertyValue("name","kuifir");
             //注册BeanDefinition
             BeanDefinitionReaderUtils.registerWithGeneratedName(beanDefinitionBuilder.getBeanDefinition(),registry); 
             ```
      - 进行依赖查找
        ```java
          Map<String, Config> configBeans = applicationContext.getBeansOfType(Config.class);
          System.out.println("Config 类型的所有的Bean" + configBeans);
          Map<String, User> userBeans = applicationContext.getBeansOfType(User.class);
          System.out.println("User 类型的所有的Bean" + userBeans); 
        ```
      - 显式关闭Spring应用上下文
        ```java
          applicationContext.close(); 
        ```

### Bean的初始化

- @PostConstruct 标注方法 

  示例

  - 创建一个有@PostConstruct标注的方法

    ```java
      // 1.基于 @PostConstruct 注解
        @PostConstruct
        public void init(){
            System.out.println("@PostConstruct: UserFactory 初始化中");
        }
    ```

  - 创建实例会自动回调初始化方法

    ```java
    @Configuration //Configuration Class
    public class BeanInitializationDemo {
        public static void main(String[] args) {
            // 创建BeanFactory容器
            AnnotationConfigApplicationContext applicationContext = new AnnotationConfigApplicationContext();
            //注册 Configuration Class(配置类)
            applicationContext.register(BeanInitializationDemo.class);
            // 启动Spring 应用上下文
            applicationContext.refresh();
            // 依赖查找UserFactory 此时应该自动回调@PostConsturct 方法
            UserFactory userFactory = applicationContext.getBean(UserFactory.class);
    
            // 关闭Spring 应用上下文
            applicationContext.close();
        }
    
        @Bean(initMethod = "initUserFactory")
        public UserFactory userFactory(){
            return new DefaultUserFactory();
        }
    }
    ```

    

- 实现 InitializingBean 接口的 afterPropertiesSet() 方法 

  - 实现Spring InitializingBean接口，实现 afterPropertiesSet()方法

    ```java
      @Override
      public void afterPropertiesSet() throws Exception {
        System.out.println("InitializingBean#afterPropertiesSet：UserFactory 初始化中");
      }
    ```

  - 创建实例会自动回调初始化方法

- 自定义初始化方法 

  - XML 配置： 

  - Java 注解：@Bean(initMethod=”init”)

    - 在@Bean中增加initMethid属性，值为自定义初始化方法

      ```java
         @Bean(initMethod = "initUserFactory")
          public UserFactory userFactory(){
              return new DefaultUserFactory();
         }
      ```

  -  Java API：AbstractBeanDefinition#setInitMethodName(String)

思考：假设以上三种方式均在同一 Bean 中定义，那么这些方法的执行顺序是怎样？
```text
@PostConstruct: UserFactory 初始化中
InitializingBean#afterPropertiesSet：UserFactory 初始化中
自定义初始化方法 initUserFactory()：UserFactory 初始化中 
```

###  延迟初始化 

- XML 配置： 

```xml
<bean lazy-init=”true” ... /
```

- Java 注解：@Lazy(true) 

- 思考：当某个 Bean 定义为延迟初始化，那么，Spring 容器返回的对象与非延迟的对象存在怎样的差异？

  - 非延迟的对象在应用上下文初始化之前进行初始化

  - 延迟的对象在应用上下文初始化之后进行初始化 

###  销毁 Spring Bean

- @PreDestroy 标注方法 
  实例

  - 创建一个带有@PreDestroy注解的方法,应用上下文关闭前会自动回调

    ```java
    
        @PreDestroy
        public void preDistroy(){
            System.out.println("@PreDestroy: UserFactory 销毁中...");
        }
    ```

    

- 实现 DisposableBean 接口的 destroy() 方法

  ```java
      @Override
      public void destroy() throws Exception {
          System.out.println("DisposableBean#destroy() : UserFactory 销毁中...");
      }
  ```

-  自定义销毁方法

  - XML 配置： 

  -  Java 注解：@Bean(destroy=”destroy”) 

    ```java
    @Bean(initMethod = "initUserFactory",destroyMethod = "doDestroy")
    ```

  -  Java API：AbstractBeanDefinition#setDestroyMethodName(String)

-  思考：假设以上三种方式均在同一 Bean 中定义，那么这些方法的执行顺序是怎样？  

  ```text
  应用上下文准备关闭...
  @PreDestroy: UserFactory 销毁中...
  DisposableBean#destroy() : UserFactory 销毁中...
  自定义销毁方法 doDestroy()：UserFactory销毁中
  应用上下文已关闭...
  ```

### 垃圾回收 Spring Bean

简单实现

1. 关闭 Spring 容器（应用上下文）
2. 执行 GC（不是必须的，可能还要等一段时间） 

3. Spring Bean 覆盖的 finalize() 方法被回调 

## Spring IoC 依赖查找 （Dependency Lookup）

### 依赖查找的今世前生 

-  单一类型依赖查找 
  - JNDI - javax.naming.Context#lookup(javax.naming.Name) 
  - JavaBeans - java.beans.beancontext.BeanContext

-  集合类型依赖查找 
  - java.beans.beancontext.BeanContext

- 层次性依赖查找 
  -  java.beans.beancontext.BeanContext  

### 单一类型依赖查找

 单一类型依赖查找接口 - BeanFactory 

-  根据 Bean 名称查找 

  - getBean(String) 

  - Spring 2.5 覆盖默认参数：getBean(String,Object...)  

- 根据 Bean 类型查找 

  - Bean 实时查找 
    - Spring 3.0 getBean(Class)
    - Spring 4.1 覆盖默认参数：getBean(Class,Object...)
    
  - Spring 5.1 Bean 延迟查找 
    - getBeanProvider(Class) 
      示例：
    
      ```java
      ObjectProvider<String> beanProvider =   applicationContext.getBeanProvider(String.class);
      System.out.println(beanProvider.getObject());
      ```
    
      
    
    -  getBeanProvider(ResolvableType)

-  根据 Bean 名称 + 类型查找：getBean(String,Class) 

### 集合类型依赖查找 - ListableBeanFactor 

- 根据 Bean 类型查找 
  -  获取同类型 Bean 名称列表 
    -  getBeanNamesForType(Class) 
    -  Spring 4.2 getBeanNamesForType(ResolvableType) 
  -  获取同类型 Bean 实例列表
    - getBeansOfType(Class) 以及重载方法 

-   通过注解类型查找 
  -  Spring 3.0 获取标注类型 Bean 名称列表
    -  getBeanNamesForAnnotation(Class<? extends Annotation>) 
  -  Spring 3.0 获取标注类型 Bean 实例列表
    -   getBeansWithAnnotation(Class<? extends Annotation>) 
  -  Spring 3.0 获取标注类型 Bean 实例列表
    - findAnnotationOnBean(String,Class <? extends Annotation) 

### 层次性依赖查找 

// 评论区说具体的类可能有错误

- 双亲 BeanFactory：getParentBeanFactory()

-  层次性查找 
  -  根据 Bean 名称查找 • 基于 containsLocalBean 方法实现 
  - 根据 Bean 类型查找实例列表 
    -  单一类型：BeanFactoryUtils#beanOfTypeIncludingAncestors 
    -  集合类型：BeanFactoryUtils#beansOfTypeIncludingAncestors 
  -  根据 Java 注解查找名称列表 • BeanFactoryUtils#beanNamesForTypeIncludingAncestors  

### 延迟依赖查找

-  org.springframework.beans.factory.ObjectFactory 

-  org.springframework.beans.factory.ObjectProvider 

  -  Spring 5 对 Java 8 特性扩展 

    -  函数式接口 
      - Stream 扩展 - stream()
      -  ifAvailable(Consumer)   

    -  Stream 扩展 - stream()  

### 安全依赖查找

-  依赖查找安全性对比 

  | 依赖查找类型 | 代表实现                           | 是否安全 |
  | ------------ | ---------------------------------- | -------- |
  | 单一类型查找 | BeanFactory#getBean                | 否       |
  |              | ObjectFactory#getObject            | 否       |
  |              | ObjectProvider#getIfAvailable      | 是       |
  |              |                                    |          |
  | 集合类型查找 | ListableBeanFactory#getBeansOfType | 是       |
  |              | ObjectProvider#stream              | 是       |

   注意：层次性依赖查找的安全性取决于其扩展的单一或集合类型的 BeanFactory 接口

### 内建可查找的依赖 

### 依赖查找中的经典异常   
## 依赖来源
- 依赖注入的来源要比依赖查找的来源多一个非spring容器进行管理的对象
- Spring IOC的三种依赖来源，自定义注册的Spring bean、内建的Spring bean以及内建的可注入的依赖，
其中自定义注册的Spring bean基本上是通过xml、注解或者api注册BeanDefination创建的，
内建的Spring bean是通过registerSingleton()创建的
，内建的可注入的依赖是通过registerResolveDependency()创建的，
后续如果我们需要往Spring容器里放入一些非Spring托管的bean但又可以被依赖注入的, 
可以通过registerResolveDependency() API实现


## SpringBean作用域
- Singleton Bean 无论是依赖注入还是依赖查找，均为同一个对象
Prototype Bean 无论是依赖注入还是依赖查找，均为新生成的一个对象
- 如果依赖注入集合对象，Singleton Bean 和 Prototype Bean 均会存在一个
Prototype Bean 有别于其他地方依赖注入的 Prototype Bean(依然是重新生成的)

- 无论是 Singleton Bean 还是Prototype Bean 均会执行初始化方法回调
不过仅有Singleton Bean 会执行 销毁方法回调

## Spring Bean生命周期

### Spring Bean元信息配置
### Spring Bean元信息解析
### Spring BeanDefinition 注册
- 检验bean是否注册过，如果注册过，查看是否允许重复注册，不允许抛出异常，允许放进map中
- bean没有注册过，加锁，存放在map中，beanName存放在ArrayList中
### Spring BeanDefinition的合并阶段
 - 没有继承的Bean定义最终会生成RootBeanDefinition，这种BeanDefinition不需要合并，禁止对setParentName方法设置值。而存在parent的Bean定义则生成的是普通的GenericBeanDefinition，需要合并parent的BeanDefinition属性。
 - AbstractBeanFactory的getMergedBeanDefinition(String beanName)方法提供了BeanDefinition的合并逻辑的实现。执行最终合并的是其中的mbd.overrideFrom(bd),将子bean和父bean的属性进行合并。
### Spring Bean Class加载阶段
- AbstractBeanFactory#resolveBeanClass中将string类型的beanClass 通过当前线程Thread.currentThread().getContextClassLoader(),Class.forName来获取class对象,将beanClass变为Class类型对象
### Spring Bean 实例化前
- InstantiationAwareBeanPostProcessor#postProcessBeforeInstantiation 若返回为null，则返回默认的实例对象;
若返回不为null，则此对象为最终返回的对象，也就是实例化了一个与配置文件中不同的新的对象
### Spring Bean 实例化阶段
- 想要生成一个 Bean，那么需要先根据其 Class 对象创建一个 Bean 的实例对象，然后进行一系列的初始化工作。在创建 Bean 的实例对象的过程中，传统的实例化方式（大多数情况）使用 InstantiationStrategy 这个接口来实现，获取默认构造器，然后通过它创建一个实例对象。还有一种方式就是需要通过构造器注入相关的依赖对象，首先会获取构造器中的参数对象（依赖注入，入口：DefaultListableBeanFactory#resolveDependency，前面已经分析过了），根据这些需要注入的依赖对象，通过指定的构造器创建一个实例对象。
- 之所以类型的优先级最高，是因为在
  ``` java DefaultListableBeanFactory#findAutowireCandidates 中：
  String[] candidateNames = BeanFactoryUtils.beanNamesForTypeIncludingAncestors(this, requiredType, true, descriptor.isEager());
  ```
   candidateNames 是调用 lbf.getBeanNamesForType 获取的，也就是说是通过类型获取的Bean名称列表，通过名称列表遍历获取并获取Bean实例，遍历时会筛选 Qualifer :
  ``` java 
  if (!isSelfReference(beanName, candidate) && isAutowireCandidate(candidate, fallbackDescriptor) &&
  	(!multiple || getAutowireCandidateResolver().hasQualifier(descriptor))) {
  	addCandidateEntry(result, candidate, descriptor, requiredType);
  }
  ```
  然后返回结果 matchingBeans 也就是：
   ```java 
  Map<String, Object> matchingBeans = findAutowireCandidates(beanName, type, descriptor); 
    ```
  接下来会 matchingBeans 中筛选出唯一的一个 beanName，即determineAutowireCandidate方法的结果：
  ```java 
  autowiredBeanName = determineAutowireCandidate(matchingBeans, descriptor);
  ```
  determineAutowireCandidate 方法中根据 primary -> priority -> resolvableDependencies.containsValue(beanInstance) -> matchesBeanName(candidateName, descriptor.getDependencyName())；
  matchesBeanName 中 就是通过名称或者别名来匹配。
  所以，顺序为 ` type->qualifer-> primary -> priority -> resolvableDependencies.containsValue(beanInstance) -> beanName `
  ### Spring Bean 实例化后阶段
  ```java
  // 实例化后的回调
  @Override
  public boolean postProcessAfterInstantiation(Object bean, String beanName) throws BeansException {
      if (ObjectUtils.nullSafeEquals("user", beanName) && User.class.equals(bean.getClass())) {
          // 此时属性还没有赋值，返回false 表示对象不允许属性赋值（将元信息填入到属性指）
          return false;
      }
      return true
  }
  ```
### Spring Bean 属性赋值前阶段
-  postProcessBeforeInstantiation()在bean实例化前回调,返回实例则不对bean实例化,返回null则进行spring bean实例化(doCreateBean);
-  postProcessAfterInstantiation()在bean实例化后在填充bean属性之前回调,返回true则进行下一步的属性填充,返回false:则不进行属性填充
-  postProcessProperties在属性赋值前的回调在applyPropertyValues之前操作可以对属性添加或修改等操作最后在通过applyPropertyValues应用bean对应的wapper对象
### Aware接口回调阶段
- BeanNameAware
- BeanClassLoaderAware
- BeanFactoryAware

后面这几个在ApplicationContext的生命周期中，基本的BeanFactory 回调不到这些接口
- EnvironmentAware
- EmbeddedValueResolverAware
- ResourceLoaderAware
- ApplicationEventPublisherAware
- MessageSourceAware
- ApplicationContextAware
### Spring Bean 初始化前阶段

通过XmlBeanDefinitionReader loadBeanDefinitions是将xml中bean对应的beanDefinition注册到beanFactory中,底层通过BeanDefinitionReaderUtils#registerBeanDefinition()方法实现,这个在BeanDefinition注册阶段讲过,这个时候只是注册beanDefinition没有像ApplicationContext.refresh()中的registerBeanPostProcessors()将bean post Processor添加到beanFactory的beanPostProcessors list集合中操作,所以xml读取的时候需要手动的addBeanPostProcessor;如果通过ClassPathXmlApplicationContext创建ApplicationContext的方式xml中定义MyInstantiationAwareBeanPostProcessor是可以的,因为ClassPathXmlApplicationContext创建时会执行refresh()操作会从beanFactory中找到MyInstantiationAwareBeanPostProcessor bean后添加到beanPostProcessors的list集合中

InstantiationAwareBeanPostProcessor接口继承了BeanPostProcessor接口，所以通过实现InstantiationAwareBeanPostProcessor接口就可以重写以下两个初始化拦截方法。
 postProcessBeforeInitialization
初始化前拦截操作，
 postProcessAfterInitialization
- 执行时机：
在AbstractAutowireCapableBeanFactory的initializeBean初始化方法中：
  - ①先是执行了invokeAwareMethods方法来对实现BeanNameAware、BeanClassLoaderAware、BeanFactoryAware三个接口的bean进行set属性设置。
  - ②然后，会调用applyBeanPostProcessorsBeforeInitialization方法去遍历执行所有的BeanPostProcessor的postProcessBeforeInitialization方法。
  - ③接着是invokeInitMethods方法。
  - ④最后，会调用applyBeanPostProcessorsAfterInitialization方法去遍历执行所有的BeanPostProcessor的postProcessAfterInitialization方法。
### Spring Bean 初始化阶段：@PostConstruct、InitializingBean 以及 自定义方法

- PostConstruct(非必须)也会在初始化前阶段执行，因为它跟PreDestroy注解一样被CommonAnnotationBeanPostProcessor管理和执行，CommonAnnotationBeanPostProcessor继承了InitDestroyAnnotationBeanPostProcessor，这个继承类实现了BeanPostProcessor接口，在初始化前阶段InitDestroyAnnotationBeanPostProcessor的postProcessBeforeInitialization方法会被调用，这其中就执行了metadata.invokeInitMethods(bean, beanName);这个方法，也就是注解了PostConstruct的方法。

### Spring Bean 初始化后阶段
在填充bean属性完成之后initializeBean()中有四个过程
- 1.aware的接口回调(不包括ApplicationContext相关)
- 2.postProcessBeforeInitialization()包括两部分关于ApplicationContextAwareProcessor的aware接口回调和自定义bean post processor的postProcessBeforeInitialization回调
- 3.invokeInitMethods() bean初始化的回调比如实现InitializingBean接口
- 4.applyBeanPostProcessorsAfterInitialization() bean初始化之后的回调
### Spring Bean 初始化完成阶段

- SmartInitializingSingleton 通常在 Spring　ApplicationContext 场景使用
- preInstantiateSingletons 将已经注册的 BeanDefinition 初始化成 Spring Bean

- ApplicationContext在refresh的操作里等beanFactory的一系列操作，
messageSource，注册listener等操作都完毕之后通过finishBeanFactoryInitialization开始实例化所有非懒加载的单例bean，
具体是在finishBeanFactoryInitialization调用beanFactory#preInstantiateSingletons进行的，
preInstantiateSingletons里面就是通过beanDefinitionNames循环调用getBean来实例化bean的，
这里有个细节，beanDefinitionNames是拷贝到一个副本中，循环副本，使得还能注册新的beanDefinition.
getBean的操作就是我们之前那么多节课分析的一顿操作的过程，最终得到一个完整的状态的bean。
然后所有的非延迟单例都加载完毕之后，再重新循环副本，判断bean是否是SmartInitializingSingleton，
如果是的话执行SmartInitializingSingleton#afterSingletonsInstantiated。
这保证执行afterSingletonsInstantiated的时候的bean一定是完整的。

### Spring Bean 销毁前阶段

- DestructionAwareBeanPostProcessor#postProcessBeforeDestruction

- 销毁Bean 并不意味这个Bean 被垃圾回收了，在当前容器内被销毁

### Spring Bean 销毁阶段

### Spring Bean 生命周期

BeanFactory 的默认实现为 DefaultListableBeanFactory，其中 Bean生命周期与方法映射如下：
- BeanDefinition 注册阶段 - registerBeanDefinition
- BeanDefinition 合并阶段 - getMergedBeanDefinition
- Bean 实例化前阶段 - resolveBeforeInstantiation
- Bean 实例化阶段 - createBeanInstance
- Bean 初始化后阶段 - populateBean
- Bean 属性赋值前阶段 - populateBean
- Bean 属性赋值阶段 - populateBean
- Bean Aware 接口回调阶段 - initializeBean
- Bean 初始化前阶段 - initializeBean
- Bean 初始化阶段 - initializeBean
- Bean 初始化后阶段 - initializeBean
- Bean 初始化完成阶段 - preInstantiateSingletons
- Bean 销毁前阶段 - destroyBean
- Bean 销毁阶段 - destroyBean

### 总结

- 1.注册bean Definition  registerBeanDefinition()
- 2.bean Definition的合并阶段  getMergedLocalBeanDefinition(),比如user和superUser 最后都变为root bean Definition
- 3.创建bean createBean()
- 4.将bean类型从string变为class类型 resolveBeanClass()
- 5.bean实例化前工作resolveBeforeInstantiation(),比如可以返回自定义的bean对象让spring不在实例化bean对象
- 6.开始实例化bean doCreateBean()
- 7.实例化bean createBeanInstance()
- 8.bean实例化后 postProcessAfterInstantiation()返回false即bean不在对属性处理
- 9.属性赋值前对属性处理postProcessProperties()
- 10.属性赋值applyPropertyValues()
- 11.bean初始化阶段initializeBean()
- 12.初始化前aware接口回调(非ApplicationContextAware),比如beanFactoryAware
- 13.初始化前回调applyBeanPostProcessorsBeforeInitialization(),比如@PostConstructor
- 14.初始化invokeInitMethods(),比如实现InitializingBean接口的afterPropertiesSet()方法回调
- 15.初始化后的回调applyBeanPostProcessorsAfterInitialization()
- 16.bean重新的填充覆盖来更新bean preInstantiateSingletons()
- 17.bean销毁前postProcessBeforeDestruction()
- 18.bean销毁,比如@PreDestroy

## Spring配置元信息

### 扩展Spring xml 标签

- AbstractApplicationContext执行refresh方法，其中调用obtainFreshBeanFactory()->abstract refreshBeanFactory(),此方法被子类AbstractRefreshableApplicationContext(抽象)实现，来执行loadBeanDefinitions(beanFactory);此load方法又被AbstractXmlApplicationContext （也是抽象的，继承AbstractRefreshableConfigApplicationContext）实现。
- 接着，AbstractXmlApplicationContext的loadBeanDefinitions中会执行XmlBeanDefinitionReade对象r对loadBeanDefinitions的调用，然后是doLoadBeanDefinitions的调用执行META-INF/users-context.xml的解析。用的是XMLBeanDefinitionReader的DefaultDocumentLoader进行解析。最终解析为Document对象（是对user-context.xml的封装映射）。然后传入registerBeanDefinition方法进行注册BeanDefinition。
- 最终，会创建一个BeanDefinitionParserDelegate来传入parseBeanDefinitions(root,delegate)进行解析，然后是delegate.parseCustomElement(ele)来解析自定义元素<users:user id="" name="" citys=""/>
- 当解析到users:user标签时，会反推到命名空间：xmlns:users="http://time.geekbang.org/schema/users"
- 根据命名空间找到spring.handlers中定义的Handler实现：http\://time.geekbang.org/schema/users=org.geekbang.thinking.in.spring.configuration.metadata.UsersNamespaceHandler
- 然后利用UsersNamespaceHandler的parse方法来解析BeanDefinition并注册到spring容器中。
- 其他说明：XmlBeanDefinitionReader将META-INF/users-context.xml解析为Resource资源，然后loadBeanDefinitions。