package designPattern.behavioralPattern.memento;
/**
 *@auth wws
 *@date 2018年4月19日---下午6:48:13
 *备忘录模式，即保存某个对象内部状态的拷贝，这样以后就可以将该对象恢复到原先的状态。
 
 *发起者角色(Originator)：负责创建一个备忘录用以记录当前时刻它的内部状态，并可以使用备忘录恢复内部状态。
 *备忘录角色(Memento):负责存储Originator对象的内部状态，并可以防止Originator以外的其他对象访问备忘录。
 *管理者角色(CareTake)：负责保存好备忘录。
 **/
public class MementoTest {
  public static void main(String[] args) {
    CareTaker taker=new CareTaker();

    Emp emp=new Emp("张三",18,2000);
    System.out.println("第一次打印对象："+emp.getName()+"----"+emp.getAge()+"---"+emp.getSalary());

    taker.setMemento(emp.memento());//进行备忘

    emp.setAge(20);
    emp.setSalary(3000);
    System.out.println("第二次打印对象："+emp.getName()+"----"+emp.getAge()+"---"+emp.getSalary());

    emp.recovery(taker.getMemento());//数据恢复
    System.out.println("第三次打印对象："+emp.getName()+"----"+emp.getAge()+"---"+emp.getSalary());

}
}
