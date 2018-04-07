package thread;
/**
 *@auth wws
 *@date 2018年3月28日---上午11:15:17
 *CyclicBarrier 的字面意思是可循环（Cyclic）使用的屏障（Barrier）。
 *它要做的事情是，让一组线程到达一个屏障（也可以叫同步点）时被阻塞，直到最后一个线程到达屏障时，屏障才会开门，
 *所有被屏障拦截的线程才会继续干活。线程进入屏障通过CyclicBarrier的await()方法
 *
 *四个线程a、b、c、d，这四个线程提交给了线程池。四个线程不同时间到达cb.await()语句，
 *当四个线程都输出“Thread x will wait”以后才会输出“Thread x is over”。
 **/
import java.util.Random;
import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
 
public class CyclicBarrierDemo {
    private CyclicBarrier cb = new CyclicBarrier(4);
    private Random rnd = new Random();
     
    class TaskDemo implements Runnable{
        private String id;
        TaskDemo(String id){
            this.id = id;
        }
        @Override
        public void run(){
            try {
                Thread.sleep(rnd.nextInt(1000));
                System.out.println("Thread " + id + " will wait");
                cb.await();
                System.out.println("-------Thread " + id + " is over");
            } catch (InterruptedException e) {
            } catch (BrokenBarrierException e) {
            }
        }
    }
     
    public static void main(String[] args){
        CyclicBarrierDemo cbd = new CyclicBarrierDemo();
        ExecutorService es = Executors.newCachedThreadPool();
        es.submit(cbd.new TaskDemo("a"));
        es.submit(cbd.new TaskDemo("b"));
        es.submit(cbd.new TaskDemo("c"));
        es.submit(cbd.new TaskDemo("d"));
        es.shutdown();
    }
}
