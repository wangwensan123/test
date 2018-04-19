package designPattern.behavioralPattern.iterator;

import java.util.List;

/**
 *@auth wws
 *@date 2018年4月18日---下午6:47:36
 *
 **/
public class ConcreteIterator implements MyIterator{
  
    private MyList list;
    private int index;
    
    public ConcreteIterator(MyList list){
      super();
      this.list = list;
    }

  @Override
  public boolean hasNext() {
    if(index < list.getSize()){
      return true;
    }else{
      return false;
          }
  }

  @Override
  public Object mynext() {
     Object obj = list.get(index);
     index++;
    return obj;
  }

  @Override
  public void remove(Object o) {
//    list.remove(o);
  }

  @Override
  public Object next() {
    return null;
  }

}
