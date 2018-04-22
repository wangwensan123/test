package designPattern.behavioralPattern.template;
/**
 *@auth wws
 *@date 2018年4月19日---下午1:11:00
 *使用了JAVA的继承机制，在抽象类中定义一个模板方法，该方法引用了若干个抽象方法（由子类实现）或具体方法（子类可以覆盖重写）；
 *抽象模板：定义了一个模板方法和若干抽象方法和具体方法，
 *具体模板：继承抽象模板类并实现抽象方法
 *
 **/
public class PaymentSystem extends AbstractDesignCycle{  
  
  @Override  
  public void needAnalysis() {  
      // TODO Auto-generated method stub  
      System.out.println("支付系统的需求分析");  
  }  

  @Override  
  public void conceptualDesign() {  
      // TODO Auto-generated method stub  
      System.out.println("支付系统的概要设计");  
  }  

  @Override  
  public void detailedDesign() {  
      // TODO Auto-generated method stub  
      System.out.println("支付系统的详细设计");  
  }  

  @Override  
  public void coding() {  
      // TODO Auto-generated method stub  
      System.out.println("支付系统的编码");  
  }  

  @Override  
  public void testSystem() {  
      // TODO Auto-generated method stub  
      System.out.println("支付系统的测试");  
  }  

  @Override  
  public void publishSystem() {  
      // TODO Auto-generated method stub  
      System.out.println("支付系统的发布");  
  }  
    
} 
