package fr.univtln.ganne882.project2007.gui;

import java.awt.Container;
import java.awt.Toolkit;

import javax.swing.JFrame;
import javax.swing.JTabbedPane;
import javax.swing.WindowConstants;

import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;

/**
 * When logged, this is the interface  a teacher directly accesses to
 * @author  Gregory ANNE
 */
public class MainFrameTeacher extends JFrame {

	JTabbedPane mainFrameTabs;
	Container c;
	AlgosTab teachersMainFrameAlgosTab;
	StudentsTab teachersMainFrameStudentsTab;
	static Logger logs = Logger.getRootLogger();
	
	/**
	 * class constructor : parameter s is to set window title
	 * @param s
	 */
	public MainFrameTeacher (String s){
		PropertyConfigurator.configure("log4j.prop");
		setSize(Toolkit.getDefaultToolkit().getScreenSize().width,
				Toolkit.getDefaultToolkit().getScreenSize().height);
		setTitle(s);
		setVisible(true);
		setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
		mainFrameTabs = new JTabbedPane();
		c = this.getContentPane();
		c.add(mainFrameTabs);
		teachersMainFrameAlgosTab = new AlgosTab();
		teachersMainFrameStudentsTab = new StudentsTab();
		mainFrameTabs.addTab(Messages.getString("MainFrameTeacher.1"), null, teachersMainFrameAlgosTab, null); //$NON-NLS-1$
		mainFrameTabs.addTab(Messages.getString("MainFrameTeacher.2"), null, teachersMainFrameStudentsTab, null); //$NON-NLS-1$
	}//constructor

}//class
