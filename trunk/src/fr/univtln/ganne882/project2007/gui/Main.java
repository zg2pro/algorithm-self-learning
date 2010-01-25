package fr.univtln.ganne882.project2007.gui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.JComponent;
import javax.swing.JOptionPane;

import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;

import fr.univtln.ganne882.project2007.users.CheckID;
import fr.univtln.ganne882.project2007.users.Student;

/**
 * Standard prefixes normalization for all classes :
 * 
 * Imported Components :
 * JPanel : p
 * JButton : b
 * JTextArea : ta
 * JTextField : tf
 * JEditorPane : ep
 * JScrollPane : sp
 * JLabel : l
 * JComboBox : cb
 * JList : lst
 * ListModel, DefaultListModel : lst and ends with "Contents" suffix
 * JTabbedPane :: ends with "Tabs" suffix
 * JPasswordField : pf
 * Container : named c or container
 * URL : url
 * Exception : named e
 * Event : named evt
 * Connection : named conn
 * Statement : named st
 * Resultset : named rs
 * 
 * 
 * Inner classes :
 * a or an followed by the name of the class 
 * 		when the given instance is part of a group
 * the when the instance is given as a parameter, or else is unique
 * selected when the instance is coming from a swing component
 * 
 * Primitive types
 * int : named i, j, k inside loops, 
 *   but else index, or prefixed ind
 * string : no prefix
 * char : c
 * boolean : is
 * 
 * db stands for database
 * a List (arrayLists) can be named just l
 *  if it's used as a inner variable of a method
 *  and as well its iterator can be called just it or it1, it2 if needed
 * If the List is an attribute for an object it has to get a
 * full name preffixed by listOf
 */

/**
 * Main executor class
 */
public class Main {

	LoginDialog theLoginDialog;
	boolean isStudent; //if not Student, is a Teacher...
	String userID, pwd;
	static Logger logs = Logger.getRootLogger();
	
	/**
	 * Operations to follow by chronological appearance in 
	 * the program
	 */
	public Main(){
		theLoginDialog = new LoginDialog();
	    PropertyConfigurator.configure("log4j.prop");
		logs.info("Program launched");
		theLoginDialog.bOk.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent evt) {
		        if(evt.getSource()== theLoginDialog.bOk) {
		        	OKFunction();
		      }//if bOK
			}//actionPerformed
		});
		KeyListener aKL = (new KeyListener (){
			public void keyPressed(KeyEvent evt) {}
			public void keyReleased(KeyEvent evt) {}
			public void keyTyped(KeyEvent evt) {
				logs.debug("key : "+evt.getKeyChar());
				if(evt.getKeyChar() == '\n') {
		        	OKFunction();
		      }//if enter
			}//keyTyped
		});
		theLoginDialog.tfUser.addKeyListener(aKL);
		theLoginDialog.pfPassword.addKeyListener(aKL);
	}//Main
	
	/**
	 * execution method
	 * @param args
	 */
	public static void main(String[] args) {
	    Main theMain = new Main();
	}//main
	
	private void OKFunction(){
        userID = theLoginDialog.getUserID();
        pwd = theLoginDialog.getUserPWD();
        CheckID aCheckID = new CheckID(userID, pwd);
        if (aCheckID.canBeLogged()) {
        isStudent = aCheckID.isStudent();
        if (isStudent){
        	Student theStudent = new Student(userID); 
    		logs.info(theStudent.toString() + " is being logged on application");
        	MainFrameStudent theMainFrameStudent = new MainFrameStudent(Messages.getString("Main.4"), theStudent); //$NON-NLS-1$
        	theLoginDialog.dispose();
        } else {
    		logs.info(userID + " is being logged on application");
        	MainFrameTeacher theMainFrameTeacher = new MainFrameTeacher(Messages.getString("Main.6")); //$NON-NLS-1$
        	theLoginDialog.dispose();
        }//else
        } else {
    		logs.info("WRONG LOGIN OR PASSWORD...");
        	JOptionPane.showMessageDialog(null, Messages.getString("Main.8")); //$NON-NLS-1$
        }//else
	}

	
}//class
