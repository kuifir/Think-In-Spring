package kuifir.generic;

import org.springframework.core.GenericCollectionTypeResolver;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

/**
 * Package: kuifir.generic
 * <p>
 * Description： {@link org.springframework.core.GenericCollectionTypeResolver}
 * <p>
 * Author: baci
 * <p>
 * Date: Created in 2023/1/11 15:07
 * <p>
 * Version: 0.0.1
 */
public class GenericCollectionTypeResolverDemo {
    private StringArrayList stringArrayList;
    private static List<String> strings;


    public static void main(String[] args) throws NoSuchFieldException {
        // StringList extends ArrayList<String> 具体化
        // getCollectionType 返回具体化泛型参数类型集合的成员类型 = String
        System.out.println(GenericCollectionTypeResolver.getCollectionType(StringArrayList.class));

        System.out.println(GenericCollectionTypeResolver.getCollectionType(ArrayList.class));

        Field field = GenericCollectionTypeResolverDemo.class.getDeclaredField("stringArrayList");
        System.out.println(GenericCollectionTypeResolver.getCollectionFieldType(field));

        field = GenericCollectionTypeResolverDemo.class.getDeclaredField("strings");
        System.out.println(GenericCollectionTypeResolver.getCollectionFieldType(field));
    }
}
