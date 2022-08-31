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
  
  4. 通过 FactoryBean（配置元信息：XML、Java 注解和 Java API ）
#### 特殊方式
   1. 通过 ServiceLoaderFactoryBean（配置元信息：XML、Java 注解和 Java API ）
   2. 通过 AutowireCapableBeanFactory#createBean(java.lang.Class, int, boolean)
   3. 通过 BeanDefinitionRegistry#registerBeanDefinition(String,BeanDefinition)
 