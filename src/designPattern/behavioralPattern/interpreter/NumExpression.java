package designPattern.behavioralPattern.interpreter;
/**
 *@auth wws
 *@date 2018年4月21日---下午7:31:34
 *
 **/
public class NumExpression extends AbstractExpression {
  private int num;

  public NumExpression(int _num) {
    num = _num;
  }

  @Override 
  public int interptet() {
    return num;
  }
}
