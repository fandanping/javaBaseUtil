/**
 * 线程通信
 * 使用两个线程打印1-100，线程1，线程2，交替打印
 */
class PrintNum implements Runnable {
    int num = 1;
    @Override
    public void run() {
        while (true) {
            synchronized (this){
                notify();
                if (num <= 100) {
                    try {
                        Thread.currentThread().sleep(10);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    System.out.println(Thread.currentThread().getName() + ":" + num);
                    num++;
                } else {
                    break;
                }

                try {
                    wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }
    public static void main(String[] args) {
        PrintNum p = new PrintNum();
        Thread t1 = new Thread(p);
        Thread t2 = new Thread(p);
        t1.setName("甲");
        t2.setName("乙");
        t1.start();
        t2.start();
    }
}


