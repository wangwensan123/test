package designPattern.behavioralPattern.mediator;
/**
 *@auth wws
 *@date 2018年4月21日---下午6:53:54
 *具体同事类：这里的角色是 租房者 
 **/
public class RenterPerson extends Person {  
  
  public RenterPerson(String name, Mediator mediator) {  
      super(name, mediator);  
  }  
    

  @Override  
  protected void sendMessage(String msg) {  
      mediator.operation(this, msg);  
  }  

  @Override  
  protected void getMessage(String msg) {  
      System.out.println("求租者[" + name + "]收到中介发来的消息： " + msg);  
  }  

} 
