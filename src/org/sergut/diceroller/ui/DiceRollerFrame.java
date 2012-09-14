package org.sergut.diceroller.ui;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class DiceRollerFrame extends JFrame {

    private static final long serialVersionUID = 12872384749815789L;
    
    public DiceRollerFrame() {
	JPanel mainPane = new JPanel();
	mainPane.setLayout(new GridLayout(0,1));
	
	JButton simpleButton = new JButton("Simple roller");
	simpleButton.setToolTipText("Simple dice roller");
	initButton(simpleButton, new SimpleDieRollFrame());
	mainPane.add(simpleButton);

	JButton simpleMontecarloButton = new JButton("Simple simulator");
	simpleMontecarloButton.setToolTipText("Simple Montecarlo simulator");
	initButton(simpleMontecarloButton, new SimpleMontecarloFrame());
	mainPane.add(simpleMontecarloButton);

	JButton savageButton = new JButton("Savage Worlds");
	savageButton.setToolTipText("Simulator for attack and damage in Savage Worlds");
	initButton(savageButton, new SavageWorldsFrame());
	mainPane.add(savageButton);

	JButton exitButton = new JButton("Exit");
	exitButton.setToolTipText("Exit");
	exitButton.addActionListener(exit);
	mainPane.add(exitButton);
	this.add(mainPane);
	this.pack();
	this.setTitle("Dice Roller");
	this.setResizable(false);
	this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }
    
    private void initButton(JButton button, final JFrame frame) {
	button.addActionListener(new ActionListener() {
	    @Override
	    public void actionPerformed(ActionEvent e) {
		startFrame(frame);
	    }});
    }

    private void startFrame(JFrame frame) {
	this.setVisible(false);
	frame.setVisible(true);
	frame.addWindowListener(new WindowAdapter() {
	    @Override
	    public void windowClosing(WindowEvent e) {
		showMainWindow();
	    }});
    }

    private void showMainWindow() {
	this.setVisible(true);
    }
    
    private ActionListener exit = new ActionListener() {
	@Override
	public void actionPerformed(ActionEvent e) {
	    exit();
	}};

    private void exit() {
	System.exit(0);
    }
    
}
