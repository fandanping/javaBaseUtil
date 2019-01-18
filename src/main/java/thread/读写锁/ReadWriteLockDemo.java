package thread.读写锁;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

class ReadWriteTool{
    ReentrantReadWriteLock lock = new ReentrantReadWriteLock();
    Lock  readLock = lock.readLock();
    Lock writeLock = lock.writeLock();
    private int num;
    public void read(){
        int cnt = 0;
        while(cnt++ <3){
            readLock.lock();
            System.out.println(Thread.currentThread().getId()+"start to read");
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println(Thread.currentThread().getId()+"reading"+num);
            readLock.unlock();
        }
    }
    public void write(){
        int cnt = 0;
        while(cnt++ <3){
            writeLock.lock();
            System.out.println(Thread.currentThread().getId()+"start to write");
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            num = (int)(Math.random() *10);
            System.out.println(Thread.currentThread().getId()+"writeing"+num);
            writeLock.unlock();
        }
    }
}
class ReadThread extends Thread{
    private ReadWriteTool readWriteTool;
    public ReadThread(ReadWriteTool readWriteTool){
        this.readWriteTool = readWriteTool;
    }
    @Override
    public void run() {
        readWriteTool.read();
    }
}
class WriteThread extends  Thread{
    private ReadWriteTool readWriteTool;
    public WriteThread(ReadWriteTool readWriteTool){
        this.readWriteTool = readWriteTool;
    }
    @Override
    public void run() {
        readWriteTool.write();
    }
}
public class ReadWriteLockDemo {
    public static void main(String[] args){
        ReadWriteTool tool = new ReadWriteTool();
        for(int i=0;i<3;i++){
            new ReadThread(tool).start();
            new WriteThread(tool).start();
        }
    }
}
