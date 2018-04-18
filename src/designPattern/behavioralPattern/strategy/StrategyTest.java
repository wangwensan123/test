package designPattern.behavioralPattern.strategy;
/**
 *@auth wws
 *@date 2018年4月10日---下午5:02:11
 *策略模式属于对象的行为模式。其用意是针对一组算法，将每一个算法封装到具有共同接口的独立的类中，
 *从而使得它们可以相互替换。策略模式使得算法可以在不影响到客户端的情况下发生变化。
 **/
public class StrategyTest {  
  
  public static void main(String[] args) {  
      String exp = "2+8";  
      ICalculator cal = new Plus();  
      int result = cal.calculate(exp);  
      System.out.println(result);  
  }  
} 
