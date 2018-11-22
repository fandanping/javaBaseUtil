package thread;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
/**
 * http://www.cnblogs.com/dolphin0520/p/3949310.html
 * 线程池的优点：并发量很多时，频繁创建线程和销毁线程浪费时间和降低系统效率。因此采用多线程
 */

/**
 * 创建一个可重用固定线程数的线程池，以共享的无界队列方式来运行这些线程。
 * Executors.newFixedThreadPool()
 * newFixedThreadPool 创建一个定长线程池，可控制线程最大并发数，超出的线程会在队列中等待。
 */
public class MyExecutor  extends Thread{
     private int index;
     public MyExecutor(int i){
         this.index=i;
     }
     public void run(){
         try {
             System.out.println(Thread.currentThread().getName()  + "正在执行… …" +index);
            // Thread.sleep((int)(Math.random()*10000));
             System.out.println(Thread.currentThread().getName()  + "end… …"+index );
         } catch (Exception e) {
             e.printStackTrace();
         }
     }

    /**
     * 创建了包含2个工作线程的固定大小线程池，然后我们向线程池提交5个任务；
     * 因此首先会启动5个工作线程，其他任务将进行等待。一旦有任务结束，工作线程会从等待队列中挑选下一个任务并开始执行。
     * @param args
     * @throws ExecutionException
     * @throws InterruptedException
     */
     public static void main(String[] args) throws ExecutionException, InterruptedException {
         ExecutorService service= Executors.newFixedThreadPool(2);
         List<Future> futureList = new ArrayList<>();
         for(int i=0;i<5;i++){
             MyExecutor task=new MyExecutor(i);
             //将线程放入池中进行执行
             futureList.add(service.submit(task));
         }
         System.out.println("分组完成，共:"+futureList.size());
         for (Future future : futureList) {
             future.get();
         }
         //关闭线程池
         service.shutdown();
         System.out.println("submit finish");
         while(!service.isTerminated()){
             System.out.println("all is un finished");
         }
         System.out.println("all is finished");
     }

}
