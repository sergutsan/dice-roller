package org.sergut.diceroller.adt;


public class ConstantNode implements Node {

    private int value;
    
    public ConstantNode(int value) {
	this.value = value;
    }
    
    @Override
    public int getResult() {
	return value;
    }
    
    @Override
    public String toString() {
	return value + "";
    }

}
