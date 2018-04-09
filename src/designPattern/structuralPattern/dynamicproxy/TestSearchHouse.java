package designPattern.structuralPattern.dynamicproxy;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Proxy;

/**
 *@auth wws
 *@date 2018年3月29日---上午11:50:49
 *
 **/
public class TestSearchHouse {
  public static void main(String[] args) {
      Person person1 =  (Person) new HomeLink().getInstance(new Master1());
      person1.searchHouse();
      System.out.println("----------------");
      
      InvocationHandler homelink =  new HomeLink(new Master());
      Person person2 = (Person) Proxy.newProxyInstance(Person.class.getClassLoader(), new Class[]{Person.class}, homelink);
      person2.searchHouse();
      
      System.out.println("----------------");
      Person person3 = new MasterProxy();
      person3.searchHouse();
  }
}
