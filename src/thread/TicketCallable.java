package thread;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.FutureTask;
import java.util.concurrent.ThreadPoolExecutor;

/**
 *@auth wws
 *@date 2018年3月20日---下午5:01:41
 *
 **/
public class TicketCallable implements Callable<Integer>{

  static Integer tick = 20;
  
  @Override
  public Integer call() throws Exception {

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
     
    return tick;
  }
  
  public static void main(String[] args) {
    ExecutorService service = Executors.newFixedThreadPool(4);
    System.out.println("begin:" + (System.currentTimeMillis()/1000));
    TicketCallable  call = new TicketCallable();
    for (int i = 0; i < 5; i ++) {
        FutureTask<Integer> task = new FutureTask<Integer>(call);
        service.execute(task);
//        new Thread(task).start();
//          service.submit(task);

           }
    service.shutdown();
    
    Boolean flag = new Boolean(true);
          Long a = new Long(1);
  }

  
  
}
