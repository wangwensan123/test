package task;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Hashtable;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.SortedSet;
import java.util.Stack;
import java.util.TreeMap;
import java.util.TreeSet;
import java.util.Vector;
import java.util.WeakHashMap;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;


/**
 *@auth wws
 *@date 2018年3月21日---上午11:08:24
 *
 **/
public class TaskTest {
  
  
  public static void main(String[] args) {
      Task.init(Runtime.getRuntime().availableProcessors());
      System.out.println("111");
      System.out.println(" Task.activeThread()="+ Task.activeThread());
      new Task(){
        @Override
        public void onExecute() {
            System.out.println("----------");
          
                  }      
      }.schedule(3000);

      System.out.println(" Task.activeThread()="+ Task.activeThread());
//      Task.shutdown();
      System.out.println("222");
      
    
  }
  


}
