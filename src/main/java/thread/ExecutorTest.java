package thread;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
/**
 *  创建一个可根据需要创建新线程的线程池，而且在以前构造的线程可用时将重用它们。
 */
public class ExecutorTest {
    static class WorkerRunnable  implements Runnable{
        private final CountDownLatch doneSignal;
        private  final int i;
        WorkerRunnable (CountDownLatch doneSignal,int i){
            this.doneSignal=doneSignal;
            this.i=i;
        }
        @Override
        public void run() {
            dowork(i);
            //System.out.println(Thread.currentThread().getName());
            //任务执行完毕递减锁存器的计数
            doneSignal.countDown();
        }
        void dowork(int i ){
            System.out.println(Thread.currentThread().getName()+"这是第"+(i+1)+"个任务");
        }
    }
    public static void main(String[] args){
          //线程数
         int num=20;
         //CountDownLatch是一个同步辅助类也可以使用AtomicInteger替代
         CountDownLatch doneSignal=new CountDownLatch(100);
        ExecutorService pool= Executors.newCachedThreadPool();
        for(int i=0;i<num;i++){
            WorkerRunnable wr=new WorkerRunnable(doneSignal,i);
            pool.execute(wr);
        }
    /*    try {
            doneSignal.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }*/
        pool.shutdown();
        System.out.println("所有任务都执行完毕");
    }
}
