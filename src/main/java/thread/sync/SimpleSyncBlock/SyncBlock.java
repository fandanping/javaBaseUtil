package thread.sync.SimpleSyncBlock;

public class SyncBlock implements  Runnable{

    @Override
    public void run() {
        //锁住当前对象
        synchronized (this){
           for (int i=0;i<3;i++) {
                System.out.println(Thread.currentThread().getName()+"正在打印第"+i+"次");
           }
        }
    }
    //测试程序
    public static void main(String [] args){
        SyncBlock t1 = new SyncBlock();
        Thread ta = new Thread(t1, "A");
        Thread tb = new Thread(t1, "B");
        ta.start();;
        tb.start();
    }
}
