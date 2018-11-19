package thread;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Semaphore;

/**
 * 　　Semaphore也是一个线程同步的辅助类，可以维护当前访问自身的线程个数，并提供了同步机制。使用Semaphore可以控制同时访问资源的线程个数，例如，实现一个文件允许的并发访问数。

 Semaphore的主要方法摘要：

 　　void acquire():从此信号量获取一个许可，在提供一个许可前一直将线程阻塞，否则线程被中断。

 　　void release():释放一个许可，将其返回给信号量。

 　　int availablePermits():返回此信号量中当前可用的许可数。

 　　boolean hasQueuedThreads():查询是否有线程正在等待获取。


 */
public class MySemaphore extends Thread {
    Semaphore position;
    private int id;
    public MySemaphore(int i,Semaphore s){
        this.id=i;
        this.position=s;
    }
    public void run(){
        try{
            if(position.availablePermits()>0){
                System.out.println("顾客["+this.id+"]进入厕所，有空位");
            }
            else{
                System.out.println("顾客["+this.id+"]进入厕所，没空位，排队");
            }
            position.acquire();
            System.out.println("顾客["+this.id+"]获得坑位");
            Thread.sleep((int)(Math.random()*1000));
            System.out.println("顾客["+this.id+"]使用完毕");
            position.release();
        }
        catch(Exception e){
            e.printStackTrace();
        }
    }
    public static void main(String args[]){
        ExecutorService list=Executors.newCachedThreadPool();
        Semaphore position=new Semaphore(2);
        for(int i=0;i<10;i++){
            list.submit(new MySemaphore(i+1,position));
        }
        list.shutdown();
        position.acquireUninterruptibly(2);
        System.out.println("使用完毕，需要清扫了");
        position.release(2);
    }
}