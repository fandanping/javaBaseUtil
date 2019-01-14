package thread.sync.SimpleDeadLock;
//死锁 demo
class T  extends  Thread{
    //得到另外一个线程对象
     T t;
    @Override
    public void run() {
        System.out.println(Thread.currentThread().getName()+"启动");
        sync();
        //线程结束时调用
        System.out.println(Thread.currentThread().getName()+"finished");
    }
    // sync 为同步方法, 只有获得当前对象锁之后才能使用该方法
    public synchronized void sync(){
        //必须要加上sleep ，要不看不到死锁效果。因为线程1可能很快就执行完了，然后线程2还没执行,互不影响
        try{
            sleep(2000);
        }
        catch (InterruptedException e){
            e.printStackTrace();
        }
        //调用另外一个对象otherThread的同步方法
        t.anoSync();
    }
    //a noSync也是同步方法
    public synchronized void anoSync(){
        System.out.println(Thread.currentThread().getName()+"成功调用另一个线程");
    }
}

 public class DeadLock{
     public static void main(String [] args){
         //创建两个线程
         T ta = new T();
         T tb = new T();
         ta.t = tb;
         tb.t = ta;
         // 启动两个线程
         ta.start();
         tb.start();
     }
 }
