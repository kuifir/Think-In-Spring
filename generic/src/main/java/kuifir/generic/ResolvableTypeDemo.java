package kuifir.generic;

import org.springframework.core.ResolvableType;

/**
 * Package: kuifir.generic
 * <p>
 * Description： {@link org.springframework.core.ResolvableType}
 * <p>
 * Author: baci
 * <p>
 * Date: Created in 2023/1/11 16:31
 * <p>
 * Version: 0.0.1
 */
public class ResolvableTypeDemo {
    public static void main(String[] args) {
        ResolvableType resolvableType = ResolvableType.forClass(StringArrayList.class);
        resolvableType.getSuperType();

        System.out.println(resolvableType.asCollection().resolve());
        System.out.println(resolvableType.asCollection().resolveGeneric(0));
    }
}
