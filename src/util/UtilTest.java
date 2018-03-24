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
    
  public static void MyMap() {
    Map<String, String> map = new HashMap<String, String>();
    map.put("1", "a");
    map.put("2", "nnnnn");
    Set<Entry<String, String>> entry = map.entrySet();
    Iterator<Entry<String, String>> ite = entry.iterator();
    while (ite.hasNext()) {
      Entry<String, String> aa = ite.next();
      System.out.println(aa.getKey() + ":" + aa.getValue());
          }
    
    Set<String> keys = map.keySet();
    Iterator<String> itek = keys.iterator();
    while (itek.hasNext()) {
      System.out.println(itek.next());
    }
    
    Collection<String> values = map.values();
    Iterator<String> itev = values.iterator();
    while (itev.hasNext()) {
      System.out.println(itev.next());
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
