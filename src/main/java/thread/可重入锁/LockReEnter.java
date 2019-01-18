package thread.可重入锁;

import java.util.concurrent.locks.ReentrantLock;

class LockReEnterDemo implements  Runnable{
    ReentrantLock lock = new ReentrantLock ();
    @Override
    public void run() {
        get();
    }
    public void get(){
        lock.lock();
        System.out.println(Thread.currentThread().getId());
        set();
        lock.unlock();
    }
    public void set(){
        lock.lock();
        System.out.println(Thread.currentThread().getId());
        lock.unlock();
    }
}
public class LockReEnter {
    public static void main(String[] args){
        Thread ta = new Thread(new LockReEnterDemo());
        Thread tb = new Thread(new LockReEnterDemo());
        ta.start();
        tb.start();
    }
}
