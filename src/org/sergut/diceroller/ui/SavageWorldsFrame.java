package org.sergut.diceroller.ui;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

import javax.swing.BoxLayout;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTextField;

import org.sergut.diceroller.DiceRoller;
import org.sergut.diceroller.IllegalDiceExpressionException;
import org.sergut.diceroller.savageworlds.AttackAim;
import org.sergut.diceroller.savageworlds.SavageWorldsDamageCounter;
import org.sergut.diceroller.savageworlds.SavageWorldsSimulationJob;
import org.sergut.diceroller.savageworlds.SavageWorldsSimulator;

public class SavageWorldsFrame extends JFrame {

    private static final long serialVersionUID = 12872389749812789L;
    
    private static final int SHORT_FIELD_LENGTH = 2;
    private static final int FIELD_LENGTH = 4;
    
    // TODO: develop a general reusable way of creating labels and fields for dice
    //   based on a text description, maybe partially based on roll20 syntax
    
    private final String[] attackDice = {"d4", "d6", "d8", "d10", "d12"};
    private final String[] gangUpBonus = {"+0", "+1", "+2", "+3", "+4"};
    
    private JComboBox attackDiceCombobox = new JComboBox(attackDice);
    private JComboBox gangUpCombobox = new JComboBox(gangUpBonus);

    private JLabel damageD4Label = new JLabel(" d4");
    private JLabel damageD6Label = new JLabel(" d6");
    private JLabel damageD8Label = new JLabel(" d8");
    private JLabel damageD10Label = new JLabel("d10");
    private JLabel damageD12Label = new JLabel("d12");
    private JLabel defenseBonusLabel = new JLabel(" +?");
    private JLabel attackBonusLabel  = new JLabel(" +?");

    private JTextField damageD4Field = new JTextField(FIELD_LENGTH);
    private JTextField damageD6Field = new JTextField(FIELD_LENGTH);
    private JTextField damageD8Field = new JTextField(FIELD_LENGTH);
    private JTextField damageD10Field = new JTextField(FIELD_LENGTH);
    private JTextField damageD12Field = new JTextField(FIELD_LENGTH);
    private JTextField damageBonusField = new JTextField(FIELD_LENGTH);
    private JTextField attackBonusField = new JTextField(SHORT_FIELD_LENGTH);

    private JLabel parryLabel = new JLabel("Parry / Diff.");
    private JTextField parryField = new JTextField(FIELD_LENGTH);
    private JLabel toughnessLabel = new JLabel("Toughness");
    private JLabel toughnessBodyLabel = new JLabel("Body");
    private JLabel toughnessArmLabel = new JLabel("Arm");
    private JLabel toughnessHeadLabel = new JLabel("Head");
    private JTextField toughnessBodyField = new JTextField(SHORT_FIELD_LENGTH);
    private JTextField toughnessArmField  = new JTextField(SHORT_FIELD_LENGTH);
    private JTextField toughnessHeadField = new JTextField(SHORT_FIELD_LENGTH);

    private static final int INITIAL_MAX_ROLLS = 1000000;
    private JLabel iterationsLabel = new JLabel("Rolls");
    private JTextField iterationsField = new JTextField("  " + INITIAL_MAX_ROLLS);

    private JButton calculateButton = new JButton("Calculate!");

    private AttackAimPanel attackerAimPanel = new AttackAimPanel();
    
    private WildCardChoicePanel attackerWildCardPanel = new WildCardChoicePanel("Attacker");
    private WildCardChoicePanel defenderWildCardPanel = new WildCardChoicePanel("Defender");
    
    private CheckPanel defenderShakenPanel = new CheckPanel("Defender already shaken?");
    private CheckPanel attackerWildAttackPanel = new CheckPanel("Wild Attack?");
    private CheckPanel defenderWildAttackPanel = new CheckPanel("Wild-attacked? (-2 parry)");
    private CheckPanel trademarkWeaponPanel = new CheckPanel("Trademark Weapon?");
    private CheckPanel attackerFencerPanel = new CheckPanel("Fencer?");
    private CheckPanel attackerBerserkPanel = new CheckPanel("In berserk state?");
    private CheckPanel frenzyAttackPanel = new CheckPanel("Frenzy attack?");
    private CheckPanel doubleAttackPanel = new CheckPanel("Double attack?");
    private CheckPanel twoFistedPanel = new CheckPanel("Two-fisted?");
    private CheckPanel ambidextrousPanel = new CheckPanel("Ambidextrous?");
    
    public SavageWorldsFrame() {
	setButtonBehaviours();
	JPanel westPane = getWestPanel();
	JPanel eastPane = getEastPanel();	
	JPanel northPane = new JPanel();	
	northPane.setLayout(new GridLayout(1,0));
	northPane.add(westPane);
	northPane.add(eastPane);
	JPanel southPane = getSouthPanel();	
	this.setLayout(new BoxLayout(getContentPane(), BoxLayout.Y_AXIS));
	this.add(northPane);
	this.add(southPane);
	this.pack();
	this.setTitle("Savage Worlds: Kill chances");
	this.setLocation(100, 100);
    }
    
    private JPanel getEastPanel() {
	JPanel result = new JPanel();
	JPanel damagePane = new JPanel();
	damagePane.setLayout(new GridLayout(0,1));
	damagePane.add(new JLabel("Damage dice"));
	damagePane.add(packLabelAndTextField(damageD4Label, damageD4Field), BorderLayout.CENTER);
	damagePane.add(packLabelAndTextField(damageD6Label, damageD6Field), BorderLayout.CENTER);
	damagePane.add(packLabelAndTextField(damageD8Label, damageD8Field), BorderLayout.CENTER);
	damagePane.add(packLabelAndTextField(damageD10Label, damageD10Field), BorderLayout.CENTER);
	damagePane.add(packLabelAndTextField(damageD12Label, damageD12Field), BorderLayout.CENTER);
	damagePane.add(packLabelAndTextField(defenseBonusLabel, damageBonusField), BorderLayout.CENTER);
	JPanel enemyPane = new JPanel();
	enemyPane.setLayout(new GridLayout(0,1));
	JPanel parryPane = new JPanel();
	parryPane.setLayout(new FlowLayout());
	parryPane.add(parryLabel);
	parryPane.add(parryField);
	enemyPane.add(parryPane);
	enemyPane.add(defenderWildAttackPanel);
	JPanel toughnessPane = new JPanel();
	toughnessPane.setLayout(new FlowLayout());
	toughnessPane.add(toughnessLabel);
	linkToughnessTextBoxes();
	toughnessPane.add(toughnessBodyLabel);
	toughnessPane.add(toughnessBodyField);
	toughnessPane.add(toughnessArmLabel);
	toughnessPane.add(toughnessArmField);
	toughnessPane.add(toughnessHeadLabel);
	toughnessPane.add(toughnessHeadField);
	enemyPane.add(toughnessPane);
	enemyPane.add(defenderWildCardPanel);
	enemyPane.add(defenderShakenPanel);
	result.setLayout(new GridLayout(0,1));
	result.add(damagePane);
	result.add(enemyPane);
	return result;
    }
    
    private void linkToughnessTextBoxes() {
    	toughnessBodyField.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent arg0) {
				String currentTxt = toughnessBodyField.getText();
				toughnessArmField.setText(currentTxt);
				toughnessHeadField.setText(currentTxt);
			}});
	}

	private JPanel getWestPanel() {
	JPanel result = new JPanel();
	result.setLayout(new BoxLayout(result, BoxLayout.Y_AXIS));
	JPanel attackDiePanel = new JPanel();
	attackDiePanel.add(new JLabel("Attack die: "));
	attackDiePanel.add(attackDiceCombobox);
	attackDiePanel.add(packLabelAndTextField(attackBonusLabel, attackBonusField));
	result.add(attackDiePanel);
	JPanel gangUpPanel = new JPanel();
	gangUpPanel.add(new JLabel("Gang Up bonus: "));
	gangUpPanel.add(gangUpCombobox);
	result.add(gangUpPanel);
	result.add(attackerAimPanel);
	result.add(attackerWildCardPanel);
	result.add(attackerWildAttackPanel);
	result.add(trademarkWeaponPanel);
	result.add(attackerFencerPanel);
	result.add(frenzyAttackPanel);
	result.add(doubleAttackPanel);
	result.add(twoFistedPanel);
	result.add(ambidextrousPanel);
	result.add(attackerBerserkPanel);
	//   - magic bonus   ____
	//   - other bonus   ____
	return result;
    }

    private JPanel getSouthPanel() {
	JPanel result = new JPanel();
	result.setLayout(new GridLayout(0,1));
	JPanel iterationsPane = new JPanel();
	iterationsPane.setLayout(new FlowLayout());
	iterationsPane.add(iterationsLabel);
	iterationsPane.add(iterationsField);
	result.add(iterationsPane);
	result.add(calculateButton);
	return result;
    }

    private JPanel packLabelAndTextField(JLabel label, JTextField field) {
	JPanel result = new JPanel();
	result.setLayout(new FlowLayout());
	result.add(label);
	result.add(field);
	return result;
    }

    private void setButtonBehaviours() {
	calculateButton.addActionListener(new ActionListener() {
	    @Override
	    public void actionPerformed(ActionEvent e) {
		try {
		    runSimulation();
		} catch (IllegalArgumentException ex) {
		    String s = "Error: " + ex.getMessage();
		    JOptionPane.showMessageDialog(null, s, "Error", JOptionPane.ERROR_MESSAGE);
		}
	    }});
    }

    private void runSimulation() {
	try {
	    SavageWorldsSimulationJob job = new SavageWorldsSimulationJob();
	    job.attackDice = collectAttackDice();
	    job.attackBonus = collectAttackBonus();
	    job.damageDice = collectDamageDice();
	    job.attackerWildCard = attackerWildCardPanel.isWildCard();
	    job.attackAim = attackerAimPanel.getAim();
	    job.attackerTrademarkWeapon = trademarkWeaponPanel.isChecked();
	    job.attackerFencer = attackerFencerPanel.isChecked();
	    job.attackerBerserk = attackerBerserkPanel.isChecked();
	    job.wildAttack = attackerWildAttackPanel.isChecked();
	    job.attackAim = attackerAimPanel.getAim();
	    job.doubleAttack = doubleAttackPanel.isChecked();
	    job.attackerTwoFisted = twoFistedPanel.isChecked();
	    job.attackerAmbidextrous = ambidextrousPanel.isChecked();
	    job.frenzyAttack = frenzyAttackPanel.isChecked();
	    ToughnessTuple toughness = getToughness();
	    job.defenderToughnessBody = toughness.body;
	    job.defenderToughnessArm  = toughness.arm;
	    job.defenderToughnessHead = toughness.head;
	    job.defenderParry = getParry();
	    job.defenderAttackedWild = defenderWildAttackPanel.isChecked();
	    job.defenderShaken = defenderShakenPanel.isChecked();
	    job.defenderWildCard = defenderWildCardPanel.isWildCard();
	    job.maxIterations = getMaxIterations();
	    SavageWorldsDamageCounter damageCounter = SavageWorldsSimulator.getInstance().simulate(job);
	    if (defenderWildCardPanel.isWildCard()) { 
		showResultsForWildCard(damageCounter);
	    } else {
		showResultsForExtra(damageCounter);
	    }
	} catch (IllegalDiceExpressionException ex) {
	    String s = "Invalid expression: " + ex.getExpression();
	    ex.printStackTrace();
	    JOptionPane.showMessageDialog(this, s, "Invalid expression", JOptionPane.ERROR_MESSAGE);
	}
    }

	private void showResultsForWildCard(SavageWorldsDamageCounter damageCounter) {
	int wound1Ratio = DiceRoller.getSimpleRate(damageCounter.wound1, damageCounter.getTotalRolls());
	int wound2Ratio = DiceRoller.getSimpleRate(damageCounter.wound2, damageCounter.getTotalRolls());
	int wound3Ratio = DiceRoller.getSimpleRate(damageCounter.wound3, damageCounter.getTotalRolls());
	int wound4Ratio = DiceRoller.getSimpleRate(damageCounter.wound4m, damageCounter.getTotalRolls());
	int shakenRatio = DiceRoller.getSimpleRate(damageCounter.shaken, damageCounter.getTotalRolls());
	int woundRatio = wound1Ratio + wound2Ratio + wound3Ratio + wound4Ratio; 
	int nothingRatio = 100 - wound1Ratio - wound2Ratio - wound3Ratio - wound4Ratio - shakenRatio; 
	String s = "Shaken  ratio: " + shakenRatio + "% \n"
	    	+  "1 wound  ratio: " + wound1Ratio + "% \n" 
	    	+  "2 wounds ratio: " + wound2Ratio + "% \n" 
	    	+  "3 wounds ratio: " + wound3Ratio + "% \n" 
	    	+  "4+ wounds ratio: " + wound4Ratio + "% \n"
	    	+  "(nothing ratio: " + nothingRatio + "% \n"
	    	+  "wounding ratio: " + woundRatio + "%)"
	    	; 
	System.out.println(damageCounter);
	JOptionPane.showMessageDialog(this, s, "Result", JOptionPane.INFORMATION_MESSAGE);
    }

    private void showResultsForExtra(SavageWorldsDamageCounter damageCounter) {
	int killRatio = DiceRoller.getSimpleRate(damageCounter.getWounds(), damageCounter.getTotalRolls());
	int shakenRatio = DiceRoller.getSimpleRate(damageCounter.shaken, damageCounter.getTotalRolls());
	String s = "Kill ratio: " + killRatio + "%  Shaken ratio: " + shakenRatio + "%";
	System.out.println(damageCounter);
	JOptionPane.showMessageDialog(this, s, "Result", JOptionPane.INFORMATION_MESSAGE);
    }

   private int getParry() {
	int result = parseTextFieldAsInteger(parryField);
	return result;
    }

    private ToughnessTuple getToughness() {
    	ToughnessTuple result = new ToughnessTuple();
    	result.body = parseTextFieldAsInteger(toughnessBodyField);
    	result.head = parseTextFieldAsInteger(toughnessHeadField);
    	result.arm  = parseTextFieldAsInteger(toughnessArmField);
    	return result;
    }

    private int getMaxIterations() {
	int result = parseTextFieldAsInteger(iterationsField);
	return result;
    }

    private String collectAttackDice() {
	String result = (String) attackDiceCombobox.getSelectedItem() + "!";
	return result;
    }

    private int collectAttackBonus() {
    	int result = parseTextFieldAsInteger(attackBonusField);
    	result += Integer.parseInt(((String) gangUpCombobox.getSelectedItem()).substring(1)); // from string "+1" to int 1
    	return result;
	}

    private String collectDamageDice() {
	String result = "";
	int n;
	n = parseTextFieldAsInteger(damageD4Field);
	result += n + "d4!+";
	n = parseTextFieldAsInteger(damageD6Field);
	result += n + "d6!+";
	n = parseTextFieldAsInteger(damageD8Field);
	result += n + "d8!+";
	n = parseTextFieldAsInteger(damageD10Field);
	result += n + "d10!+";
	n = parseTextFieldAsInteger(damageD12Field);
	result += n + "d12!+";
	n = parseTextFieldAsInteger(damageBonusField);
	result += n + "+";
	return result.substring(0, result.length()-1); // Remove trailing "+"
    }

    private int parseTextFieldAsInteger(JTextField field) {
	String content = field.getText().trim();
	if ("".equals(content)) 
	    return 0;
	else
	    return Integer.parseInt(content);
    }
    
    /**
     * A panel to choose whether someone is an extra or a wild card
     */
    private class WildCardChoicePanel extends JPanel {
	private static final long serialVersionUID = 111111L;
	private boolean isWildCard = false;
	
	public WildCardChoicePanel(String title) {
	    this.setLayout(new FlowLayout());
	    JLabel label = new JLabel(title);
	    this.add(label);
	    ButtonGroup buttonGroup = new ButtonGroup();
	    JRadioButton extraButton = new JRadioButton("Extra", true);
	    buttonGroup.add(extraButton);
	    this.add(extraButton);
	    extraButton.addActionListener(new ActionListener() {
		@Override public void actionPerformed(ActionEvent e) {
		    isWildCard = false;
		}});
	    JRadioButton wildCardButton = new JRadioButton("Wild Card", false);
	    buttonGroup.add(wildCardButton);
	    this.add(wildCardButton);
	    wildCardButton.addActionListener(new ActionListener() {
		@Override public void actionPerformed(ActionEvent e) {
		    isWildCard = true;
		}});
	}
	
	public boolean isWildCard() {
	    return isWildCard;
	}
    }

    /**
     * A panel to choose where to attack
     */
    private class AttackAimPanel extends JPanel {
	private static final long serialVersionUID = 1111113L;
	private AttackAim aim = AttackAim.BODY;
	
	public AttackAimPanel() {
	    this.setLayout(new FlowLayout());
	    JLabel label = new JLabel("Attacking: ");
	    this.add(label);
	    ButtonGroup buttonGroup = new ButtonGroup();
	    JRadioButton bodyButton = new JRadioButton("Body", true);
	    buttonGroup.add(bodyButton);
	    this.add(bodyButton);
	    bodyButton.addActionListener(new ActionListener() {
		@Override public void actionPerformed(ActionEvent e) {
		    aim = AttackAim.BODY;
		}});
	    JRadioButton armButton = new JRadioButton("Arm", false);
	    buttonGroup.add(armButton);
	    this.add(armButton);
	    armButton.addActionListener(new ActionListener() {
		@Override public void actionPerformed(ActionEvent e) {
		    aim = AttackAim.ARM;
		}});
	    JRadioButton headButton = new JRadioButton("Head", false);
	    buttonGroup.add(headButton);
	    this.add(headButton);
	    headButton.addActionListener(new ActionListener() {
		@Override public void actionPerformed(ActionEvent e) {
		    aim = AttackAim.HEAD;
		}});
	}

	public AttackAim getAim() {
	    return aim;
	}
    }
    
    /**
     * A panel to check or uncheck some property
     */
    private class CheckPanel extends JPanel {
	private static final long serialVersionUID = 111113L;
	private JCheckBox box = new JCheckBox();
	
	public CheckPanel(String text) {
	    this.setLayout(new FlowLayout());
	    this.add(new JLabel(text));    
	    this.add(box);
	}
	
	public boolean isChecked() {
	    return box.isSelected();
	}
    }
    
    private class ToughnessTuple {
    	int body;
    	int head;
    	int arm;
    }
    
    public static void main(String... args) {
	SavageWorldsFrame frame = new SavageWorldsFrame();
	frame.setVisible(true);
	frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);	
    }
}
