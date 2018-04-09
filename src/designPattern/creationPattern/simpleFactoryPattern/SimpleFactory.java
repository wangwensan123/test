package designPattern.creationPattern.simpleFactoryPattern;

import designPattern.creationPattern.simpleFactoryPattern.Broom;
import designPattern.creationPattern.simpleFactoryPattern.Plane;

public class SimpleFactory {

    public IMoveable create(Class<?> clazz) {
        if (clazz.getName().equals(Plane.class.getName())) {
            return createPlane();
        } else if (clazz.getName().equals(Broom.class.getName())) {
            return createBroom();
                    }
        
        return null;
    }
    
    private Broom createBroom() {
        return new Broom();
    }
    
    private Plane createPlane() {
        return new Plane();
    }
    
    public static void main(String[] args) {
              // 简单工厂模式测试
      SimpleFactory simpleFactory = new SimpleFactory();
      IMoveable broom =  simpleFactory.create(Broom.class);
      broom.run();
      
      IMoveable plane =  simpleFactory.create(Plane.class);
      plane.run();

  }
}
