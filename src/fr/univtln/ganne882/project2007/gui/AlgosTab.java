package fr.univtln.ganne882.project2007.gui;

import java.awt.Color;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;

import fr.univtln.ganne882.project2007.algos.Algorithm;
import fr.univtln.ganne882.project2007.algos.AlgosListing;
import fr.univtln.ganne882.project2007.algos.IsRelatedToProgram;

/**
 * This tab is accessible only for teachers
 * It presents all available algorithms, 
 * and an access in order to make modifications
 * @author Gregory ANNE
 */
public class AlgosTab extends JPanel {
	
	JPanel pButtons;
	JPanel pSteps;
	JPanel pRight;
	JButton bAdd;
	JButton bEdit;
	JButton bSteps;
	JLabel [] lSteps;
	JLabel lLegend;
	JComboBox [] cbSteps;
	JList lstAlgos;
	DefaultListModel lstAlgosContents;
	static Logger logs = Logger.getRootLogger();
	Font f = new Font("format", Font.TRUETYPE_FONT, 35);
	Font f2 = new Font("format", Font.TRUETYPE_FONT, 28);
	
	/**
	 * Class constructor
	 */
	public AlgosTab (){
	    PropertyConfigurator.configure("log4j.prop");
	    setLayout(new GridLayout(1,0));
	    pButtons = new JPanel();
	    pButtons.setLayout(new GridLayout(0, 1));
	    bAdd = new JButton(Messages.getString("AlgosTab.1")); //$NON-NLS-1$
	    //bAdd.setPreferredSize(new Dimension(250, 150));
	    bEdit = new JButton(Messages.getString("AlgosTab.2")); //$NON-NLS-1$
	    //bEdit.setPreferredSize(new Dimension(250, 150));
	    lstAlgosContents = new DefaultListModel();
	    lstAlgos = new JList(lstAlgosContents);
	    List<Algorithm> l = AlgosListing.run();
	    for (Algorithm it : l) 
	    	lstAlgosContents.addElement(it);
	    pButtons.add(bAdd);
	    pButtons.add(bEdit);
	    add(lstAlgos);
	    lSteps = new JLabel[5];
	    lSteps [0] = new JLabel(Messages.getString("AlgosTab.3")); //$NON-NLS-1$
	    lSteps [1] = new JLabel(Messages.getString("AlgosTab.4")); //$NON-NLS-1$
	    lSteps [2] = new JLabel(Messages.getString("AlgosTab.5")); //$NON-NLS-1$
	    lSteps [3] = new JLabel(Messages.getString("AlgosTab.6")); //$NON-NLS-1$
	    lSteps [4] = new JLabel(Messages.getString("AlgosTab.7")); //$NON-NLS-1$
	    cbSteps = new JComboBox[5];
	    pSteps = new JPanel();
	    pSteps.setLayout(new GridLayout(0, 2));
	    for (int i=0; i<5; i++){
	    	cbSteps[i] = new JComboBox();
	    	cbSteps[i].addItem(Messages.getString("AlgosTab.8")); //$NON-NLS-1$
	    	cbSteps[i].addItem(Messages.getString("AlgosTab.9")); //$NON-NLS-1$
	    	cbSteps[i].addItem(Messages.getString("AlgosTab.10")); //$NON-NLS-1$
	    	cbSteps[i].addItem(Messages.getString("AlgosTab.11")); //$NON-NLS-1$
	    	lSteps[i].setHorizontalTextPosition(SwingConstants.CENTER);
	    	pSteps.add(lSteps[i]);
	    	pSteps.add(cbSteps[i]);
	    }//for
	    pSteps.add(new JLabel(""));
	    bSteps = new JButton(Messages.getString("AlgosTab.13")); //$NON-NLS-1$
	    pSteps.add(bSteps);
	    pRight = new JPanel();
	    pRight.setLayout(new GridLayout(0, 1));
	    pRight.add(pButtons);
	    pRight.add(pSteps);
	    add(lstAlgos);
	    add(pRight);
	    bAdd.setFont(f);
	    bEdit.setFont(f);
	    listeners();
	}//constructor
	

	
	private void listeners (){
		bAdd.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent evt) {
				AlgosFrame test = new AlgosFrame(new Algorithm(), false);
			}//actionperformed
		});
		bEdit.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent evt) {
				AlgosFrame anAlgosFrame = new AlgosFrame((Algorithm)lstAlgos.getSelectedValue(), false);
			}//actionperformed
		});
		lstAlgos.addListSelectionListener(new ListSelectionListener() {
			public void valueChanged (ListSelectionEvent evt){
				Algorithm selectedAlgorithm = (Algorithm)(((JList)(evt.getSource())).getSelectedValue());
				//first see if the Edit button has to be turned in red
				if (selectedAlgorithm.hasBeenRecentlyModificated()) {
					bEdit.setForeground(Color.RED);
				    bEdit.setFont(f2);
					bEdit.setText(Messages.getString("AlgosTab.14") + //$NON-NLS-1$
							Messages.getString("AlgosTab.15")); //$NON-NLS-1$
				} else {
					bEdit.setForeground(Color.BLACK);
				    bEdit.setFont(f);
					bEdit.setText(Messages.getString("AlgosTab.16")); //$NON-NLS-1$
				}//else
				//then put the actual settings 
				//for the students to learn algos
				for (int i=0; i<5; i++){
					char cSetGivenByDB = IsRelatedToProgram.run(selectedAlgorithm.getIndAlgo(), lSteps[i].getText());
					switch (cSetGivenByDB){
						case 'm' : cbSteps[i].setSelectedIndex(1); break;
						case 'a' : cbSteps[i].setSelectedIndex(2); break;
						case 'd' : cbSteps[i].setSelectedIndex(3); break;
						default : cbSteps[i].setSelectedIndex(0);
					}//switch
					cbSteps[i].validate();
				}//for
			}//valuechanged
		});
		bSteps.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				for (int i=0; i<5; i++){
					int indSelectedIndexInCB = cbSteps[i].getSelectedIndex();
					char charSet;
					switch (indSelectedIndexInCB) {
						case 1 : charSet = 'm'; break;
						case 2 : charSet = 'a'; break;
						case 3 : charSet = 'd'; break;
						default : charSet = 'n';
					}//switch
					Algorithm selectedAlgorithm = (Algorithm)lstAlgos.getSelectedValue();
					IsRelatedToProgram.setNewSet(selectedAlgorithm.getIndAlgo(), lSteps[i].getText(), charSet);
				}//for
			}//actionperformed
		});
	}//normalizes
	
}//class
