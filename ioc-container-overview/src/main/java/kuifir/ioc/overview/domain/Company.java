package kuifir.ioc.overview.domain;

/**
 * Package: kuifir.ioc.overview.domain
 * <p>
 * Description： 公司
 * <p>
 * Author: baci
 * <p>
 * Date: Created in 2023/1/8 0:28
 * <p>
 * Version: 0.0.1
 */
public class Company {
    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "Company{" +
                "name='" + name + '\'' +
                '}';
    }
}
