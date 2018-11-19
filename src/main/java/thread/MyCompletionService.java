package thread;

import java.util.concurrent.*;

/**
 *
 * !!! CompletionService用于提交一组Callable任务，其take方法返回已完成的一个Callable任务对应的Future对象。
 * CompletionService的作用是将线程池中所有线程的执行结果future放入blockQueen队列，take可以取出结果。
 应用场景例子：对于在执行某种业务逻辑之前，需要执行1，2，3，4等步骤，这些步骤校验完成，才能正常执行业务逻辑，而且这些步骤之前没有关联性，就可以应用CompletionService。
 */
public class MyCompletionService implements Callable<String>{
    private int id;
    public MyCompletionService(int i){
        this.id=i;
    }
    public static void main(String[] args) throws Exception{
        ExecutorService service=Executors.newCachedThreadPool();
        CompletionService<String> completion=new ExecutorCompletionService<String>(service);
        for(int i=0;i<10;i++){
            completion.submit(new MyCompletionService(i));
        }
        for(int i=0;i<10;i++){
            System.out.println(completion.take().get());
        }
        service.shutdown();
    }
    @Override
    public String call() throws Exception {
        Integer time=(int) (Math.random()*1000);
        System.out.println(this.id+" start");
        Thread.sleep(time);
        System.out.println(this.id+" end");
        return this.id+":"+time;
    }
}
