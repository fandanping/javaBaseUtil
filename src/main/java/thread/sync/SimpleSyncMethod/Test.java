package thread.sync.SimpleSyncMethod;

public class Test implements  Runnable {
    private syncTest syncTest;
    public Test(syncTest syncTest){
        this.syncTest = syncTest;
    }
    @Override
    public void run() {
        syncTest.add();
    }
    //测试程序入口
    public static  void  main(String [] args){
        syncTest syncTest= new  syncTest();
         for(int i=0;i<2;i++){
             Thread th=new Thread(new Test(syncTest));
             th.setDaemon(false);
             th.start();
         }

    }

}
