package designPattern.behavioralPattern.strategy;
/**
 *@auth wws
 *@date 2018年4月10日---下午5:00:36
 *
 **/
public abstract class AbstractCalculator {  
  
  public int[] split(String exp,String opt){  
      String array[] = exp.split(opt);  
      int arrayInt[] = new int[2];  
      arrayInt[0] = Integer.parseInt(array[0]);  
      arrayInt[1] = Integer.parseInt(array[1]);  
      return arrayInt;  
  }  
} 
