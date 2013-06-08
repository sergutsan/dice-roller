package org.sergut.diceroller.ui;

import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import org.sergut.diceroller.DiceRoller;
import org.sergut.diceroller.IllegalDiceExpressionException;
import org.sergut.diceroller.montecarlo.DiceResult;
import org.sergut.diceroller.montecarlo.MontecarloSimulator;

public class SimpleMontecarloFrame extends JFrame {

    private static final long serialVersionUID = 12872389749817789L;
    
    private static final int INITIAL_MAX_ROLLS = 1000000;
    private JLabel iterationsLabel = new JLabel("Rolls");
    private JTextField iterationsField = new JTextField("  " + INITIAL_MAX_ROLLS);

    private JButton calculateButton = new JButton("Calculate!");

    private JLabel diceLabel = new JLabel("Dice expressions: ");
    private JTextField diceField = new JTextField(10);
    private JTextField targetField = new JTextField(10);

    private final String[] operators = {">=", ">", "<=", "<", "="};

    private JComboBox<String> operatorCombobox = new JComboBox<String>(operators);
    
    public SimpleMontecarloFrame() {
	JPanel dicePane = new JPanel();
	dicePane.setLayout(new FlowLayout());
	dicePane.add(diceLabel);
	dicePane.add(diceField);
	dicePane.add(operatorCombobox);
	dicePane.add(targetField);
	
	JPanel iterationsPane = new JPanel();
	iterationsPane.setLayout(new FlowLayout());
	iterationsPane.add(iterationsLabel);
	iterationsPane.add(iterationsField);	

	JPanel mainPane = new JPanel();
	mainPane.setLayout(new GridLayout(0,1));
	mainPane.add(dicePane);
	mainPane.add(iterationsPane);
	mainPane.add(calculateButton);

	setButtonBehaviours();

	this.add(mainPane);
	this.pack();
	this.setLocation(100, 100);
	this.setResizable(false);
	this.setTitle("Simple dice simulator");
    }
    
    private void setButtonBehaviours() {
	calculateButton.addActionListener(new ActionListener() {
	    @Override
	    public void actionPerformed(ActionEvent e) {
		resetEmptyFields();
		simulateDice();
	    }});
    }

    protected void resetEmptyFields() {
	if ("".equals(diceField.getText().trim())) {
	    diceField.setText("0");
	}
	if ("".equals(targetField.getText().trim())) {
	    targetField.setText("0");
	}
    }

    private void simulateDice() {
	try {
	    String testDice = diceField.getText().trim();
	    String goalDice = targetField.getText().trim();
	    int maxRolls = Integer.parseInt(iterationsField.getText().trim());
	    String operator = (String) operatorCombobox.getSelectedItem();
	    MontecarloSimulator simulator = MontecarloSimulator.getInstance();
	    DiceResult result = simulator.simulateDice(testDice, operator, goalDice, maxRolls);
	    showResultsForExtra(result.successRolls, result.totalRolls);
	} catch (NumberFormatException ex) {
	    String s = "There is an error with one of the numbers";
	    ex.printStackTrace();
	    JOptionPane.showMessageDialog(this, s, "Invalid number", JOptionPane.ERROR_MESSAGE);
	} catch (IllegalDiceExpressionException ex) {
	    String s = "Invalid expression: " + ex.getExpression();
	    ex.printStackTrace();
	    JOptionPane.showMessageDialog(this, s, "Invalid expression", JOptionPane.ERROR_MESSAGE);
	}
    }

    private void showResultsForExtra(int success, int maxRolls) {
	int ratio = DiceRoller.getSimpleRate(success, maxRolls);
	String s = "Success ratio: " + ratio + "%";
	JOptionPane.showMessageDialog(this, s, "Result", JOptionPane.INFORMATION_MESSAGE);
    }
    
}
