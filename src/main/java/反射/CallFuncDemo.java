package 反射;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

class Person{
    private String name;
    public Person(String name){
        this.name = name;
    }
    public void saySkill (String skill){
        System.out.println("Name is:"+name+",skill is"+skill);
    }
    public int addSalary(int current) {
        return current + 100;
    }
}
public class CallFuncDemo {
    public static void main(String[] args){
        Class c1azz = null;
        Constructor c = null;
        try {
            c1azz = Class.forName("Person");
            c = c1azz.getDeclaredConstructor(String.class);
            Person p = (Person)c.newInstance("Peter");
            //output: Name is:Peter, skill is:java
            p.saySkill("Java");
            // 调用方法，必须传递对象实例，同时传递参数值
            Method method1 = c1azz.getMethod("saySkill", String.class);
            //因为没返回值，所以能直接调
            //输出结果是Name is:Peter, skill is:C#
            method1.invoke(p, "C#");

            Method method2 = c1azz.getMethod("addSalary", int.class);
            Object invoke = method2.invoke(p, 100);
            //输出200
            System.out.println(invoke);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (NoSuchMethodException  e1) {
            e1.printStackTrace();
        }
        catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
    }
}
