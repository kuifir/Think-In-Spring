package kuifir.generic;

import java.util.ArrayList;
import java.util.Collection;

/**
 * Package: kuifir.generic
 * <p>
 * Description： java 泛型示例
 * <p>
 * Author: baci
 * <p>
 * Date: Created in 2023/1/11 13:05
 * <p>
 * Version: 0.0.1
 */
public class GenericDemo {
    public static void main(String[] args) {
        Collection<String> collection = new ArrayList<>();
        collection.add("hello");
        collection.add("world");
        // 编译时错误
//        collection.add(1);
        // 泛型擦除
        Collection collection1 = collection;
        // 编译成功
        collection1.add(1);

        System.out.println(collection);
        // 取值成功
        System.out.println(((ArrayList)collection1).get(2));
        // 取值报错
        System.out.println(((ArrayList<String>) collection).get(2));

    }
}
