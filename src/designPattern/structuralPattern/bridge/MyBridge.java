package designPattern.structuralPattern.bridge;
/**
 *@auth wws
 *@date 2018年4月10日---上午11:48:33
 *
 **/
public class MyBridge extends AbstractBridge {
  

  public MyBridge(ISourceable source) {
    super(source);
  }

  @Override
  public void method() {
    this.beforeMethod();
    super.method();
    this.afterMethod();
  }

  @Override
  public void afterMethod() {
    System.out.println("do after method");
  }

  @Override
  public void beforeMethod() {
    System.out.println("do before method");
  }    

} 
