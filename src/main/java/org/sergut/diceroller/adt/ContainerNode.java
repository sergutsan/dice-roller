package org.sergut.diceroller.adt;

/**
 * A container node in the abstract dice tree, with other nodes underneath.
 * 
 * @author sergut
 *
 */
public interface ContainerNode extends Node {
    /**
     * Add a new child node to this container node. 
     * 
     * @param newChild the new child node
     */
    void addChild(Node newChild);
    
    /**
     * Remove children that do not add anything to the final output of getResult().
     * 
     * This method is only here for the sake of efficiency. 
     */
    void clean();
}
