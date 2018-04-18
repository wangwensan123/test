package designPattern.behavioralPattern.strategy;
/**
 *@auth wws
 *@date 2018年4月10日---下午5:01:35
 *
 **/
public class Minus extends AbstractCalculator implements ICalculator {  
  
  @Override  
  public int calculate(String exp) {  
      int arrayInt[] = split(exp,"-");  
      return arrayInt[0]-arrayInt[1];  
  }  

} 
