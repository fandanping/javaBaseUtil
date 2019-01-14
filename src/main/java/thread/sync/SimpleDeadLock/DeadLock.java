package thread.sync.SimpleDeadLock;
//死锁 demo
public class DeadLock  extends  Thread{
    //得到另外一个线程对象
     DeadLock otherThread;
    @Override
    public void run() {
        sync();
        //线程结束时调用
        System.out.println("Thread finished");
    }
    // sync 为同步方法, 只有获得当前对象锁之后才能使用该方法
    public synchronized void sync(){
        //调用另外一个对象otherThread的同步方法
        otherThread.anoSync();
    }
    //a noSync也是同步方法
    public synchronized void anoSync(){
        System.out.println("成功调用");
    }
    public static void main(String [] args){
      //创建两个线程
      Thread ta = new DeadLock();
      Thread tb = new DeadLock();
      ta.otherThread = tb;
      tb.otherThread = ta;
      // 启动两个线程
      ta.start();
      tb.start();
    }
}
