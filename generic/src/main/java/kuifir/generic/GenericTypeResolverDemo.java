package kuifir.generic;

import com.sun.org.apache.xerces.internal.xs.StringList;
import org.springframework.core.GenericTypeResolver;

import java.lang.reflect.Method;
import java.lang.reflect.Type;
import java.lang.reflect.TypeVariable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Package: kuifir.generc
 * <p>
 * Description： {@link org.springframework.core.GenericTypeResolver}
 * <p>
 * Author: baci
 * <p>
 * Date: Created in 2023/1/11 14:19
 * <p>
 * Version: 0.0.1
 */
public class GenericTypeResolverDemo {
    public static void main(String[] args) throws NoSuchMethodException {

        // String 是 Comparable<String> 具体化
        displayReturnTypeGenericInfo(GenericTypeResolverDemo.class,"getString",Comparable.class);

        // ArrayList<Object> 是 List 泛型参数类型的具体化
        displayReturnTypeGenericInfo(GenericTypeResolverDemo.class,"getList", List.class);

        // StringList 也是 List 泛型参数类型的具体化
        displayReturnTypeGenericInfo(GenericTypeResolverDemo.class,"getStringList",List.class);

        // 具备 ParameterizedType 返回，否则 null

        // TypeVariable
        Map<TypeVariable, Type> typeVariableMap = GenericTypeResolver.getTypeVariableMap(StringList.class);
        System.out.println(typeVariableMap);
    }

    private static void displayReturnTypeGenericInfo(Class<?> containClass,String methodName,  Class<?> genericIfc ) throws NoSuchMethodException {
        Method method = containClass.getMethod(methodName);
//        Type genericReturnType = method.getGenericReturnType();
        // 声明类 -> GenericTypeResolverDemo.class
        Class<?> returnType = GenericTypeResolver.resolveReturnType(method, containClass);
        System.out.println(returnType);

        Class<?> returnTypeArgument = GenericTypeResolver.resolveReturnTypeArgument(method, genericIfc);
        System.out.println(returnTypeArgument);
    }


    public StringArrayList getStringList(){
        return null;
    }

    public ArrayList<Object> getList(){
        return null;
    }

    public String getString(){
        return null;
    }

}
