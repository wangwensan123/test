package thread;
/**
 *@auth wws
 *@date 2018年3月28日---上午11:32:20
 *
 **/
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicInteger;

public class AtomicIntegerTest {
    public static final AtomicInteger atomicInteger = new AtomicInteger(0);
    public static int value = 0;

    public static void main(String[] args) throws InterruptedException {
      intTest();

    }

    private  static void atomicIntegerTest() throws InterruptedException {
        ExecutorService executorService = Executors.newFixedThreadPool(10000);
        for (int i = 0; i < 10000; i++) {
            executorService.execute(() -> {
                for (int j = 0; j < 4; j++) {
                    System.out.println(atomicInteger.getAndIncrement());
                }
            });
        }
        executorService.shutdown();
        Thread.sleep(3000);
        System.out.println("最终结果是" + atomicInteger.get());
    }
    
    public static void intTest() throws InterruptedException{
      ExecutorService executorService = Executors.newFixedThreadPool(10000);
      for (int i = 0; i < 10000; i++) {
          executorService.execute(() -> {
              for (int j = 0; j < 4; j++) {
                  System.out.println(value++);
              }
          });
      }
      executorService.shutdown();
      Thread.sleep(3000);
      System.out.println("最终结果是" + value);
  }
}
