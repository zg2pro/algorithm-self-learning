package fr.univtln.ganne882.project2007.gui;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.WindowConstants;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;

import fr.univtln.ganne882.project2007.algos.Algorithm;
import fr.univtln.ganne882.project2007.algos.AlgosListing;
import fr.univtln.ganne882.project2007.algos.StudentKnowledge;
import fr.univtln.ganne882.project2007.users.Student;

/**
 * When logged, this is the first frame
 * a student will be able to see
 * @author Gregory ANNE
 */
public class MainFrameStudent extends JFrame implements ListSelectionListener {

	//tabs will be case 0 for mandatory, 1 for advised, 2 for dummy
	JLabel [] lMAD;
	JLabel [] lIsLearnt;
	//second dimension will be case 0 for toLearn and 1 for learnt
	JList [][] lstMAD;
	DefaultListModel [][] lstMADContents;
	JButton[] bFromLeftToRight;
	JButton[] bFromRightToLeft;
	JButton[] bLookAtOrPropose;
	JPanel[] pMAD;
	JPanel[] pMADCenter;
	JPanel[] pCenterMADCenter;
	JPanel pMain;
	JPanel pIsLearnt;
	String student;
	static Logger logs = Logger.getRootLogger();

	/**
	 * class constructor
	 * @param frameTitle, Student
	 */
	public MainFrameStudent (String frameTitle, Student theStudent){
		PropertyConfigurator.configure("log4j.prop");
		student = theStudent.getLogin();
		setSize(Toolkit.getDefaultToolkit().getScreenSize().width,
				Toolkit.getDefaultToolkit().getScreenSize().height);
		setTitle(frameTitle);
		setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
		pMain = new JPanel();
		pIsLearnt = new JPanel();
		pMain.setLayout(new GridLayout(3,0));
		pIsLearnt.setLayout(new GridLayout(1, 0));
		setLayout(new BorderLayout());
		lMAD = new JLabel[3];
		lMAD[0] = new JLabel(Messages.getString("MainFrameStudent.1")); //$NON-NLS-1$
		lMAD[1] = new JLabel(Messages.getString("MainFrameStudent.2")); //$NON-NLS-1$
		lMAD[2] = new JLabel(Messages.getString("MainFrameStudent.3")); //$NON-NLS-1$
		lIsLearnt = new JLabel[2];
		lIsLearnt[0] = new JLabel(Messages.getString("MainFrameStudent.4")); //$NON-NLS-1$
		lIsLearnt[1] = new JLabel(Messages.getString("MainFrameStudent.5")); //$NON-NLS-1$
		pIsLearnt.add(lIsLearnt[0]);
		pIsLearnt.add(new JLabel(""));
		pIsLearnt.add(lIsLearnt[1]);
		lstMAD = new JList[3][2];
		lstMADContents = new DefaultListModel[3][2];
		bFromLeftToRight = new JButton[3];
		bFromRightToLeft = new JButton[3];
		bLookAtOrPropose = new JButton[3];
		for (int i = 0; i<3; i++){
			bFromLeftToRight[i] = new JButton(">");
			bFromRightToLeft[i] = new JButton("<");
			bLookAtOrPropose[i] = new JButton(Messages.getString("MainFrameStudent.9")); //$NON-NLS-1$
		}//for i
		pMAD = new JPanel[3];
		pMADCenter = new JPanel[3];
		pCenterMADCenter = new JPanel[3];
		for (int i = 0; i<3; i++) {
			pCenterMADCenter[i] = new JPanel();
			pCenterMADCenter[i].setLayout(new GridLayout(0, 1));
			pCenterMADCenter[i].add(bFromLeftToRight[i]);
			pCenterMADCenter[i].add(bFromRightToLeft[i]);
			pCenterMADCenter[i].add(bLookAtOrPropose[i]);
			pMADCenter[i] = new JPanel();
			pMADCenter[i].setLayout(new GridLayout(1, 0));
			for (int j=0; j<2; j++) {
				lstMADContents[i][j] = new DefaultListModel();
				lstMAD[i][j] = new JList(lstMADContents[i][j]);
				char cSet, cIsLearnt;
				switch (i) {
					case 0 : cSet = 'm'; break;
					case 1 : cSet = 'a'; break;
					default : cSet = 'd';
				}
				cIsLearnt = (j==0)? 't' : 'l';
				List<Algorithm> l = AlgosListing.runWithNameSetAndIsLearnt(theStudent.getLogin(), cIsLearnt, cSet, theStudent.getStep());
				for (Algorithm it : l)
					lstMADContents[i][j].addElement(it);
			    lstMAD[i][j].addListSelectionListener(this);
				lstMAD[i][j].validate();
			}//for j
			pMADCenter[i].add(lstMAD[i][0]);
			pMADCenter[i].add(pCenterMADCenter[i]);
			pMADCenter[i].add(lstMAD[i][1]);
			pMAD[i] = new JPanel();
			pMAD[i].setLayout(new BorderLayout());
			pMAD[i].add(pMADCenter[i]);
			pMAD[i].add(lMAD[i], BorderLayout.NORTH);
			pMain.add(pMAD[i]);
			add (pIsLearnt, BorderLayout.NORTH);
			add(pMain);
		}//for i
		setVisible(true);
		listeners();
		validate();
	}//constructor
	
	/**
	 * This function links two lists on the same line :
	 * only one element can be selected above the two lists.
	 */
	public void valueChanged(ListSelectionEvent evt) { 
		int indConcernedSet = 0, indConcernedColumn = 0;
		for (int i=0; i<3; i++) for(int j=0; j<2; j++)
				if (lstMAD[i][j] == (JList)evt.getSource()){
					indConcernedSet = i; indConcernedColumn = j;
				}//if
		indConcernedColumn = (indConcernedColumn>0)? indConcernedColumn-1 : indConcernedColumn+1;
		lstMAD[indConcernedSet][indConcernedColumn].clearSelection();
	}//valuechanged

	/**
	 * components listeners
	 */
	private void listeners (){
		for (int i = 0; i<3; i++){
		bFromLeftToRight[i].addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent evt) {
				int indConcernedSet = 0;
				for (int i = 0; i<3; i++)
				if (bFromLeftToRight[i] == (JButton)evt.getSource()) indConcernedSet = i;
			    int ind = lstMAD[indConcernedSet][0].getSelectedIndex();
			    try {
			    	int indAlgo = ((Algorithm)lstMADContents[indConcernedSet][0].elementAt(ind)).getIndAlgo();
			    	StudentKnowledge.switchKnowledge(student, indAlgo, 't');
			    	logs.debug("idalgo to switch : "+indAlgo);
			    	lstMADContents[indConcernedSet][1].addElement(lstMADContents[indConcernedSet][0].elementAt(ind));
				    lstMADContents[indConcernedSet][0].remove(ind);
				    lstMAD[indConcernedSet][0].validate();
				    lstMAD[indConcernedSet][1].validate();
			    } catch (Exception e) {
			    	logs.error(e.getMessage());
			    } //there was no selected value
			}//actionperformed
		});
		bFromRightToLeft[i].addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent evt) {
				int indConcernedSet = 0;
				for (int i = 0; i<3; i++)
					if (bFromRightToLeft[i] == (JButton)evt.getSource()) indConcernedSet = i;
				logs.debug("set : " +indConcernedSet);
			    int ind = lstMAD[indConcernedSet][1].getSelectedIndex();
			    logs.debug("ind : "+ind);
			    try {
			    	int indAlgo = ((Algorithm)lstMADContents[indConcernedSet][1].elementAt(ind)).getIndAlgo();
			    	logs.debug("algo  : "+ indAlgo);
			    	StudentKnowledge.switchKnowledge(student, indAlgo, 'l');
			    	lstMADContents[indConcernedSet][0].addElement(lstMADContents[indConcernedSet][1].elementAt(ind));
				    lstMADContents[indConcernedSet][1].remove(ind);
				    lstMAD[indConcernedSet][0].validate();
				    lstMAD[indConcernedSet][1].validate();
			    } catch (Exception e) {
			    	logs.error(e.getMessage());
			    } //there was no selected value
			}//actionperformed
		});
		bLookAtOrPropose[i].addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent evt) {
				int indConcernedSet = 0, indConcernedColumn=0, 
				indSelectedAlgoInList=0;
				for (int i = 0; i<3; i++)
				if (bLookAtOrPropose[i] == (JButton)evt.getSource()) indConcernedSet = i;
				indSelectedAlgoInList = lstMAD[indConcernedSet][0].getSelectedIndex();
			    if (indSelectedAlgoInList < 0) {
			    	indSelectedAlgoInList = lstMAD[indConcernedSet][1].getSelectedIndex();
			    	indConcernedColumn = 1;
			    }//if
			    Algorithm theAlgorithm = (Algorithm)lstMAD[indConcernedSet][indConcernedColumn].getSelectedValue();
			    AlgosFrame anAlgosFrame = new AlgosFrame(theAlgorithm, true);
			}//actionperformed
		});
		}//for each line
	}//normalizes
	
	
}//class
