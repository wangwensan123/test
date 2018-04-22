package designPattern.behavioralPattern.interpreter;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

/**
 *@auth wws
 *@date 2018年4月21日---下午7:16:17
 *
 **/
public class Calculator {

  private List<AbstractExpression> list = new ArrayList<>();

  public Calculator(String expression) {
    AbstractExpression left, right;
    String[] elements = expression.split(" ");
    for (int i = 0; i < elements.length; ++i) {
      char c = elements[i].charAt(0);
      switch (c) {
        case '+':
          left = list.get(list.size()-1);
          right = new NumExpression(Integer.valueOf(elements[++i]));
          list.add(new PlusExpression(left, right));
          break;
        case '-':
          left = list.get(list.size()-1);
          right = new NumExpression(Integer.valueOf(elements[++i]));
          list.add(new SubtractExpression(left, right));
          break;
        default:
          list.add(new NumExpression(Integer.valueOf(elements[i])));
          break;
      }
    }
  }

  public int calculate() {
    return list.get(list.size()-1).interptet();
  }
}
