package designPattern.behavioralPattern.interpreter;

import java.util.Stack;

/**
 *@auth wws
 *@date 2018年4月21日---下午7:16:17
 *
 **/
public class Calculator1 {

  private Stack<AbstractExpression> stack = new Stack<AbstractExpression>();

  public Calculator1(String expression) {
    AbstractExpression left, right;
    String[] elements = expression.split(" ");
    for (int i = 0; i < elements.length; ++i) {
      char c = elements[i].charAt(0);
      switch (c) {
        case '+':
          left = stack.pop();
          right = new NumExpression(Integer.valueOf(elements[++i]));
          stack.push(new PlusExpression(left, right));
          break;
        case '-':
          left = stack.pop();
          right = new NumExpression(Integer.valueOf(elements[++i]));
          stack.push(new SubtractExpression(left, right));
          break;
        default:
          stack.push(new NumExpression(Integer.valueOf(elements[i])));
          break;
      }
    }
  }

  public int calculate() {
    return stack.pop().interptet();
  }
}
