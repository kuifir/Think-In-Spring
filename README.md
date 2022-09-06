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