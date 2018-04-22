package designPattern.behavioralPattern.state;


/**
 * 电梯门关闭以后，电梯可以做哪些事情
 */
public class ClosingState implements ILiftState {
  
    
    @Override
    public void close() {
        System.out.println("C 电梯门关闭...");
    }

    @Override
    public void open() {
      Context.getInstance().setLiftState(Context.openningState); // 置为门敞状态
      Context.getInstance().getLiftState().open();
    }

    @Override
    public void run() {
      Context.getInstance().setLiftState(Context.runningState); // 设置为运行状态；
      Context.getInstance().getLiftState().run();
    }

    @Override
    public void stop() {
      Context.getInstance().setLiftState(Context.stoppingState); // 设置为停止状态；
      Context.getInstance().getLiftState().stop();
    }
}