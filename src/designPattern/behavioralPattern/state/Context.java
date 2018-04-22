package designPattern.behavioralPattern.state;


public class Context {
    
    // 定义出所有的电梯状态
    public final static ILiftState openningState = new OpenningState();
    public final static ILiftState closeingState = new ClosingState();
    public final static ILiftState runningState = new RunningState();
    public final static ILiftState stoppingState = new StoppingState();

        // 定一个当前电梯状态
    private ILiftState liftState;
    
    private static  Context context = null;
      
    private Context(){
      System.out.println("Context正在执行构造器..."); 
        }
    
    public static Context getInstance(){
      if(context == null){
        context = new Context();
              }
      return context;
        }


    public ILiftState getLiftState() {
        return liftState;
    }

    public void setLiftState(ILiftState liftState) {
        this.liftState = liftState;
    }

    public void open() {
        this.liftState.open();
    }

    public void close() {
        this.liftState.close();
    }

    public void run() {
        this.liftState.run();
    }

    public void stop() {
        this.liftState.stop();
    }
}