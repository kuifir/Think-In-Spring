package kuifir.ioc.overview.domain;

import kuifir.ioc.overview.enums.City;
import org.springframework.core.io.Resource;

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
    private City city;

    private Resource configFileLocation;

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", city=" + city +
                ", configFileLocation=" + configFileLocation +
                '}';
    }

    public Resource getConfigFileLocation() {
        return configFileLocation;
    }

    public void setConfigFileLocation(Resource configFileLocation) {
        this.configFileLocation = configFileLocation;
    }

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

    public City getCity() {
        return city;
    }

    public void setCity(City city) {
        this.city = city;
    }

    public static User createUser() {
        User user = new User();
        user.setName("kuifir");
        user.setId(1L);
        return user;
    }
}
