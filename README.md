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
   2. 通过 AutowireCapableBeanFactory#createBean(java.lang.Class, int, boolean)
   3. 通过 BeanDefinitionRegistry#registerBeanDefinition(String,BeanDefinition)
 