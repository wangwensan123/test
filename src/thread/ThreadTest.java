package thread;

import java.util.Date;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

/**
 *@auth wws
 *@date 2018年3月19日---下午3:29:38
 *
 **/
public class ThreadTest {
    public static void main(String[] args) {
      ScheduledThreadPoolExecutor executor = new ScheduledThreadPoolExecutor(4, new ThreadFactory() {

        AtomicInteger i = new AtomicInteger(1);

        @Override
        public Thread newThread(Runnable r) {
          Thread th = new Thread(r);
          th.setContextClassLoader(Thread.currentThread().getContextClassLoader());
          th.setName("task-" + i.incrementAndGet());
          return th;
        }

      });
      executor.schedule(new TicketRunnable(), 1000, TimeUnit.MILLISECONDS);
      
    }
      
        protected static void aa(){
      
    }

}
