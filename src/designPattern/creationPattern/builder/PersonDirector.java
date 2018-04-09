package designPattern.creationPattern.builder;
/**
 *@auth wws
 *@date 2018年4月9日---下午1:13:54
 *
 **/
public class PersonDirector {  
  public Person constructPerson(PersonBuilder pb) {  
      //按照 身体--->头部--->四肢 的顺序创建人物  
      pb.buildHead();  
      pb.buildBody();  
      pb.buildFoot();  
      return pb.buildPerson();  
  }  
}  
