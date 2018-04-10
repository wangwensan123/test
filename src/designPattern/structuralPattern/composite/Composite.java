package designPattern.structuralPattern.composite;

import java.util.ArrayList;
import java.util.List;

/**
 *@auth wws
 *@date 2018年4月10日---下午2:31:07
 *
 **/
public class Composite extends Component {

  private List<Component> children = new ArrayList<Component>();
  
  public Composite(String name) {
      super(name);
  }

  @Override
  public void add(Component c) {
      children.add(c);
  }
  //删除当前目录下所有匹配的叶子节点
  @Override
  public void remove(Component c1) {
    for (int i = 0;i<children.size();i++) {
      Component c = children.get(i);
      if(c1.name.equals(c.name)){
        children.remove(c);
                  }
          }

  }
  //删除目录下所有匹配的叶子节点(包括子目录下的)
  public void removeSubsetLeaf(Component c1) {
    for (int i = 0;i<children.size();i++) {
      Component c = children.get(i);
      if(c1.name.equals(c.name)){
        children.remove(c);
                  }
      c.remove(c1);
          }

}

  @Override
  public void display(int depth) {
      String temp = "";
      for (int i = 0; i < depth; i++){
        temp += '-';
                }           
      System.out.println(temp + name);
      
      for (Component c : children) {
          c.display(depth + 2);
              }
  }
  
}
