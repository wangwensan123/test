package designPattern.structuralPattern.composite;
/**
 *@auth wws
 *@date 2018年4月10日---下午2:30:39
 *
 **/
public class Leaf extends Component {

  public Leaf(String name) {
      super(name);
  }

  @Override
  public void add(Component c) {
      System.out.println("Can not add to a leaf");
  }

  @Override
  public void remove(Component c) {
      System.out.println("Can not remove from a leaf");
  }

  @Override
  public void display(int depth) {
      String temp = "";
      for (int i = 0; i < depth; i++){
        temp += '-';
              }          
      System.out.println(temp + name);
  }
  
}
