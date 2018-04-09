package designPattern.structuralPattern.dynamicproxy;
/**
 *@auth wws
 *@date 2018年3月29日---上午11:49:03
 *
 **/
public class Master1 implements Person{

  @Override
  public void searchHouse() {
        System.out.println("我是主人，我要找房子，三室一厅!");
    
  }

  @Override
  public void rentHouse() {
    System.out.println("租一个大房子!");
    
  }
  
  public void aaa() {
    System.out.println("1231231!");
    
  }

}
