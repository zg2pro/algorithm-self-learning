package fr.univtln.ganne882.project2007.users;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;

/**
 * Aim of this class is to list  all stored students in the db and manage students attributes
 * @author  Gregory ANNE
 */
public class Student {
	
	String login;
	String name;
	String step;
	static Logger logs = Logger.getRootLogger();
	
	/**
	 * this class describes a student within its attributes :
	 * @param theLogin
	 * @param theName
	 * @param theStep
	 */
	public Student(String theLogin, String theName, String theStep){
		PropertyConfigurator.configure("log4j.prop");
		login = theLogin;
		name = theName;
		step = theStep;
	}//constructor
	public Student(String theLogin){
		PropertyConfigurator.configure("log4j.prop");
		login = theLogin;
		try {
	        Class.forName("org.postgresql.Driver");  //$NON-NLS-1$
	    } catch (Exception e) {
	        logs.error("no driver"); //$NON-NLS-1$
	    }//catch
	    try {
			String url = "jdbc:postgresql://localhost/portable";
			Connection conn = DriverManager.getConnection(url, "portable", "gregory");
			Statement st = conn.createStatement();
			String query = "select a.step, b.name from studiesin a, " +
					"student b where '" + login + "' = a.student" +
							" and a.student = b.login";
			logs.debug("query : "+ query);
			ResultSet rs = st.executeQuery(query);
			//that means that a student is only 
			//in one step meantimme : no dubbled-cursus
			if (rs.next()){
			step = rs.getString(1);
			name = rs.getString(2);
			}//if
			rs.close(); 
			st.close();
			conn.close();
		} catch (SQLException e) {
			logs.error(e.getMessage());
		}//catch
	}//constructor
	
	/**
	 * @Override
	 */
	public String toString() {
		return name;
	}//tostring
	
	/**
	 * Main useful function.
	 * returns all the list available in db
	 * @return List<String>
	 */
	public  static List<Student> doIt (String step, int grad){
		List<Student> l = new ArrayList<Student>();
		//loading postgres driver
	    try {
	        Class.forName("org.postgresql.Driver");  //$NON-NLS-1$
	    } catch (Exception E) {
	        logs.error("no driver"); //$NON-NLS-1$
	    }//catch
		try {
			String url = "jdbc:postgresql://localhost/portable";
			Connection conn = DriverManager.getConnection(url, "portable", "gregory");
			Statement st = conn.createStatement();
			String query = "select * from student a, studiesin b " +
					"where b.step = '" + step +
					"' and b.graduation = " + grad + 
				    " and a.login = b.student " + " order by name";
			logs.debug("query : "+query);
			ResultSet rs = st.executeQuery(query);
			while (rs.next()){
				Student s = new Student(rs.getString(1), rs.getString(2), step);
				l.add(s);
			}//while
			rs.close(); 
			st.close();
			conn.close();
		} catch (SQLException e) {
			logs.error(e.getMessage());
		}//catch
		return l;
	}//doIt

	/**
	 * @return  the login
	 * @uml.property  name="login"
	 */
	public String getLogin() {
		return login;
	}//getlogin
	/**
	 * @return  the step
	 * @uml.property  name="step"
	 */
	public String getStep() {
		return step;
	}//getstep
	
}//class
