package designPattern.behavioralPattern.state;



/**
 * 在电梯门开启的状态下能做什么事情
 */
public class OpenningState implements ILiftState {
    
  
    @Override
    public void close() {
      Context context = Context.getInstance();
      context.setLiftState(Context.closeingState);
      context.getLiftState().close();

    }

    @Override
    public void open() {
      System.out.println("O 电梯开门...");
    }

    @Override
    public void run() {
        // do nothing;
      System.out.println("O error:电梯开门状态不能上下跑动...");
    }

    // 开门还不停止？
    public void stop() {
        // do nothing;
      System.out.println("O error:电梯开门状态不能停止操作...");
    }
}