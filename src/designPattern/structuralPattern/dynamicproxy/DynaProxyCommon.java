package designPattern.structuralPattern.dynamicproxy;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

/**
 *@auth wws
 *@date 2018年4月16日---下午6:32:27
 *
 **/
public class DynaProxyCommon implements InvocationHandler {

      private Object target;//目标对象
      private Object advice;//通知对象
      private String before;//执行前方法
      private String after;//执行后方法
    
              /*
               * 
               */
      public Object getInstance(Object target, Object advice, String before, String after) {
        this.target = target;
        this.advice = advice;
        this.before = before;
        this.after = after;
        Class clazz = target.getClass();
        Object obj = Proxy.newProxyInstance(clazz.getClassLoader(), clazz.getInterfaces(), this);
        return obj;
      }
    
      @Override
      public Object invoke(Object obj, Method method, Object[] args) throws Throwable {
        Class clazz = this.advice.getClass();
        if(null!=this.before&&!this.before.equals("")){
         Method start = clazz.getDeclaredMethod(this.before, new Class[] { Method.class,String.class});
         start.invoke(this.advice, start,"");
                   }

        method.invoke(this.target, args);
    
        if(null!=this.after&&!this.after.equals("")){
          Method end = clazz.getDeclaredMethod(this.after, new Class[] { Method.class });
          end.invoke(this.advice, end);
                    }
        return null;
      }

}
