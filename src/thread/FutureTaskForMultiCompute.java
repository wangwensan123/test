package thread;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.FutureTask;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 *@auth wws
 *@date 2018年4月27日---下午4:51:12
 *
 **/
public class FutureTaskForMultiCompute {    
  
  public static void main(String[] args) {  
        
      FutureTaskForMultiCompute inst=new FutureTaskForMultiCompute();  
              // 创建任务集合  
      List<Future<Integer>> taskList = new ArrayList<Future<Integer>>();  
              // 创建线程池  
      ExecutorService exec = Executors.newFixedThreadPool(5);  
      for (int i = 0; i < 20; i++) {  
                //传入Callable对象创建FutureTask注意这里的ft实现了callable接口  
          FutureTask<Integer> ft = new FutureTask<Integer>(new ComputeTask(i, ""+i));  
                        //第二种提交方式为 
//          Future<Integer> ft = exec.submit(new ComputeTask(i,""+i));  
          taskList.add(ft);  
                        //提交给线程池执行任务，也可以通过exec.invokeAll(taskList)一次性提交所有任务;  
          exec.submit(ft);
              }  
        
      System.out.println("所有计算任务提交完毕, 主线程接着干其他事情！");  

                // 开始统计各计算线程计算结果  
      Integer totalResult = 0;  
      for (Future<Integer> ft : taskList) {  
          try {  
              //FutureTask的get方法会自动阻塞,直到获取计算结果为止  
              totalResult = totalResult + ft.get();  
          } catch (InterruptedException e) {  
              e.printStackTrace();  
          } catch (ExecutionException e) {  
              e.printStackTrace();  
                        }  
                }  
          System.out.println("--------------");
              // 关闭线程池  
      exec.shutdown();  
      System.out.println("多任务计算后的总结果是:" + totalResult);

  }  

  static final class ComputeTask implements Callable<Integer> {  
    
    private Integer result = 0;  
    private String taskName = "";  
    Lock lock = new ReentrantLock();
    public ComputeTask(Integer iniResult, String taskName){  
        result = iniResult;  
        this.taskName = taskName;  
        System.out.println(Thread.currentThread().getName()+"生成子线程计算任务: "+taskName);  
    }  
      
    public String getTaskName(){  
        return this.taskName;  
    }  
      
    @Override  
    public Integer call() throws Exception {  
        // TODO Auto-generated method stub  
        for (int i = 0; i < 100; i++) {  
            result =+ i;  
          }  
        // 休眠5秒钟，观察主线程行为，预期的结果是主线程会继续执行，到要取得FutureTask的结果是等待直至完成。  
      Thread.sleep(1000);
        System.out.println(Thread.currentThread().getName()+"子线程计算任务: "+taskName+" 执行完成! result="+result);  
        return result;  
    }  
  }
    
} 
