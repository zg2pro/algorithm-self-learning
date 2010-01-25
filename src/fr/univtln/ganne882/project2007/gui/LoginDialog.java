package fr.univtln.ganne882.project2007.gui;

import java.awt.Color;
import java.awt.Container;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.Panel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.WindowConstants;

import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;
 
/**
 * This is the first class to be instancied : the user first gives his login and password and then he is recognized as a teacher or a student
 * @author  Gregory ANNE
 */
public class LoginDialog extends JFrame { 

	private String userID;
	private String userPWD;
	JLabel lUser;
	JLabel lPassword;
    static JTextField tfUser;
	static JPasswordField pfPassword;
	static JButton bOk;
	static JButton bQuit;
	Container container;
    Panel pLogin;
    Panel pPassword;
    Panel pButtons;
	static Logger logs = Logger.getRootLogger();

    /**
     * class constructor
     */
    public LoginDialog() {
 		super(Messages.getString("LoginDialog.0")); //$NON-NLS-1$
    	PropertyConfigurator.configure("log4j.prop");
	    logs.debug("launching here resource bundle");
	    pLogin = new Panel(new GridLayout(0,1));
	    pPassword = new Panel(new GridLayout(0,1));
	    pButtons = new Panel(new FlowLayout());
	    container = this.getContentPane();
	    container.setLayout(new GridLayout(0,1));
	    lUser= new JLabel(Messages.getString("LoginDialog.2")); //$NON-NLS-1$
	    lUser.setForeground(Color.BLUE);
	    lUser.setBounds(10,270,80,20);
	    tfUser= new JTextField();
	    tfUser.setBounds(100,270,120,20);
	    lPassword= new JLabel(Messages.getString("LoginDialog.3")); //$NON-NLS-1$
	    lPassword.setForeground(Color.BLUE);
	    lPassword.setBounds(10,300,80,20);
	    pfPassword=new JPasswordField();
	    pfPassword.setBounds(100,300,120,20);
	    bOk=new JButton(Messages.getString("LoginDialog.4")); //$NON-NLS-1$
	    bOk.setBackground(Color.RED);
	    bOk.setForeground(Color.YELLOW);
	    bOk.setBounds(40,410,80,20);
	    bQuit = new JButton(Messages.getString("LoginDialog.5")); //$NON-NLS-1$
	    bQuit.setBackground(Color.RED);
	    bQuit.setForeground(Color.YELLOW);
	    bQuit.setBounds(150,410,80,20);
 		pLogin.add(lUser);
		pLogin.add(tfUser);
		pPassword.add(lPassword);
		pPassword.add(pfPassword);
		pButtons.add(bOk);
		pButtons.add(bQuit);
		container.add(pLogin);
		container.add(pPassword);
		container.add(pButtons);

		setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
		setSize(300,200);
		setResizable(true);
		setLocation(500,150);
		setVisible(true);
	    
		//only one listener here, quit button can be completely independant
		//from the orthers operation
		bQuit.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				if(JOptionPane.showConfirmDialog(null,
	                 Messages.getString("LoginDialog.6"), Messages.getString("LoginDialog.7"), //$NON-NLS-1$ //$NON-NLS-2$
	                  JOptionPane.YES_NO_OPTION,
	                  JOptionPane.WARNING_MESSAGE)== JOptionPane.YES_OPTION) {
						logs.info("user rejected identification, program quit");
	                     System.exit(0);
	            }//if YESOPTION
			}//actionperformed
		});
		
	}// end constructor

	
	/**
	 * @return  the userID
	 * @uml.property  name="userID"
	 */
	public String getUserID() {
		userID= tfUser.getText();
		return userID;
	}//getuserID
	
	/**
	 * @return  the userPWD
	 * @uml.property  name="userPWD"
	 */
	public String getUserPWD() {
        char[] pass = pfPassword.getPassword();
        userPWD = new String(pass);
		return userPWD;
	}//getpassword
	
 
} //end class LoginDialog
