package util;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.Stack;
import java.util.TreeMap;
import java.util.TreeSet;
import java.util.Vector;
import java.util.WeakHashMap;
import java.util.concurrent.ConcurrentHashMap;

/**
 *@auth wws
 *@date 2018年3月22日---下午12:04:14
 *
 **/
public class UtilTest {
  
    public static void main(String[] args) {
      MyMap();
    }
    /*
 * HashMap,LinkedHashMap,WeakHashMap对象的key、value值均可为null。
 * TreeMap对象的key不可为null、value值可为null。TreeMap.compare报错
 * Hashtable,ConcurrentHashMap对象的key、value值均不可为null。
 * 
 * TreeSet不能为null
   * 
  *ConcurrentHashMap代替同步的Map（Collections.synchronized（new HashMap()）），众所周知，HashMap是根据散列值分段存储的，
     *同步Map在同步的时候锁住了所有的段，而ConcurrentHashMap加锁的时候根据散列值锁住了散列值锁对应的那段，因此提高了并发性能。
  *ConcurrentHashMap也增加了对常用复合操作的支持，比如"若没有则添加"：putIfAbsent()，替换：replace()。这2个操作都是原子操作。
  *CopyOnWriteArrayList和CopyOnWriteArraySet分别代替List和Set，主要是在遍历操作为主的情况下来代替同步的List和同步的Set，
     *这也就是上面所述的思路：迭代过程要保证不出错，除了加锁，另外一种方法就是"克隆"容器对象。
  *ConcurrentLinkedQuerue是一个先进先出的队列。它是非阻塞队列。
  *ConcurrentSkipListMap可以在高效并发中替代SoredMap。
  *ConcurrentSkipListSet可以在高效并发中替代SoredSet
     */
  public static void MyMap() {
//    Map<String, String> map = new TreeMap<String, String>();
//    map.put("1", "1");
//    map.put("2", "nnnnn");
//    Set<Entry<String, String>> entry = map.entrySet();
//    Iterator<Entry<String, String>> ite = entry.iterator();
//    while (ite.hasNext()) {
//      Entry<String, String> aa = ite.next();
//      System.out.println(aa.getKey() + ":" + aa.getValue());
//          }
    Set<String> list2 = new LinkedHashSet<String>();
    list2.add(null);
    list2.add(null);
    list2.add("1");
    Iterator<String> iteset = list2.iterator();
    while (iteset.hasNext()) {
      System.out.println(iteset.next());
          }
  }
    
    public static void Util(){
      List list = new ArrayList();
      List list1 = new LinkedList();
      Vector list2 = new Vector();
      Stack list3 = new Stack();             
      List  list4  =  Collections.synchronizedList(new  LinkedList()); 
      Set set = new HashSet();
      Set set1 = new TreeSet();
      Set set2 = new LinkedHashSet();
      
      Map map = new HashMap();
      Map map1 = new TreeMap();
      Map map11 = new LinkedHashMap();
      Map map3 = new Hashtable();
      Map map4 = new ConcurrentHashMap();
      Map map5 = new WeakHashMap();
      
      Date date = new Date();
      Object o = new Object();
      Integer a = new Integer(1);
    }

}
