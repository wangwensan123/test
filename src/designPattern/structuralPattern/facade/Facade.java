package designPattern.structuralPattern.facade;
/**
 *@auth wws
 *@date 2018年4月10日---上午11:32:13
 *
 **/
public class Facade {  
  ServiceA sa;  
  ServiceB sb;  
  ServiceC sc;  

  public Facade() {  
      sa = new ServiceAImpl();  
      sb = new ServiceBImpl();  
      sc = new ServiceCImpl();  
  }  

  public void methodA() {  
      sa.methodA();  
      //sb.methodB();  
  }  

  public void methodB() {  
      sb.methodB();  
      //sc.methodC();  
  }  

  public void methodC() {  
      sc.methodC();  
      //sa.methodA();  
  }  
    
}  
