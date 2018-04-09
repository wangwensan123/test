package designPattern.structuralPattern.dynamicproxy;
/**
 *@auth wws
 *@date 2018年3月29日---上午11:49:47
 *
 **/


public class MasterProxy implements Person{
    
    private Person master = new Master1();

    @Override
    public void searchHouse() {
      System.out.println("我是链家，我帮别人找房子..");
      master.searchHouse();
      System.out.println("我是链家，已经找完了..");
      
    }

    @Override
    public void rentHouse() {
      System.out.println("我是链家，在我这租房子");
      master.rentHouse();
      System.out.println("我是链家，已经我这租房子了..");
      
    }
    

}
