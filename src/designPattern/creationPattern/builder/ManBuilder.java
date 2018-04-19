package designPattern.creationPattern.builder;
/**
 *@auth wws
 *@date 2018年4月9日---下午1:13:07
 *
 **/
public class ManBuilder implements IBuilder {  
  
  Person person;  
 
  public ManBuilder() {  
      person = new Person();//创建一个person实例，用于调用set方法  
  }  
 
  public void buildBody() {  
      person.setBody("建造身体部分");  
  }  
 
  public void buildFoot() {  
      person.setFoot("建造四肢部分");  
  }  
 
  public void buildHead() {  
      person.setHead("建造头部部分");  
  }  
 
  public Person buildPerson() {  
      return person;//返回一个person实例  
  }  
 
} 
