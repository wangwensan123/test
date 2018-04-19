package designPattern.behavioralPattern.iterator;

import java.util.Iterator;

/**
 *@param <E>
 * @auth wws
 *@date 2018年4月18日---下午6:46:10
 *
 **/
public interface MyIterator<E> extends Iterator<E>{

     public boolean hasNext();
     
     public Object mynext();
     
     public void remove(Object o);
}
