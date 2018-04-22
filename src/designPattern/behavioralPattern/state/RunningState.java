package designPattern.behavioralPattern.state;


/**
 * 电梯在运行状态下能做哪些动作
 */
public class RunningState implements ILiftState {
    
    @Override
    public void close() {
        // do nothing
      System.out.println("R error:电梯运行状态不能关闭操作...");
    }

    @Override
    public void open() {
        // do nothing
      System.out.println("R error:电梯运行状态不能开启操作...");
    }

    @Override
    public void run() {
        System.out.println("R 电梯上下跑...");
    }

    @Override
    public void stop() {
      Context.getInstance().setLiftState(Context.stoppingState);
      Context.getInstance().getLiftState().stop();
    }
}