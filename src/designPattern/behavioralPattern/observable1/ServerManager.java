package designPattern.behavioralPattern.observable1;

import java.util.Observable;

/**
 *@auth wws
 *@date 2018年4月19日---下午3:50:29
 *
 **/
public class ServerManager extends Observable {
  private int data = 0;

  public int getData() {
    return data;
  }

  public void setData(int i) {
    if (this.data != i) {
      this.data = i;
      setChanged();
    }
    notifyObservers(); // 只有在setChange()被调用后，notifyObservers()才会去调用update()，否则什么都不干。

  }

}
