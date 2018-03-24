package thread;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 *@auth wws
 *@date 2018年3月19日---下午4:30:51
 *
 **/
public class Practice1 {

  public static void main(String[] args) {
    ExecutorService service = Executors.newFixedThreadPool(4);
        //创建一个线程池
    System.out.println("begin:" + (System.currentTimeMillis()/1000));
    for (int i = 0; i < 16; i ++) {
        final String log = "" + (i+1);
                    //表示一个日志
        service.execute(new Runnable() {
                    //拿一个线程去执行
            @Override
            public void run() {
                parseLog(log);
                            }
                    }
                  );
    }
    service.shutdown();
    //最后别忘了关掉线程池
}
public static void parseLog(String log) {
    System.out.println(Thread.currentThread().getName() + "---"+ log + "---" + (System.currentTimeMillis()/1000));
    try {
        Thread.sleep(1000);
                  //模拟打印一次日志需要1秒
          }
    catch (InterruptedException e) {
        // TODO Auto-generated catch block
        e.printStackTrace();
    }
}
  
}
