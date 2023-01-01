package kuifir.bean.lifecycle;

import kuifir.ioc.overview.domain.User;

/**
 * Package: kuifir.bean.lifecycle
 * <p>
 * Description： TODO
 * <p>
 * Author: baci
 * <p>
 * Date: Created in 2023/1/1 23:58
 * <p>
 * Version: 0.0.1
 */
public class UserHolder {
    private User user;
    private Integer number;
    private String description;

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Integer getNumber() {
        return number;
    }

    public void setNumber(Integer number) {
        this.number = number;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public String toString() {
        return "UserHolder{" +
                "user=" + user +
                ", number=" + number +
                ", description='" + description + '\'' +
                '}';
    }
}
