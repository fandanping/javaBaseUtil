package thread.syncTest;

public class syncTest {
    int value=0;
    public   void add(){
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
    public synchronized  static void  generalMethod(){
        System.out.println("111");
    }
}
