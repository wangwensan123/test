package test;

import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 *@auth wws
 *@date 2018年3月16日---上午11:48:23
 *
 **/
public class Test {
  public static void main(String[] args) throws Exception
  {
      Method method = new Test().getClass().getMethod("test", List.class,String.class);//这里的第二个参数，和getRawType()意义类似
      Type[] types = method.getGenericParameterTypes();
      System.out.println(types[0].getTypeName());
      System.out.println(types[1].getTypeName());
      
      ParameterizedType pType = (ParameterizedType) types[0];
      Type type = pType.getActualTypeArguments()[0];
      System.out.println(type);

      //type是Type类型，但直接输出的不是具体Type的五种子类型，而是这五种子类型以及WildcardType具体表现形式
      System.out.println(type.getClass().getName());
      
      method.invoke(new Test(),null,"b");
  }
  public  void test(List<ArrayList<String>[]> a,String b){
    System.out.println("-----------b="+b);
  }
//读者可将test中参数用相应类型代替
  

}
