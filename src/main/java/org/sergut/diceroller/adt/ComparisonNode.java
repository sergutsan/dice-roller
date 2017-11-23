package org.sergut.diceroller.adt;

import java.util.ArrayList;
import java.util.List;

public class ComparisonNode implements ContainerNode {

    public enum Choosing {
	BEST,
	WORST
    }
    
    private List<Node> children;

    /**
     * True for choosing the best result from children, false 
     * to choose the worst. 
     */
    private boolean choosingBest = true;
    
    public ComparisonNode(Choosing choosing) {
	this.choosingBest = choosing == Choosing.BEST ? true : false;
	children = new ArrayList<Node>();
    }
    
    public ComparisonNode() {
	this(Choosing.BEST);
    }
    
    @Override
    public synchronized int getResult() {
	int result = Integer.MIN_VALUE;
	if (choosingBest) {
	    for (Node child : children) {
		int newResult = child.getResult(); 
		result = newResult > result ? newResult : result;
	    }
	} else  {
	    result = Integer.MAX_VALUE; 
	    for (Node child : children) {
		int newResult = child.getResult(); 
		result = newResult < result ? newResult : result;
	    }	    
	}
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
	String result = "b[";
	for (Node child : children) {
	    result += child.toString() + ",";
	}
	result = result.substring(0, result.length()-1); // remove trailing +
	return result + "]";
    }

}
