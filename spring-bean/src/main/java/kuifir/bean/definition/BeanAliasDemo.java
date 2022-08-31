package kuifir.bean.definition;

import kuifir.ioc.overview.domain.User;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * Package: kuifir.bean.definition
 * <p>
 * Description： Bean别名示例
 * <p>
 * Author: baci
 * <p>
 * Date: Created in 2022/8/26 23:06
 * <p>
 * Version: 0.0.1
 */
public class BeanAliasDemo {
    public static void main(String[] args) {
        BeanFactory beanFactory = new ClassPathXmlApplicationContext("classpath:\\META-INF\\bean-definitions-context.xml");
        User user = beanFactory.getBean("user",User.class);
//        通过别名kuifir-user获取user的Bean
        User kuifirUser = beanFactory.getBean("kuifir-user",User.class);
        // 判断是否为一个对象
        System.out.println("user 与 kuifir-user 是否相同" + (user == kuifirUser));

    }
}
