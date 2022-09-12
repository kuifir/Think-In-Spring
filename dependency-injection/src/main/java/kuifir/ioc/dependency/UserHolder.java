package kuifir.ioc.dependency;

import kuifir.ioc.overview.domain.User;

/**
 * Package: kuifir.ioc.dependency
 * <p>
 * Description： {@link User}的Holder类
 * <p>
 * Author: baci
 * <p>
 * Date: Created in 2022/9/12 22:16
 * <p>
 * Version: 0.0.1
 */
public class UserHolder {

    private User user;

    public UserHolder() {
    }

    public UserHolder(User user) {
        this.user = user;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    @Override
    public String toString() {
        return "UserHolder{" +
                "user=" + user +
                '}';
    }
}
