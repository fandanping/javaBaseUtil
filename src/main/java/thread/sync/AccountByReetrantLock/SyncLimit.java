package thread.sync.AccountByReetrantLock;

class Account{
    int balance;
    public Account(){
        this.balance = 0;
    }
    public synchronized  void login(){

    }
    public synchronized void logout(){

    }
    public synchronized  void add(){
        this.balance += 800;
        System.out.println("After balance is"+ this.balance);
    }
    public synchronized  void minus(){
        this.balance -= 800;
        System.out.println("After balance is"+ this.balance);
    }

}
class AddThread extends  Thread{
    Account accout;
    String person;
    public AddThread(Account accout,String person){
        this.accout = accout;
        this.person = person;
    }
    @Override
    public void run() {
        for (int i = 0; i < 3; i++) {
            System.out.println(person + " add money," +  i  + " cnt");
            accout.login();
            System.out.println(person + " login ");
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            accout.add();;
            System.out.println(person + " logout ");
            accout.logout();
        }
    }
}
class MinusThread extends  Thread{
    Account accout;
    String person;
    public MinusThread(Account accout,String person){
        this.accout = accout;
        this.person = person;
    }
    @Override
    public void run() {
        for (int i = 0; i < 3; i++) {
            System.out.println(person + " minus money," + i + " cnt");
            System.out.println(person + " login ");
            accout.login();
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            accout.minus();;
            System.out.println(person + " logout ");
            accout.logout();
        }
    }
}
public class SyncLimit {
    public static void main(String [] args){
        Account account = new Account();
        Thread addThread =new AddThread(account,"Tom");
        Thread minusThread =new MinusThread(account,"San");
        addThread.start();
        minusThread.start();
    }
}
