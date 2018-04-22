package designPattern.behavioralPattern.visitor;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 *@auth wws
 *@date 2018年4月21日---上午12:13:15
 *
 **/
public class ObjectStructure {
  
  private List<IElement> list = new ArrayList<IElement>();
  
    public void add(IElement e){
      list.add(e);
    }
    
    public void remove(IElement e){
      list.remove(e);
    }
  
  // 在本方法中,我们实现了对 Collection 的元素的成功访问
  public void accpet(IVisitor visitor) {
    Iterator<IElement> iterator = list.iterator();
    while (iterator.hasNext()) {
      Object o = iterator.next();
      if (o instanceof IElement) {
        ((IElement) o).accept(visitor);
      }
    }
  }

}
