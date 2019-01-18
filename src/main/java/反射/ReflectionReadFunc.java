package 反射;

import java.lang.reflect.Constructor;
import java.lang.reflect.Method;

class MyFuncClass{
    public MyFuncClass(){}
    public MyFuncClass(int i){}
    private void f1(){}
    protected int f2(int i){return 0;}
    public String f2(String s) {return "Java";}
}
public class ReflectionReadFunc {
    public static void main(String[] args){
        Class<MyFuncClass> clazz = MyFuncClass.class;
        Method[] methods = clazz.getDeclaredMethods();
        for(Method method : methods){
            System.out.println(method);
        }
        //得到所得的构造函数
        Constructor[] cl = clazz.getDeclaredConstructors();
        //输出所有的构造函数
        for (Constructor ct : cl){
           System.out.println(ct);
        }

    }
}
