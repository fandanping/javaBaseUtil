package thread;

public class SimpleThread  extends Thread{
    // 线程编号
    private int index;
    // 通过构造函数指定该线程的编号
    public SimpleThread (int index){
        this.index = index;
        System.out.println("第"+this.index+"个线程开始创建");
    }
    // run方法, 当调用线程的start方法时会自动调用该方法, 此时线程进入可执行状态
    @Override
    public void run() {
        //循环打印当前次数
        for(int j=0;j<3;j++){
             System.out.println("第"+this.index+"个线程执行"+j+"次");
        }
        //当前线程运行结束
       System.out.println("第"+index+"个线程结束");
    }

    // 用main方法作为测试方法
    public static void main(String[] args){
        for(int i=0;i<3;i++){
            // 实例化该类型的对象, 并直接调用start方法直接把线程拉起
            //这个方法会自动调用run方法,
            Thread th = new SimpleThread(i);
            th.start();
        }
    }
}
