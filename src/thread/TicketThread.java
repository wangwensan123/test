package thread;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 *@auth wws
 *@date 2018年3月19日---下午3:26:07
 *
 **/
public class TicketThread extends Thread{
  
  public TicketThread(){
     super();      
    }
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
      sleep(1000);
    } catch (InterruptedException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
        }

       }
    }
  
  public static void main(String[] args) {
    TicketThread station1=new TicketThread();
//    TicketThread station2=new TicketThread();
//    TicketThread station3=new TicketThread();
    try {
      station1.sleep(10000);
    } catch (InterruptedException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
          }
    station1.start();
//    station2.start();
//    station3.start();
//    ExecutorService service = Executors.newFixedThreadPool(4);
//    System.out.println("begin:" + (System.currentTimeMillis()/1000));
//    TicketThread  aa = new TicketThread();
//    for (int i = 0; i < 5; i ++) {
//        service.execute(aa);
//          }
//    service.shutdown();

  }

}
