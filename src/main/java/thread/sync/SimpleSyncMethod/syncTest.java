package thread.sync.SimpleSyncMethod;

public class syncTest {
    int value=0;
    public  synchronized  void add(){
        System.out.println("begining");
        try{
            this.value++;
            System.out.println(Thread.currentThread().getName()+"    "+"this.value:"+this.value);
            Thread.sleep(5000);
        }catch(Exception e){
            e.printStackTrace();
        }
        System.out.println("ending");
    }
}
