package designPattern.behavioralPattern.templatemethod;
/**
 *@auth wws
 *@date 2018年4月19日---下午1:11:30
 *
 **/
//物流系统类  
public class LogisticSystem extends DesignCycle{  

  @Override  
  public void needAnalysis() {  
      // TODO Auto-generated method stub  
      System.out.println("物流系统的需求分析");  
  }  

  @Override  
  public void conceptualDesign() {  
      // TODO Auto-generated method stub  
      System.out.println("物流系统的概要设计");  
  }  

  @Override  
  public void detailedDesign() {  
      // TODO Auto-generated method stub  
      System.out.println("物流系统的详细设计");  
  }  

  @Override  
  public void coding() {  
      // TODO Auto-generated method stub  
      System.out.println("物流系统的编码");  
  }  

  @Override  
  public void testSystem() {  
      // TODO Auto-generated method stub  
      System.out.println("物流系统的测试");  
  }  

  @Override  
  public void publishSystem() {  
      // TODO Auto-generated method stub  
      System.out.println("物流系统的发布");  
  }  

}
