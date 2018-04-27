package thread;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.FutureTask;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 *@auth wws
 *@date 2018年3月20日---下午5:01:41
 *
 **/
public class TicketCallable implements Callable<String>{

  static Integer tick = 20;
  Lock lock = new ReentrantLock(true);
  @Override
  public String call() throws Exception {

    while(tick>0){
//      synchronized (tick) {
        lock.lock();
        if(tick>0){
          tick--;
          System.out.println(Thread.currentThread().getName()+" sell:1,and remainder:"+tick);
        }else{
          System.out.println("finish");
                }   
//           }
        lock.unlock();
     try {
       Thread.sleep(1000);
     } catch (Exception e) {
       // TODO Auto-generated catch block
       e.printStackTrace();
         }

        }
     
    return tick+"";
  }
  
  public static void main(String[] args) throws InterruptedException, ExecutionException {
    ExecutorService executor = Executors.newFixedThreadPool(4); 
    List<FutureTask<String>> list = new ArrayList<FutureTask<String>>();

    for (int i = 0; i < 15; i++) {
      FutureTask<String> task = new FutureTask<String>(new TicketCallable());
      executor.submit(task);
//      Future<String> aa = service.submit(call);
//        new Thread(task).start();
      list.add(task);
           }
    for(FutureTask<String> task:list){
      System.out.println(task.get());
          }
    executor.shutdown();
  }

  
  
}
