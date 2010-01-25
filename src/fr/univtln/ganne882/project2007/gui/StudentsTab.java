package fr.univtln.ganne882.project2007.gui;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;

import fr.univtln.ganne882.project2007.algos.Algorithm;
import fr.univtln.ganne882.project2007.algos.AlgosListing;
import fr.univtln.ganne882.project2007.algos.IsRelatedToProgram;
import fr.univtln.ganne882.project2007.users.Student;

/**
 * The teacher can observe the work of his students
 * @author Gregory ANNE
 */
public class StudentsTab extends JPanel implements ListSelectionListener {

	String name;
	String step; int indGrad;
	JLabel lName;
	JLabel lStep;
	JLabel lGrad; 
	JLabel [] lMAD;
	JComboBox cbName;
	JComboBox cbStep;
	JComboBox cbGrad;
	JList [] lstMAD;
	DefaultListModel [] lstMADContents;
	JPanel pStudentsDescription;
	JPanel pAlgosListCenter;
	JPanel pAlgosListTop;
	JPanel pAlgosList;
	JPanel pExport;
	JButton [] bExport;
	int [] indexesOfNoInfoAlgos;
	static Logger logs = Logger.getRootLogger();
	
	
	/**
	 * class constructor
	 */
	public StudentsTab () {
		PropertyConfigurator.configure("log4j.prop");
		lName = new JLabel(Messages.getString("StudentsTab.6"));  //$NON-NLS-1$
		lStep = new JLabel(Messages.getString("StudentsTab.5"));  //$NON-NLS-1$
		lGrad = new JLabel(Messages.getString("StudentsTab.4"));  //$NON-NLS-1$
		lMAD = new JLabel[3];
		lMAD[0] = new JLabel(Messages.getString("StudentsTab.3"));  //$NON-NLS-1$
		lMAD[1] = new JLabel(Messages.getString("StudentsTab.2"));  //$NON-NLS-1$
		lMAD[2] = new JLabel(Messages.getString("StudentsTab.1"));  //$NON-NLS-1$
		cbName = new JComboBox();
		cbName.setEnabled(false);
		cbStep = new JComboBox();
		cbStep.addItem(""); cbStep.addItem(Messages.getString("StudentsTab.8")); //$NON-NLS-2$
		cbStep.addItem(Messages.getString("StudentsTab.9"));cbStep.addItem(Messages.getString("StudentsTab.10")); //$NON-NLS-1$ //$NON-NLS-2$
		cbStep.addItem(Messages.getString("StudentsTab.11")); cbStep.addItem(Messages.getString("StudentsTab.12")); //$NON-NLS-1$ //$NON-NLS-2$
		cbStep.setEnabled(false);
		cbGrad = new JComboBox();
		cbGrad.addItem(""); cbGrad.addItem("2008");
		cbGrad.addItem("2007"); cbGrad.addItem("2006");
		lstMAD = new JList[3];
		lstMADContents = new DefaultListModel[3];
		for (int i=0; i<3; i++) {
			lstMADContents[i] = new DefaultListModel();
			lstMAD[i] = new JList(lstMADContents[i]);
		}//for
		pStudentsDescription = new JPanel();
		pStudentsDescription.setLayout(new GridLayout(0,2));
		pStudentsDescription.add(lName);
		pStudentsDescription.add(cbName);
		pStudentsDescription.add(lStep);
		pStudentsDescription.add(cbStep);
		pStudentsDescription.add(lGrad);
		pStudentsDescription.add(cbGrad);
		pAlgosList = new JPanel();
		pAlgosList.setLayout(new BorderLayout());
		pAlgosListTop = new JPanel();
		pAlgosListCenter = new JPanel();
		pAlgosListTop.setLayout(new GridLayout(1, 0));
		pAlgosListCenter.setLayout(new GridLayout(1,0, 12, 1));
		for (int i=0; i<3; i++)
			pAlgosListTop.add(lMAD[i]);
		for (int i=0; i<3; i++)
			pAlgosListCenter.add(lstMAD[i]);
		pAlgosList.add(pAlgosListTop, BorderLayout.NORTH);
		pAlgosList.add(pAlgosListCenter);
		setLayout(new BorderLayout());
		add(pStudentsDescription, BorderLayout.NORTH);
		add(pAlgosList, BorderLayout.CENTER);
		bExport = new JButton[4];
		bExport[0] = new JButton(Messages.getString("StudentsTab.17") + //$NON-NLS-1$
				Messages.getString("StudentsTab.18")); //$NON-NLS-1$
		bExport[1] = new JButton(Messages.getString("StudentsTab.19") + //$NON-NLS-1$
		Messages.getString("StudentsTab.20")); //$NON-NLS-1$
		bExport[2] = new JButton(Messages.getString("StudentsTab.21") + //$NON-NLS-1$
		Messages.getString("StudentsTab.22")); //$NON-NLS-1$
		bExport[3] = new JButton(Messages.getString("StudentsTab.23") + //$NON-NLS-1$
		Messages.getString("StudentsTab.24")); //$NON-NLS-1$
		pExport  = new JPanel();
		pExport.setLayout(new GridLayout(1, 0));
		for (int i=0; i<4; i++){
			bExport[i].setPreferredSize(new Dimension(250, 100));
			pExport.add(bExport[i]);
		}//for
		indexesOfNoInfoAlgos = new int[3];
		add(pExport, BorderLayout.SOUTH);
		listeners();
	}//constructor

	/**
	 * This function links the three lists :
	 * elements may be selected in only one list meantime.
	 */
	public void valueChanged(ListSelectionEvent evt) { 
		int indColumnWithASelectionInIt = 0;
		for (int i=0; i<3; i++)
				if (lstMAD[i] == (JList)evt.getSource()){
					indColumnWithASelectionInIt = i;
				}//if
		for (int i=0; i<3; i++) if(i!=indColumnWithASelectionInIt)
			lstMAD[i].clearSelection();
	}//valuechanged
	
	
	private void listeners (){
		cbGrad.addActionListener(new ActionListener (){
			public void actionPerformed(ActionEvent evt){
				indGrad = Integer.parseInt((String)cbGrad.getSelectedItem());
				cbStep.setEnabled(true);
			}//actionPerformed
		});
		cbStep.addActionListener(new ActionListener (){
			public void actionPerformed(ActionEvent evt){
				step = (String)cbStep.getSelectedItem();
				List<Student> theStudentsListing = Student.doIt(step, indGrad);
				cbName.removeAllItems();
				cbName.addItem("");
				for (Student s : theStudentsListing)
					cbName.addItem(s);
				cbName.setEnabled(true);
			}//actionPerformed
		});
		cbName.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent evt){
				//I don't know why but the listener is launched even if
				//no choice is made in here.
				try {
				name = ((Student)cbName.getSelectedItem()).getLogin();
				for (int i=0; i<3; i++){
					lstMADContents[i].removeAllElements();
					indexesOfNoInfoAlgos[i]=0;
					fillList(i);
				}
				} catch (Exception e) {
					logs.debug(Messages.getString("StudentsTab.26")); //$NON-NLS-1$
				}
			}//actionperformed
		});
		bExport[0].addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent evt){
				List<Student> lS = Student.doIt(step, indGrad);
				int indConcernedList = 0;
				for (int i = 0; i<3; i++){
					if (!lstMAD[i].isSelectionEmpty()) indConcernedList = i;
				}//for
				logs.debug("indconcernedlist  : "+indConcernedList);
				List<Algorithm> lA = getSelectedListOfAlgos (indConcernedList);
				for (Student it1 : lS)
				for (Algorithm it2 : lA)
					IsRelatedToProgram.setNewSetToAStudent(it2.getIndAlgo(), it1.getLogin(), 'l');
				lstMADContents[indConcernedList].removeAllElements();
				indexesOfNoInfoAlgos[indConcernedList]=0;
				fillList(indConcernedList);
			}//actionPerformed
		});
		bExport[1].addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent evt){
				List<Student> lS = Student.doIt(step, indGrad);
				int indConcernedList = 0;
				for (int i = 0; i<3; i++){
					if (!lstMAD[i].isSelectionEmpty()) indConcernedList = i;
				}//for
				logs.debug("indconcernedlist  : "+indConcernedList);
				List<Algorithm> lA = getSelectedListOfAlgos (indConcernedList);
				for (Student it1 : lS)
				for (Algorithm it2 : lA)
					IsRelatedToProgram.setNewSetToAStudent(it2.getIndAlgo(), it1.getLogin(), 't');
				lstMADContents[indConcernedList].removeAllElements();
				indexesOfNoInfoAlgos[indConcernedList]=0;
				fillList(indConcernedList);
			}//actionPerformed
		});
		bExport[2].addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent evt){
				int indConcernedList = 0;
				for (int i = 0; i<3; i++){
					if (!lstMAD[i].isSelectionEmpty()) indConcernedList = i;
				}//for
				logs.debug("indconcernedlist  : "+indConcernedList);
				List<Algorithm> l = getSelectedListOfAlgos (indConcernedList);
				for (Algorithm it : l)
					IsRelatedToProgram.setNewSetToAStudent(it.getIndAlgo(), name, 'l');
				lstMADContents[indConcernedList].removeAllElements();
				indexesOfNoInfoAlgos[indConcernedList]=0;
				fillList(indConcernedList);
			}//actionPerformed
		});
		bExport[3].addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent evt){
				int indConcernedList = 0;
				for (int i = 0; i<3; i++){
					if (!lstMAD[i].isSelectionEmpty()) indConcernedList = i;
				}//for
				logs.debug("indconcernedlist  : "+indConcernedList);
				List<Algorithm> l = getSelectedListOfAlgos (indConcernedList);
				for (Algorithm it : l)
					IsRelatedToProgram.setNewSetToAStudent(it.getIndAlgo(), name, 't');
				lstMADContents[indConcernedList].removeAllElements();
				indexesOfNoInfoAlgos[indConcernedList]=0;
				fillList(indConcernedList);
			}//actionPerformed
		});
	}//normalizes

	/**
	 * The teacher can proceed to some operations above the algorithms
	 * students have to learn, this function allows him to select some
	 * algos in the list and add them in the students access to the program.
	 * The algos which are already accessible for students will be ignored.
	 * @param indConcernedList
	 * @return List<Algorithm>
	 */
	private List<Algorithm> getSelectedListOfAlgos (int indConcernedList){
		List<Algorithm> l = new ArrayList<Algorithm>();
		int [] indexes = lstMAD[indConcernedList].getSelectedIndices();
		for (int i=0; i<indexes.length; i++){
			logs.debug("selected indices : "+ indexes[i]);
			logs.debug("indOfnoInfoAlgos : "+ indexesOfNoInfoAlgos[indConcernedList]);
			if (indexesOfNoInfoAlgos[indConcernedList]<indexes[i]){
				Algorithm it = (Algorithm)lstMADContents[indConcernedList].getElementAt(indexes[i]);
				l.add(it);
			} else {
				JOptionPane.showMessageDialog(this, Messages.getString("StudentsTab.27") + //$NON-NLS-1$
				Messages.getString("StudentsTab.28") + //$NON-NLS-1$
				Messages.getString("StudentsTab.29")); //$NON-NLS-1$
			}//else
		}//for
		return l;
	}//getSelectedListOfAlgos
	
	/**
	 * This function fills a m, a or d list corresponding to a student and
	 * his current knowledge.
	 * @param i
	 */
	private void fillList (int i){
		char cMAD;
		switch (i) {
			case 0 : cMAD = 'm'; break;
			case 1 : cMAD = 'a'; break;
			default : cMAD = 'd';
		}//switch
		List<Algorithm> l = AlgosListing.runWithNameSetAndIsLearnt(name, 'l', cMAD, step);
		if (!l.isEmpty()) {
			lstMADContents[i].addElement(Messages.getString("StudentsTab.30")); //$NON-NLS-1$
			indexesOfNoInfoAlgos[i]++;
			for (Algorithm it : l){
				indexesOfNoInfoAlgos[i]++;
				lstMADContents[i].addElement(it);
			}//for
		}//if
		l = null;
		l = AlgosListing.runWithNameSetAndIsLearnt(name, 't', cMAD, step);
		if (!l.isEmpty()) {
			indexesOfNoInfoAlgos[i]++;
			lstMADContents[i].addElement(Messages.getString("StudentsTab.31")); //$NON-NLS-1$
			for (Algorithm it : l){
				indexesOfNoInfoAlgos[i]++;
				lstMADContents[i].addElement(it);
			}//for
		}//if
		l = null;
		l = AlgosListing.remainingAlgos(name, step, cMAD);
		logs.debug("list column  " +i+"   number of items  "+ indexesOfNoInfoAlgos[i]); //$NON-NLS-1$ //$NON-NLS-2$
		if (!l.isEmpty()) {
			lstMADContents[i].addElement(Messages.getString("StudentsTab.34")); //$NON-NLS-1$
			indexesOfNoInfoAlgos[i]++;
			for (Algorithm it : l)
				lstMADContents[i].addElement(it);
		}//if
		//this i cannot understand : hurra programmation!!!
		indexesOfNoInfoAlgos[i]--;
	}//fillList
	
}//class
