package designPattern.structuralPattern.composite;
/**
 *@auth wws
 *@date 2018年4月10日---下午2:30:07
 *
 **/
public abstract class Component {
  protected String name;
  
  public Component(String name) {
      this.name = name;
  }
  
  public abstract void add(Component c);
  public abstract void remove(Component c);
  public abstract void display(int depth);
}
