package designPattern.creationPattern.builder;
/**
 *@auth wws
 *@date 2018年4月9日---下午1:12:40
 *
 **/
public interface PersonBuilder {  
  void buildHead();  
  void buildBody();  
  void buildFoot();  
  Person buildPerson();//组装  
}  
