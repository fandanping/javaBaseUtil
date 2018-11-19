package thread.join;

public class JoinTest {
     public static void main(String[] args){
         Thread t=new Thread(new ThreadImpll());
         t.start();
         try {
             t.join();
            // t.thread.join(2000);
             if(t.isAlive()){
                 System.out.println("t has not finished");
             }else{
                 System.out.println("t has finished");
             }
         } catch (InterruptedException e) {
             e.printStackTrace();
         }
     }
}
