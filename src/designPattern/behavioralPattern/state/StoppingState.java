package designPattern.behavioralPattern.state;


/**
 * 在停止状态下能做什么事情
 */
public class StoppingState implements ILiftState {
    
    @Override
    public void close() {
        // do nothing;
      Context.getInstance().setLiftState(Context.closeingState);
      Context.getInstance().getLiftState().close();
    }

    @Override
    public void open() {
      Context.getInstance().setLiftState(Context.openningState);
      Context.getInstance().getLiftState().open();
    }

    @Override
    public void run() {
      Context.getInstance().setLiftState(Context.runningState);
      Context.getInstance().getLiftState().run();
    }

    @Override
    public void stop() {
      System.out.println("S 电梯门停止...");
    }
}