package thread.可重入锁;
class SyncReEnterDemo  implements Runnable{
    @Override
    public void run() {
        get();
    }
    public synchronized  void get(){
        System.out.println(Thread.currentThread().getId());
        set();
    }
    public synchronized  void set(){
        System.out.println(Thread.currentThread().getId());
    }
}
public class SyncReEnter{
    public static void main(String[] args){
        Thread ta = new Thread(new SyncReEnterDemo());
        Thread tb = new Thread(new SyncReEnterDemo());
        ta.start();
        tb.start();
    }
}
