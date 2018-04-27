package thread;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 *@auth wws
 *@date 2018年3月20日---上午11:59:46
 *
 **/
public class TicketRunnable implements Runnable{

   static Integer tick = 20;
 

 @Override
 public void run() {
  while(tick>0){
    synchronized (tick) {
      if(tick>0){
        System.out.println(Thread.currentThread().getName()+" sell:1,and remainder:"+tick);
        tick--;
      }else{
        System.out.println("finish");
              }   
         }
    
   try {
     Thread.sleep(1000);
   } catch (InterruptedException e) {
     // TODO Auto-generated catch block
     e.printStackTrace();
       }

      }
   }
 
 
 public static void main(String[] args) {

// ExecutorService service = Executors.newFixedThreadPool(4);
// System.out.println("begin:" + (System.currentTimeMillis()/1000));
// TicketRunnable  aa = new TicketRunnable();
// for (int i = 0; i < 5; i ++) {
//     service.execute(aa);
//    }
// service.shutdown();

     aaa();
}

 public static void aaa(){
   ThreadPoolExecutor executor = new ThreadPoolExecutor(5, 10, 200, TimeUnit.MILLISECONDS,new ArrayBlockingQueue<Runnable>(5));

    for(int i=0;i<15;i++){
       TicketRunnable myTask = new TicketRunnable();
       executor.execute(myTask);
       System.out.println("线程池中线程数目："+executor.getPoolSize()+"，队列中等待执行的任务数目："+executor.getQueue().size()+"，已执行完别的任务数目："+executor.getCompletedTaskCount());
    }
    executor.shutdown();
 }
 
}
