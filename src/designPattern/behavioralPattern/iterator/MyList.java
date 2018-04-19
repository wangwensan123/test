package designPattern.behavioralPattern.iterator;

import java.util.Iterator;

/**
 *@param <E>
 * @auth wws
 *@date 2018年4月18日---下午6:57:00
 *
 **/
public interface MyList<E> extends Iterable<E>{

  public void add(Object obj);
  
  public void remove(int index);

  public Object get(int index);
  
  public MyIterator myiterator();

  public int getSize();
}
