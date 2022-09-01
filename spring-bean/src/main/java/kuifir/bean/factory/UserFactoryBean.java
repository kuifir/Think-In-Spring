package kuifir.bean.factory;

import kuifir.bean.definition.BeanInstantiationDemo;
import kuifir.ioc.overview.domain.User;
import org.springframework.beans.factory.FactoryBean;


/**
 * Package: kuifir.bean.factory
 * <p>
 * {@link User} Bean 的 {@link org.springframework.beans.factory.FactoryBean} 实现
 * <p>
 * Author: baci
 * <p>
 * Date: Created in 2022/9/1 20:40
 * <p>
 * Version: 0.0.1
 */
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
