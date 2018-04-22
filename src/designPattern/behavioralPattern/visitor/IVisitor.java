package designPattern.behavioralPattern.visitor;


/**
 * 
 */
interface IVisitor {
    public void visitString(StringElement stringE);

    public void visitFloat(FloatElement floatE);

}