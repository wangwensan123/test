package designPattern.structuralPattern.facade;
/**
 *@auth wws
 *@date 2018年4月10日---上午11:30:25
 *
 **/
public class ServiceAImpl implements ServiceA {  
  
  /* (non-Javadoc) 
   * @see design.facade.ServiceA#methodA() 
   */  
  @Override  
  public void methodA() {  
      System.out.println( "methodA--> is runing" );   
  }  

} 
