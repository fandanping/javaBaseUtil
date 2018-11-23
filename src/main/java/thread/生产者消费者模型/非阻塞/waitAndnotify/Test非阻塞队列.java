package thread.生产者消费者模型.非阻塞.waitAndnotify;

import java.util.PriorityQueue;

/**
 *
 */
public class Test非阻塞队列 {
     private static  int queueSize = 10;
     private static PriorityQueue<Integer> queue=new PriorityQueue<Integer>(queueSize);

     public static void main(String[] args){
         Test非阻塞队列 test=new Test非阻塞队列();
         Producer producer=new Producer();
         Consumer consumer=new Consumer();
         producer.start();
         consumer.start();

     }
    static class Producer extends  Thread{
        @Override
        public void run (){
             produce();
        }
        private void produce(){
            while(true){
                 synchronized (queue){
                     while(queue.size() == queueSize){
                         try {
                             System.out.println("队列满，等待有空余空间");
                             queue.wait();
                         } catch (InterruptedException e) {
                             e.printStackTrace();
                             queue.notify();
                         }
                     }
                     //每次插入一个元素
                     queue.offer(1);
                     queue.notify();
                     System.out.println("向队列取中插入一个元素，队列剩余空间："+(queueSize-queue.size()));
                 }
            }
        }
    }
     static class Consumer extends  Thread{
         @Override
         public void run (){
             consume();
         }
         private void consume(){
              while(true){
                   synchronized (queue){
                       while(queue.size()==0){
                           try {
                               System.out.println("队列空，等待数据");
                               queue.wait();
                           } catch (InterruptedException e) {
                               e.printStackTrace();
                               queue.notify();
                           }
                       }
                       queue.poll();
                       queue.notify();
                       System.out.println("从队列取走一个元素，队列剩余"+queue.size()+"个元素");
                   }
              }
         }
     }
}
