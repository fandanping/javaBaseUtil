package thread;

/**
 * 单例模式懒汉式实现--解决线程安全问题实现
 */
public class Singleton {
    private Singleton() {
    }

    private static Singleton instance = null;

/*    public static thread.Singleton getInstance() {
        if (instance == null) {
            instance = new thread.Singleton();
        }
        return instance;
    }*/
    public static Singleton getInstance() {
    //如果第一个线程获取了单例的实例对象，后面的线程再获取实例的时候就不需要进入同步代码块了
        if (instance == null) {//多线程执行时，会先判断对象是否为null，如果为null，直接返回，无需等待进去同步代码块线程执行完毕，然后再去执行，提高了效率
            synchronized (Singleton.class) {//对于静态方法，使用但钱勒本身充当锁
                if (instance == null) {
                    instance = new Singleton();
                }
            }
        }
        return instance;
    }

    public static void main(String[] args) {
        Singleton s1 = Singleton.getInstance();
        Singleton s2 = Singleton.getInstance();
        System.out.println(s1 == s2);
    }
}
