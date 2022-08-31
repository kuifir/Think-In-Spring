package kuifir.ioc.overview.domain;

import kuifir.ioc.overview.annotation.Super;

/**
 * Package: kuifir.ioc.overview.domain
 * <p>
 * Description： TODO
 * <p>
 * Author: baci
 * <p>
 * Date: Created in 2022/8/21 21:57
 * <p>
 * Version: 0.0.1
 */

@Super
public class SuperUser extends User{
    private String address;

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    @Override
    public String toString() {
        return "SuperUser{" +
                "address='" + address + '\'' +
                "} " + super.toString();
    }
}
