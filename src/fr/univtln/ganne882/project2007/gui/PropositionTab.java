package fr.univtln.ganne882.project2007.gui;

import java.awt.BorderLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;

import fr.univtln.ganne882.project2007.algos.AlgoDynamicCompiler;
import fr.univtln.ganne882.project2007.algos.Algorithm;

/**
 * This is the tab where classes can be executed.
 * @author portable
 */
public class PropositionTab extends JPanel {

	static JButton bExecute;
	static JButton bRegister;
	JTextArea taEditableField;
	JTextField tfParams;
	JTextField tfFunctionHeader;
	JTextArea taStdout;
	JPanel pButtons;
	JPanel pWorkingSet;
	JPanel pParams;
	JPanel pStdOut;
	JLabel lParams;
	Font f = new Font("format", Font.TRUETYPE_FONT, 50);
	static Logger logs = Logger.getRootLogger();
	
	/**
	 * Each instance of a propositionTab 
	 * will correspond to a particular algorithm
	 * @param theAlgorithm
	 * @param isStudent
	 */
	public PropositionTab(Algorithm theAlgorithm, boolean isStudent){
		PropertyConfigurator.configure("log4j.prop");
		bExecute = new JButton(Messages.getString("PropositionTab.1")); //$NON-NLS-1$
		if(isStudent) bRegister = new JButton(Messages.getString("PropositionTab.2")); //$NON-NLS-1$
		else bRegister = new JButton(Messages.getString("PropositionTab.3")); //$NON-NLS-1$
		pButtons = new JPanel();
		pButtons.setLayout(new GridLayout(1, 0));
		pButtons.add(bExecute);
		pButtons.add(bRegister);
		pWorkingSet = new JPanel();
		pWorkingSet.setLayout(new BorderLayout());
		tfFunctionHeader = new JTextField(theAlgorithm.getFunctionHeader());
		if (isStudent) tfFunctionHeader.setEditable(false);
		pWorkingSet.add(tfFunctionHeader, BorderLayout.NORTH);
		//pWorkingSet.add(pButtons, BorderLayout.SOUTH);
		pParams = new JPanel();
		pParams.setLayout(new GridLayout(1, 0));
		lParams = new JLabel(Messages.getString("PropositionTab.4") + //$NON-NLS-1$
				Messages.getString("PropositionTab.5")); //$NON-NLS-1$
		pParams.add(lParams);
		tfParams = new JTextField();
		pParams.add(tfParams);
		pWorkingSet.add(pParams, BorderLayout.SOUTH);
		taEditableField  = new JTextArea(theAlgorithm.getAlgoProposition());
		taEditableField.setEditable(true);
		pWorkingSet.add(taEditableField);
		setLayout(new GridLayout(0, 1));
		add(pWorkingSet);
		taStdout = new JTextArea();
		taStdout.setEditable(false);
		pStdOut = new JPanel(new BorderLayout());
		pStdOut.add(pButtons, BorderLayout.NORTH);
		pStdOut.add(taStdout);
		add(pStdOut);
		taStdout.setFont(f);
		taStdout.setToolTipText("Affichage de ce qui est retourne par l'algo");
		bExecute.addActionListener(new ActionListener(){
			public void actionPerformed (ActionEvent evt){
				Object o = AlgoDynamicCompiler.runExecution(
						tfFunctionHeader.getText(), taEditableField.getText(), 
						tfParams.getText());
				taStdout.setText(o.toString());
			}//actionPerfomed
		});
	}//constructor
	
	public String [] getPropositionParams(){
		String [] s = new String[2];
		s[0] = tfFunctionHeader.getText();
		s[1] = taEditableField.getText();
		return s;
	}//getPropositionParams
	

	
}//class
