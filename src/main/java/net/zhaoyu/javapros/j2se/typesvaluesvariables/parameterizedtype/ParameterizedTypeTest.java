package net.zhaoyu.javapros.j2se.typesvaluesvariables.parameterizedtype;

import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

/**
 * ParameterizedType表示参数化的类型，语法为C<T1,T2...>,如Collection<String>,Comparator<T>。
 * 参数化类型拥有类型引元（Type Arguments）列表<T1,T2...>，这些类型引元有引用类型和通配符两种。
 */
public class ParameterizedTypeTest {
    public static void main(String[] args) throws NoSuchMethodException {
        Method method = BoxHolder.class.getMethod("getT", Box.class);
        printParameterTypeOfMethodReturn(method.getGenericReturnType());
        Method method1 = BoxHolder.class.getMethod("getString", null);
        printParameterTypeOfMethodReturn(method1.getGenericReturnType());
    }

    //方法返回值如果为参数化类型，打印其名称。
    private static void printParameterTypeOfMethodReturn(Type returnType) throws NoSuchMethodException {

        if (returnType instanceof ParameterizedType) {
            ParameterizedType type= (ParameterizedType) returnType;
            Type[] typeArguments = type.getActualTypeArguments();
            for (Type typeArgument : typeArguments) {
//                Class typeArgClass = (Class) typeArgument;
                System.out.println("typeArgClass = " + typeArgument.getTypeName());
            }
        }
    }
}

class Box<T> {
    T t;

    public void setT(T t) {
        this.t = t;
    }

    public T getT() {
        return t;
    }
}

class BoxHolder {
    public Box<String> getString(){  //Box<String>的参数为String的引用类型,作为其Type Arguments
        return null;
    };

    public <S> Box<S> getT(Box<S> boxt){ //Box<S> 也是参数化类型。
        return boxt;
    }

}