package thread.wait;

/**
 *
 */
public class Test {
    public static Object object=new Object();
     static class Thread1 extends Thread{
         @Override
         public void run(){
             synchronized (object){
                 try {
                     object.wait();
                 } catch (InterruptedException e) {
                     e.printStackTrace();
                 }
                 System.out.println(Thread.currentThread().getName()+"获取到锁");
             }
         }
    }
    static class Thread2 extends Thread{
        @Override
        public void run(){
            synchronized (object){
                object.notify();
                System.out.println(Thread.currentThread().getName()+"释放了锁");
            }
        }
    }
    public static void main(String[] args){
         Thread1 th1=new Thread1();
         Thread2 th2=new Thread2();
         th1.start();
         th2.start();
    }
}
