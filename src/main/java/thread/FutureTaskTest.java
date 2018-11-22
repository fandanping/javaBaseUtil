package thread;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;

public class FutureTaskTest   implements Callable<Integer> {
    private int index;
    public FutureTaskTest(int i){
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
        // 创建线程池
        ExecutorService service= Executors.newFixedThreadPool(2);
        // 创建任务集合
        List<FutureTask<Integer>> futureList = new ArrayList<>();
        for(int i=0;i<5;i++){
            // 传入Callable对象创建FutureTask对象
            FutureTest task=new FutureTest(i);
            FutureTask<Integer> futureTask = new FutureTask<Integer>(task);
            //将线程放入池中进行执行
            futureList.add(futureTask);
            // 提交给线程池执行任务，也可以通过exec.invokeAll(taskList)一次性提交所有任务;
            service.submit(futureTask);
        }
        System.out.println("分组完成，共:"+futureList.size());
        for (Future future : futureList) {
             System.out.println("task运行结果"+future.get());
           // System.out.println("task运行结果"+future.cancel(true));
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
