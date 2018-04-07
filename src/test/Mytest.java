package test;

import java.util.ArrayList;
import java.util.List;

/**
 *@auth wws
 *@date 2018年3月31日---下午10:50:11
 *
 **/
public class Mytest{
  
  
  public static void main(String[] args) {
    List<String> name = new ArrayList<String>();
    List<Integer> age = new ArrayList<Integer>();
    List<Number> number = new ArrayList<Number>();
    List<Test> test = new ArrayList<Test>();
    
    name.add("icon");
    age.add(18);
    number.add(314);
    test.add(new Test());

//    getUperNumber(name);//1
    getUperNumber(age);//2
    getUperNumber(number);//3
//    getUperNumber(test);//4
   
}

public static void getData(List<?> data) {
  System.out.println("data :" + data.get(0));
}

public static  void getUperNumber(List<? extends Number> data) {
      System.out.println("data :" + data.get(0));
   }

}
