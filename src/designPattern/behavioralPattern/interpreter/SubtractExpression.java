package designPattern.behavioralPattern.interpreter;
/**
 *@auth wws
 *@date 2018年4月21日---下午7:15:52
 *
 **/
public class SubtractExpression extends AbstractExpression {
  private AbstractExpression left, right;

  public SubtractExpression(AbstractExpression _left, AbstractExpression _right) {
    left = _left;
    right = _right;
  }

  @Override
  public int interptet() {
    return left.interptet() - right.interptet();
  }
}
