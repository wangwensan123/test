package designPattern.behavioralPattern.iterator;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 *@auth wws
 *@date 2018年4月18日---下午6:53:22
 *迭代器模式（Iterator），提供一种方法顺序访问一个聚合对象中的各种元素，而又不暴露该对象的内部表示。
 *(1)迭代器角色（Iterator）:定义遍历元素所需要的方法，一般来说会有这么三个方法：取得下一个元素的方法next()，
 *判断是否遍历结束的方法hasNext()），移出当前对象的方法remove(),
 *(2)具体迭代器角色（Concrete Iterator）：实现迭代器接口中定义的方法，完成集合的迭代。
 *(3)容器角色(Aggregate):  一般是一个接口，提供一个iterator()方法，例如java中的Collection接口，List接口，Set接口等
 *(4)具体容器角色（ConcreteAggregate）：就是抽象容器的具体实现类，比如List接口的有序列表实现ArrayList，List接口的链表实现LinkList，
 *Set接口的哈希列表的实现HashSet等。
 **/
public class IteratorTest {
  
    public static void main(String[] args) {
      MyList list = new MyArrayList();
      list.add("aa");list.add("bb");list.add("cc");list.add(new String[]{"1"});
      for(int i = 0; i < 150;i++){
        list.add("i"+i);
              }
      System.out.println("size="+list.getSize());

      MyIterator aa = list.myiterator();
      while(aa.hasNext()){
          System.out.print(aa.mynext()+",");        
              }
      System.out.println();
      list.remove(153);
      System.out.println("size="+list.getSize());
      for(Object obj:list){
        System.out.print(obj+",");
            }
      
      
      
      System.out.println();
      List list1 = new ArrayList();
      list1.add("aa");list1.add("bb");list1.add("cc");list1.add(new String("dd"));list1.add(1);
      Iterator aa1 = list1.iterator();
      while(aa1.hasNext()){
          System.out.print(aa1.next()+",");                
              }
            
    }

}
