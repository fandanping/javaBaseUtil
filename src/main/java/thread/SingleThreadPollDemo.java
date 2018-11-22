package thread;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class SingleThreadPollDemo {
    static class SingleTask implements Runnable{

        @Override
        public void run() {
            System.out.println(Thread.currentThread().getName()+"正在执行.....");
            try {
                Thread.sleep(3000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println(Thread.currentThread().getName() + "执行完毕");
        }
    }
    public static void main(String [] args){
        //创建一个使用单个worker线程的executor,以无界队列方式来运行该线程
        ExecutorService pool= Executors.newSingleThreadExecutor();
        for(int i=0;i<3;i++){
                  Runnable task1 = new SingleTask();
                  pool.execute(task1);
        }
        pool.shutdown();
    }
}
