package kuifir.ioc.overview.domain;

import kuifir.ioc.overview.enums.City;
import org.springframework.beans.factory.BeanNameAware;
import org.springframework.core.io.Resource;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import java.util.Arrays;
import java.util.List;

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
public class User implements BeanNameAware {
    private Long id;
    private String name;
    private City city;
    private City[] workCities;
    private List<City> lifeCities;

    private Resource configFileLocation;

    // 当前bean的名称
    private transient String beanName;

    public City[] getWorkCities() {
        return workCities;
    }

    public List<City> getLifeCities() {
        return lifeCities;
    }

    public void setLifeCities(List<City> lifeCities) {
        this.lifeCities = lifeCities;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", city=" + city +
                ", workCities=" + Arrays.toString(workCities) +
                ", lifeCities=" + lifeCities +
                ", configFileLocation=" + configFileLocation +
                '}';
    }

    public void setWorkCities(City[] workCities) {
        this.workCities = workCities;
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

    @PostConstruct
    public void init() {
        System.out.println("User Bean["+ beanName+"] 初始化...");
    }

    @PreDestroy
    public void destory(){
        System.out.println("User Bean["+ beanName+"] 销毁");
    }

    @Override
    public void setBeanName(String name) {
        this.beanName = name;
    }
}
