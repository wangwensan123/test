package designPattern.structuralPattern.decorator;
/**
 *@auth wws
 *@date 2018年4月9日---下午7:16:11
 *
 **/
public abstract class Decorator implements IPerson {

  protected IPerson person;
  
  public void setPerson(IPerson person) {
      this.person = person;
  }
  
  public void eat() {
      person.eat();
  }
}
