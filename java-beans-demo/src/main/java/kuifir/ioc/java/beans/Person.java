package kuifir.ioc.java.beans;

/**
 * Package: kuifir.ioc.java.beans
 * <p>
 * Description：
 * Getter / Setter 方法
 * 在Java Beans 中
 * 可写方法(Writable) / 可读方法（Readable）
 * 字段 property
 * <p>
 * Author: baci
 * <p>
 * Date: Created in 2022/8/20 21:20
 * <p>
 * Version: 0.0.1
 */
public class Person {
    String name;
    Integer age;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }
}
