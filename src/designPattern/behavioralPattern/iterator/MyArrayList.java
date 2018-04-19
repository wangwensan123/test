package designPattern.behavioralPattern.iterator;

import java.math.BigDecimal;

/**
 *@auth wws
 *@date 2018年4月18日---下午6:58:54
 *
 **/
public class MyArrayList implements MyList{
  
    private Object[] list;
    private int size = 0;
    private int index = 0;
    
    public MyArrayList(){
      size = 0;
      index = 0;
      list = new Object[10];
    }
    
    public MyArrayList(int length){
      size = 0;
      index = 0;
      list = new Object[length];
    }

  @Override
  public void add(Object obj) {
    if(size >= new BigDecimal(list.length * 0.75).intValue()){
      Object[] newlist = new Object[list.length * 2];
      System.arraycopy(list, 0, newlist, 0, list.length);
      list = newlist;
      newlist = null;
          }
    list[index++] = obj;
    size++;
  }
  

  @Override
  public void remove(int index) {
    if(index > size){
      throw new IndexOutOfBoundsException("Index: "+index+", Size: "+size);
        }
    System.arraycopy(list, index+1, list, index,size - index - 1);
    list[--size] = null;
  }

  @Override
  public Object get(int index) {
    return list[index];
  }


  @Override
  public int getSize() {
    return this.size;
  }

  @Override
  public MyIterator myiterator() {
    return new ConcreteIterator(this);
  }

  @Override
  public MyIterator iterator() {
    return new Itr();
  }

  private class Itr implements MyIterator{
    
    private int cursor;

    @Override
    public boolean hasNext() {
       if(cursor < getSize()){
         return true;
       }else{
         return false;
               }

    }

    @Override
    public Object mynext() {
      Object obj = list[cursor];
      cursor++;
      return obj;
    }

    @Override
    public void remove(Object o) {
    }

    @Override
    public Object next() {
      Object obj = list[cursor];
      cursor++;
      return obj;
    }
    
  }



}
