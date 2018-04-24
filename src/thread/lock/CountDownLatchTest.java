package thread.lock;

import java.util.concurrent.CountDownLatch;

/**
 *@auth wws
 *@date 2018年4月24日---下午5:36:52
 *利用它可以实现类似计数器的功能。比如有一个任务A，它要等待其他4个任务执行完毕之后才能执行，
 *此时就可以利用CountDownLatch来实现这种功能了。
 **/
public class CountDownLatchTest {
  public static void main(String[] args) {   
    final CountDownLatch latch = new CountDownLatch(2);//参数count为计数值

    new Thread(){
        public void run() {
            try {
                System.out.println("子线程"+Thread.currentThread().getName()+"正在执行");
               Thread.sleep(3000);
               System.out.println("子线程"+Thread.currentThread().getName()+"执行完毕");
               latch.countDown();//将count值减1
           } catch (InterruptedException e) {
               e.printStackTrace();
           }
        };
    }.start();

    new Thread(){
        public void run() {
            try {
                System.out.println("子线程"+Thread.currentThread().getName()+"正在执行");
                Thread.sleep(3000);
                System.out.println("子线程"+Thread.currentThread().getName()+"执行完毕");
                latch.countDown();//将count值减1
           } catch (InterruptedException e) {
               e.printStackTrace();
           }
        };
    }.start();

    try {
        System.out.println("等待2个子线程执行完毕...");
       latch.await();//调用await()方法的线程会被挂起，它会等待直到count值为0才继续执行
       System.out.println("2个子线程已经执行完毕");
       System.out.println("继续执行主线程");
   } catch (InterruptedException e) {
       e.printStackTrace();
   }
}
}
