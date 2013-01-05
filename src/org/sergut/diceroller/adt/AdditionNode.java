package org.sergut.diceroller.adt;

import java.util.ArrayList;
import java.util.List;



public class AdditionNode implements ContainerNode {

    private List<Node> children;
    
    public AdditionNode() {
	children = new ArrayList<Node>();
    }
    
    @Override
    public synchronized int getResult() {
	int result = 0;
	for (Node child : children)
	    result += child.getResult();
	return result;
    }

    @Override
    public synchronized void addChild(Node newChild) {
	children.add(newChild);	
    }

    @Override
    public synchronized void clean() {
	List<Node> nodesToRemove = new ArrayList<Node>();
	for (Node child : children) {
	    if (child instanceof DieNode) {
		DieNode d = (DieNode) child;
		if (d.getDiceCount() == 0) {
		    nodesToRemove.add(child);
		}
	    }
	}
	for (Node child : nodesToRemove) {
	    children.remove(child);
	}
    }
    
    @Override 
    public String toString() {
	String result = "";
	for (Node child : children) {
	    result += "(" + child.toString() + ")+";
	}
	return result.substring(0, result.length()-1); // remove trailing +
    }

}
