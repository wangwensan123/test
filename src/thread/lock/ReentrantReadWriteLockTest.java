package thread.lock;

import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 *@auth wws
 *@date 2018年4月24日---上午10:46:35
 *
 **/
public class ReentrantReadWriteLockTest {
  private ReadWriteLock rwl = new ReentrantReadWriteLock();
   
  public static void main(String[] args)  {
      final ReentrantReadWriteLockTest test = new ReentrantReadWriteLockTest();
       
      new Thread(){
          public void run() {
              test.get(Thread.currentThread());
          };
      }.start();
       
      new Thread(){
          public void run() {
              test.get(Thread.currentThread());
          };
      }.start();
       
  }  
   
  public void get(Thread thread) {
      rwl.readLock().lock();
      try {
          long start = System.currentTimeMillis();
           
          while(System.currentTimeMillis() - start <= 1) {
              System.out.println(thread.getName()+"正在进行读操作");
          }
          System.out.println(thread.getName()+"读操作完毕");
      } finally {
          rwl.readLock().unlock();
      }
  }
}
