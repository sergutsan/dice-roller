package org.sergut.diceroller;

public class IllegalDiceExpressionException extends RuntimeException {

    private static final long serialVersionUID = 100101001L;
    
    private String illegalDiceExpression;

    public IllegalDiceExpressionException(String illegalDiceExpression) {
	super("Illegal dice expression: " + illegalDiceExpression);
	this.illegalDiceExpression = illegalDiceExpression;
    }

    public IllegalDiceExpressionException(String illegalDiceExpression, Throwable cause) {
	super("Illegal dice expression: " + illegalDiceExpression, cause);
	this.illegalDiceExpression = illegalDiceExpression;
    }

    public String getExpression() {
	return this.illegalDiceExpression;
    }
}
