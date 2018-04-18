package designPattern.structuralPattern.dynamicproxy;

import java.lang.reflect.Method;

/**
 *@auth wws
 *@date 2018年4月16日---下午6:26:22
 *
 **/
public interface ILogger {
  void start(Method method,String str);

  void end(Method method);
  
  void recored();
}
