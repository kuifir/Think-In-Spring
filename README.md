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