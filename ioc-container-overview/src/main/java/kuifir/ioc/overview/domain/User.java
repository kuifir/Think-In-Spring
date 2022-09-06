package kuifir.ioc.overview.domain;

/**
 * Package: kuifir.ioc.overview.domain
 * <p>
 * Description： 用户类
 * <p>
 * Author: baci
 * <p>
 * Date: Created in 2022/8/21 21:20
 * <p>
 * Version: 0.0.1
 */
public class User {
    private Long id;
    private String name;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }
    public static User createUser() {
        User user = new User();
        user.setName("kuifir");
        user.setId(1L);
        return user;
    }
}
