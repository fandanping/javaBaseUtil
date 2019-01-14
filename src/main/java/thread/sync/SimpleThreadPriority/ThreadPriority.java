package thread.sync.SimpleThreadPriority;

public class ThreadPriority implements  Runnable{
    // 线程编号
    int number;
    public ThreadPriority(int num) {
        number = num;
        System.out.println("Create Thread[" + number + "]");
    }
    // run方法，当调用线程的start方法时会调用该方法
    public void run() {
        for (int i = 0; i <= 3; i++) {
            System.out.println("Thread[" + number + "]:Count " + i);
        }
    }
    // 用main方法做为测试方法
    public static void main(String args[]) {
        // 定义线程t1，并设置其优先级为5
        Thread t1 = new Thread(new ThreadPriority(1));
        t1.setPriority(1);
        // 定义线程t2，并设置其优先级为7
        Thread t2 = new Thread(new ThreadPriority(2));
        t2.setPriority(7);
        // 启动这两个线程
        t1.start();
        t2.start();
    }
}
