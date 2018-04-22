package test;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

/**
 *@auth wws
 *@date 2018年3月31日---下午10:50:11
 *
 **/
public class Mytest{
  
  
  public static void main(String[] args) {
      Stack stack = new Stack();
      stack.push("1");
      stack.push("2");
      stack.push("3");
      stack.push("4");
      Object aa = stack.pop();
      System.out.println(aa);
   
}

public static void getData(List<?> data) {
  System.out.println("data :" + data.get(0));
}

public static  void getUperNumber(List<? extends Number> data) {
      System.out.println("data :" + data.get(0));
   }

}
