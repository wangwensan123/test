package designPattern.structuralPattern.facade;
/**
 *@auth wws
 *@date 2018年4月10日---上午11:31:06
 *
 **/
public class ServiceBImpl implements ServiceB {  
  
  
  /* (non-Javadoc) 
   * @see design.facade.ServiceA#methodA() 
   */  
  @Override  
  public void methodB() {  
      System.out.println( "methodB--> is runing" );   
  }  

} 
