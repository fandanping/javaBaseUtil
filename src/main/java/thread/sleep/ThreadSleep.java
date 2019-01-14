package thread.sleep;

public class ThreadSleep  extends  Thread{
    @Override
    public void run() {
       Long startTime = System.currentTimeMillis();
       //sleep方法会抛出TnterruptedException异常 ，需要try catch捕获
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        long endTime = System.currentTimeMillis();
        System.out.println("ts线程阻塞的时间是"+(endTime-startTime)+"毫秒");
    }
    //主程序
    public static void main(String [] args){
        long startTime = System.currentTimeMillis();
        Thread th = new ThreadSleep();
        th.start();
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        long endTime = System.currentTimeMillis();
        System.out.println("主线程阻塞的时间是"+(endTime-startTime)+"毫秒");
    }
}
