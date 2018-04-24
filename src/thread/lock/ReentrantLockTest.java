package thread.lock;

import java.util.ArrayList;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 *总结来说，Lock和synchronized有以下几点不同：
　　1）Lock是一个接口，而synchronized是Java中的关键字，synchronized是内置的语言实现；
　　2）synchronized在发生异常时，会自动释放线程占有的锁，因此不会导致死锁现象发生；而Lock在发生异常时，
            如果没有主动通过unLock()去释放锁，则很可能造成死锁现象，因此使用Lock时需要在finally块中释放锁；
　　3）Lock可以让等待锁的线程响应中断，而synchronized却不行，使用synchronized时，等待的线程会一直等待下去，不能够响应中断；
　 4）通过Lock可以知道有没有成功获取锁，而synchronized却无法办到。
　　5）Lock可以提高多个线程进行读操作的效率。
　　在性能上来说，如果竞争资源不激烈，两者的性能是差不多的，而当竞争资源非常激烈时（即有大量线程同时竞争），
        此时Lock的性能要远远优于synchronized。所以说，在具体使用时要根据适当情况选择。
 **/
public class ReentrantLockTest {
  private ArrayList<Integer> arrayList = new ArrayList<Integer>();
  private Lock lock = new ReentrantLock();    //注意这个地方
  public static void main(String[] args)  {
      final ReentrantLockTest test = new ReentrantLockTest();
       
      new Thread(){
          public void run() {
              test.insert(Thread.currentThread());
          };
      }.start();
       
      new Thread(){
          public void run() {
              test.insert(Thread.currentThread());
          };
      }.start();
  }  
   
  public void insert(Thread thread) {
      if(lock.tryLock()) {
          try {
              System.out.println(thread.getName()+"得到了锁");
              for(int i=0;i<5;i++) {
                  arrayList.add(i);
              }
          } catch (Exception e) {
              // TODO: handle exception
          }finally {
              System.out.println(thread.getName()+"释放了锁");
              lock.unlock();
          }
      } else {
          System.out.println(thread.getName()+"获取锁失败");
      }
  }
}
