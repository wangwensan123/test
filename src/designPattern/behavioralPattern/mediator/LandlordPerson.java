package designPattern.behavioralPattern.mediator;
/**
 *@auth wws
 *@date 2018年4月21日---下午6:54:20
 *具体同事类：这里的角色是 房东
 **/
public class LandlordPerson extends Person {  
  
  public LandlordPerson(String name, Mediator mediator) {  
      super(name,mediator);  
  }  
    
  @Override  
  protected void sendMessage(String msg) {  
      mediator.operation(this, msg);  
  }  

  @Override  
  protected void getMessage(String msg) {  
      System.out.println("房东["+ name +"]收到中介发来的消息：" + msg);  
  }  

} 
