package designPattern.structuralPattern.facade;
/**
 *@auth wws
 *@date 2018年4月10日---上午11:33:02
 *Facade（外观）模式为子系统中的各类（或结构与方法）提供一个简明一致的界面，隐藏子系统的复杂性，使子系统更加容易使用。

 **/
public class Client {  
  

  public static void main(String[] args) {  
        ServiceA sa = new ServiceAImpl();  
        ServiceB sb = new ServiceBImpl();
        ServiceC sc = new ServiceCImpl();
        sa.methodA();  
        sb.methodB();
        sc.methodC();
        System.out.println("=====================");  
        Facade f = new Facade();  
        f.methodA();  
        f.methodB();  
        f.methodC() ;  
  }  

}
