package designPattern.behavioralPattern.command;
/**
 *@auth wws
 *@date 2018年4月19日---下午6:11:11
 *将来自客户端的请求传入一个对象，从而使你可用不同的请求对客户进行参数化。
 *用于“行为请求者”与“行为实现者”解耦，可实现二者之间的松耦合，以便适应变化。分离变化与不变的因素。
   *1.降低对象之间的耦合度。
  *2.新的命令可以很容易地加入到系统中。
  *3.可以比较容易地设计一个组合命令。
  *4.调用同一方法实现不同的功能
 **/
public class CommandTest {
  
   public static void main(String[] args) {
      IWork work = new WorkImplMan();
      ICommand commond = new CommondImpl(work);
      commond.execute();
      System.out.println("---------------");
              
      LittleLead lead = new LittleLead(new CommondImpl(work));
      lead.action();
      
    }

}
