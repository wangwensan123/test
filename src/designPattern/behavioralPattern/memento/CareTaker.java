package designPattern.behavioralPattern.memento;
/**
 *@auth wws
 *@date 2018年4月19日---下午6:47:10
 *管理者角色：负责管理备忘录类，示例只保存了一个备忘录对象，可以通过设置栈保存多个对象
 **/
public class CareTaker {
  private EmpMemento memento;

  public EmpMemento getMemento() {
      return memento;
  }

  public void setMemento(EmpMemento memento) {
      this.memento = memento;
  }

}
