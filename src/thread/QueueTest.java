package thread;

import java.util.PriorityQueue;

/**
 *@auth wws
 *@date 2018年4月13日---上午10:15:32
 *
 **/
public class QueueTest {
  private int queueSize = 10;
  private PriorityQueue<Integer> queue = new PriorityQueue<Integer>(queueSize);

  public static void main(String[] args) {
      QueueTest test = new QueueTest();
      Producer producer = test.new Producer();
      Consumer consumer = test.new Consumer();

      producer.start();
      consumer.start();
  }
  
  ////////////////////////////////////////////
  class Producer extends Thread {
    @Override
    public void run() {
      while (true) {
        // wait()方法 notify()方法 必须在同步块或者同步方法中进行（synchronized块或者synchronized方法）
        synchronized (queue) {
                        // 队列满的时候 锁
          while (queue.size() == queueSize) {
            try {
              System.out.println("队列满，等待有空余空间");

                              // 调用某个对象的wait()方法能让当前线程阻塞，并且当前线程必须拥有此对象的monitor（即锁）
                              // 调用某个对象的wait()方法，相当于让当前线程交出此对象的monitor，然后进入等待状态，等待后续再次获得此对象的锁
              // （Thread类中的sleep方法使当前线程暂停执行一段时间，从而让其他线程有机会继续执行，但它并不释放对象锁）；
              queue.wait();
            } catch (InterruptedException e) {
              e.printStackTrace();
              queue.notify();
            }
          }
          queue.offer(1); // 每次插入一个元素

                        // 调用某个对象的notify()方法能够唤醒一个正在等待这个对象的monitor的线程，如果有多个线程都在等待这个对象的monitor，则只能唤醒其中一个线程；
                        // 调用notifyAll()方法能够唤醒所有正在等待这个对象的monitor的线程；
          // notify()和notifyAll()方法只是唤醒等待该对象的monitor的线程，并不决定哪个线程能够获取到monitor。
                        // 一个线程被唤醒不代表立即获取了对象的monitor，只有等调用完notify()或者notifyAll()并退出synchronized块，释放对象锁后，其余线程才可获得锁执行。
          queue.notify();
          System.out.println("向队列取中插入一个元素，队列剩余空间：" + (queueSize - queue.size()));
        }
      }
    }
  }

  // //////////////////////////////////////////
  class Consumer extends Thread {
    @Override
    public void run() {
      while (true) {
        synchronized (queue) {
                      // 队列为空时 锁
          while (queue.size() == 0) {
            try {
              System.out.println("队列空，等待数据");
              queue.wait();
            } catch (InterruptedException e) {
              e.printStackTrace();
              queue.notify();
            }
          }
          queue.poll(); // 每次移走队首元素
          queue.notify();
          System.out.println("从队列取走一个元素，队列剩余" + queue.size() + "个元素");
        }
      }
    }
  }
}
