package designPattern.structuralPattern.facade;
/**
 *@auth wws
 *@date 2018年4月10日---上午11:31:42
 *
 **/
public class ServiceCImpl implements ServiceC {  
  
  
  /* (non-Javadoc) 
   * @see design.facade.ServiceA#methodA() 
   */  
  @Override  
  public void methodC() {  
      System.out.println( "methodC--> is runing" );    
  }  

} 
