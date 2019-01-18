package 反射;
class MyClass{
    public void print() {
        System.out.println("java");
    }
}
public class ForClassDemo {
    public static void main(String[] args){
        MyClass myClassObj = new MyClass();
        myClassObj.print();

        try {
            Class<?> clazz = Class.forName("反射.MyClass");
            MyClass myClass = (MyClass) clazz.newInstance();
            myClass.print();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        }


    }
}
