package thread.sync.AccountByReetrantLock;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
class AccountWithLock{
    int balance;
    private Lock lock;
    public AccountWithLock() {
        balance = 0;
        lock = new ReentrantLock();
    }
}





public class ReentrantLockDemo {

}
