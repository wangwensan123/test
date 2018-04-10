package designPattern.structuralPattern.bridge;


/**
 *@auth wws
 *@date 2018年4月10日---上午11:48:03
 *
 **/
public abstract class AbstractBridge {    
  private ISourceable source;    
  
  public void method(){
      source.method();    
  }    
  
  public AbstractBridge(ISourceable source) {
    this.source = source;
}
      
  public abstract void afterMethod();
  
  public abstract void beforeMethod();
  
} 
