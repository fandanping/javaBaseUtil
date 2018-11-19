package thread.syncTest;

public class Test implements  Runnable {
    public Test(syncTest syncTest){
        this.syncTest = syncTest;
    }
    private syncTest syncTest;
    @Override
    public void run() {
        syncTest.add();
        //thread.syncTest.generalMethod();
    }
    public static  void  main(String [] args){
        syncTest syncTest= new  syncTest();
         for(int i=0;i<1;i++){
             Thread th=new Thread(new Test(syncTest));
             th.setDaemon(false);
             th.start();
         }

    }

}
