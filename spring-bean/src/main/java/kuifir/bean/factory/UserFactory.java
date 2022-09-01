package kuifir.bean.factory;

import kuifir.ioc.overview.domain.User;

/**
 * Package: kuifir.bean.factory
 * <p>
 * Description： 工厂类
 * <p>
 * Author: baci
 * <p>
 * Date: Created in 2022/9/1 19:59
 * <p>
 * Version: 0.0.1
 */
public interface UserFactory {
    default User createUser(){
        User user = new User();
        user.setName("kuifir");
        user.setId(1L);
        return user;
    }

}
