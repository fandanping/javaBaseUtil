package thread;

/**
 * ThreadLocal为变量在每个线程中都创建了一个副本，那么每个线程可以访问自己内部的副本变量。
 * 最常见的ThreadLocal使用场景为 用来解决 数据库连接、Session管理等。
 */
public class ThreadLocalTest {
    ThreadLocal<Long> longLocal =new ThreadLocal<Long>();
    ThreadLocal<String> stringLocal=new ThreadLocal<String>();
    public void  set(){
        longLocal.set(Thread.currentThread().getId());
        stringLocal.set(Thread.currentThread().getName());
    }
    public long getLong(){
        return  longLocal.get();
    }
    public String getString(){
        return stringLocal.get();
    }


    public static void main(String[] args){
        final ThreadLocalTest test=new ThreadLocalTest();
        test.set();
        System.out.println(test.getLong());
        System.out.println(test.getString());
        Thread th=new Thread(){
            public void run(){
                test.set();
                System.out.println(test.getLong());
                System.out.println(test.getString());
            }
        };
        th.start();
        try {
            th.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println(test.getLong());
        System.out.println(test.getString());
    }

}
