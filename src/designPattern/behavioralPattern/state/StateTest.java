package designPattern.behavioralPattern.state;


/**
*当一个对象内在状态改变时允许其改变行为，这个对象看起来像改变了它的类。
 */
public class StateTest {

    public static void main(String[] args) {
        Context context = Context.getInstance();
        context.setLiftState(Context.openningState);
        context.close();
        context.run();
        context.stop();
        
        context.open();
        context.run();
        context.open();
        context.close();
        context.stop();
        context.run();
    }
}
