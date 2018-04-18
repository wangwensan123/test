package designPattern.structuralPattern.dynamicproxy;

import java.lang.reflect.Method;
import java.util.Date;

/**
 *@auth wws
 *@date 2018年4月16日---下午6:28:48
 *
 **/
public class DLogger implements ILogger {
  @Override
  public void start(Method method,String str) {
    System.out.println(new Date() +"-----"+ method.getName() + " say hello start...");
  }

  @Override
  public void end(Method method) {
    System.out.println(new Date() +"-----"+ method.getName() + " say hello end");
  }

  @Override
  public void recored() {
    System.out.println(new Date() +"-----"+  " record aaaa---");
  }
}
