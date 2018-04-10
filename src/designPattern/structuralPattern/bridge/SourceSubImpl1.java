package designPattern.structuralPattern.bridge;
/**
 *@auth wws
 *@date 2018年4月10日---上午11:47:20
 *
 **/
public class SourceSubImpl1 implements ISourceable {    
  
  @Override    
  public void method() {    
      System.out.println("this is the first sub!");    
  }    
}
