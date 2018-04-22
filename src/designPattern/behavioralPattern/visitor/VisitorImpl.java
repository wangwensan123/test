package designPattern.behavioralPattern.visitor;


/**
 * 
 */
class VisitorImpl implements IVisitor {


    public void visitString(StringElement stringE) {
        System.out.println("'" + stringE.getValue() + "'");
    }

    public void visitFloat(FloatElement floatE) {
        System.out.println(floatE.getValue().toString() + "f");
    }
}