package thread;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;

/**
 * 　　1.使用Callable+Future获取执行结果
 */
public class FutureTest implements Callable<Integer>{
     private int index;
     public FutureTest(int i){
         this.index=i;
     }
     @Override
     public Integer call(){
         try {
             System.out.println(Thread.currentThread().getName()  + "正在执行… …" +index);
             System.out.println(Thread.currentThread().getName()  + "end… …"+index );
         } catch (Exception e) {
             e.printStackTrace();
         }
         return  1;
     }

     public static void main(String[] args) throws ExecutionException, InterruptedException {
         ExecutorService service= Executors.newFixedThreadPool(2);
         List<Future<Integer>> futureList = new ArrayList<>();
         for(int i=0;i<5;i++){
             FutureTest task=new FutureTest(i);
             //将线程放入池中进行执行
             futureList.add(service.submit(task));
         }
         System.out.println("分组完成，共:"+futureList.size());
         for (Future future : futureList) {
             System.out.println("task运行结果"+future.get());
         }
         //关闭线程池
         service.shutdown();
         System.out.println("submit finish");
         while(!service.isTerminated()){
             //System.out.println("all is un finished");
         }
         System.out.println("all is finished");
     }

}
