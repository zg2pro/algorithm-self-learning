package fr.univtln.ganne882.project2007.gui;

import java.awt.Container;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

import javax.swing.JEditorPane;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTextArea;
import javax.swing.WindowConstants;

import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;

import fr.univtln.ganne882.project2007.algos.Algorithm;

/**
 * This Frame is accessible from both teachers and students,  but teachers have more privileges. This is the main interface permitting to manage algorithms by all their caracteristics, one of its tab even allows user to execute javacode directly.
 * @author  Gregory ANNE
 */
public class AlgosFrame extends JFrame {
	
	static Logger logs = Logger.getRootLogger();

	//this component presents several 
	//panels on respective tabs
	JTabbedPane mainFrameTabs;
	Container c;
	DescriptionTab algosFrameDescriptionTab;
	//the middle tab contents only one component, 
	//thus it will be managed directly from this class,
	JPanel algosFrameCodeCTab;
	JTextArea taCodeC;
	JEditorPane epCodeC;
	JScrollPane spCodeC;
	PropositionTab algosFramePropositionTab;
	Algorithm frameAlgorithm;
	/**
	 * Construction of the frame
	 * @param anAlgorithm, isStudent
	 * If anAlgorithm.getLevel == 4 => new algo
	 */
	public AlgosFrame (Algorithm anAlgorithm, boolean isStudent){
	    PropertyConfigurator.configure("log4j.prop");
	    frameAlgorithm = new Algorithm(isStudent, anAlgorithm.getIndAlgo(),
	    		anAlgorithm.getName(), anAlgorithm.getDomain_(), anAlgorithm.getType_(),
	    		anAlgorithm.getIndLevel_(), anAlgorithm.getDescription(),
	    		anAlgorithm.getCodeC(), anAlgorithm.getFunctionHeader(), 
	    		anAlgorithm.getAlgoProposition());
		setSize(Toolkit.getDefaultToolkit().getScreenSize().width,
				Toolkit.getDefaultToolkit().getScreenSize().height);
		setTitle(Messages.getString("AlgosFrame.1")+anAlgorithm); //$NON-NLS-1$
		setVisible(true);
		setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
		mainFrameTabs = new JTabbedPane();
		c = this.getContentPane();
		c.add(mainFrameTabs);
		algosFrameDescriptionTab = new DescriptionTab(anAlgorithm, isStudent);
		algosFrameCodeCTab = new JPanel();
		displayCodeC (anAlgorithm.getCodeC(), isStudent);
		algosFramePropositionTab = new PropositionTab(anAlgorithm, isStudent);
		mainFrameTabs.addTab(Messages.getString("AlgosFrame.2"), null, algosFrameDescriptionTab, null); //$NON-NLS-1$
		mainFrameTabs.addTab(Messages.getString("AlgosFrame.3"), null, algosFrameCodeCTab, null); //$NON-NLS-1$
		mainFrameTabs.addTab(Messages.getString("AlgosFrame.4"), null, algosFramePropositionTab, null);		 //$NON-NLS-1$
		listeners();
	}//Constructor
	
	/**
	 * if the data contended in the db is an URL 
	 * it's then read has it got te be, if not, it's read normally
	 * @param codeCInDB
	 * @param isStudent
	 */
	private void displayCodeC (String codeCInDB, boolean isStudent){
		if (codeCInDB.startsWith("http://")){
			try {
				epCodeC = new JEditorPane();
				spCodeC = new JScrollPane(epCodeC);
				epCodeC.setEditable(false);
				epCodeC.setPage(new URL(codeCInDB));
				algosFrameCodeCTab.add(spCodeC);
			} catch (MalformedURLException e) {
				logs.error(e.getMessage());
			}//catch
			catch (FileNotFoundException e){
				taCodeC = new JTextArea(Messages.getString("AlgosFrame.6") + //$NON-NLS-1$
						Messages.getString("AlgosFrame.7")); //$NON-NLS-1$
				if (isStudent) taCodeC.setEditable(false);
				algosFrameCodeCTab.add(taCodeC);
			}//catch
			catch (IOException e) {
				taCodeC = new JTextArea(Messages.getString("AlgosFrame.8") + //$NON-NLS-1$
						Messages.getString("AlgosFrame.9")+codeCInDB); //$NON-NLS-1$
				if (isStudent) taCodeC.setEditable(false);
				algosFrameCodeCTab.add(taCodeC);
				logs.error(e.getMessage());
			}//catch
		} else {
			taCodeC = new JTextArea(codeCInDB);
			if (isStudent) taCodeC.setEditable(false);
			algosFrameCodeCTab.add(taCodeC);
		}//else
	}//displayCodeC
	
	private String getCodeC(String htmlCodeC){
		try {
			String s  = taCodeC.getText();
			if ((s.startsWith(Messages.getString("AlgosFrame.6"))) || 
				(s.startsWith(Messages.getString("AlgosFrame.8"))) ||
				s.startsWith(Messages.getString("AlgosListing.7")))
				return htmlCodeC;
			else return s;
		} catch (Exception e){
			logs.debug("taCodeC not allocated => " +
					"a web page is loaded => no change");
			logs.error(e.getMessage());
			return htmlCodeC;
		}//catch
	}//getCodeC
	
	private void listeners (){
		PropositionTab.bRegister.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent evt){
				String [] descriptionParams = algosFrameDescriptionTab.getDescriptionParams(frameAlgorithm.getDescription());
				String [] propositionParams = algosFramePropositionTab.getPropositionParams();
				boolean isModificated = frameAlgorithm.hasBeenRecentlyModificated();
				int indAlgo = frameAlgorithm.getIndAlgo();
				int indLevel = 4;
				try {
					indLevel = Integer.parseInt(descriptionParams[3]);
				} catch (NumberFormatException e) {
					//if unable to resolve this, field is empty
					logs.info("level is not indicated yet");
				} catch (Exception e){
					logs.error(e.getMessage());
				}//catch
				String codeC = getCodeC(frameAlgorithm.getCodeC());
				frameAlgorithm = null;
				/*for (int i=0; i<4; i++) {
					if (descriptionParams[i]==null) descriptionParams[i]="";
					if (i<2 && propositionParams[i]==null) propositionParams[i]="";
				}//for
				if (codeC == null) codeC="";*/
				frameAlgorithm = new Algorithm(isModificated, indAlgo,  
						descriptionParams[0], descriptionParams[1], descriptionParams[2], 
						indLevel, descriptionParams[4], codeC, 
						propositionParams[0], propositionParams[1]);
				frameAlgorithm.updateAttributes();
			}//actionperformed
		});
	}//normalizes
	
	
}//class
