package
fr.univtln.ganne882.project2007.users;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;

/**
 * The users management is here. All the users are checked while connecting, thus, managing their privilegies is much easier
 * @author  Gregory ANNE
 */
public class CheckID {

	String login;
	String password;
	boolean isStudent;
	static Logger logs = Logger.getRootLogger();
	
	/**
	 * class constructor
	 * ld stands for LoginDialog
	 */
	public CheckID (String theLogin, String thePassword) {
		 PropertyConfigurator.configure("log4j.prop");
		 //return true if the login/password correspond to db
		login = theLogin;
		password = thePassword;
		//return canBeLogged();
	}//constructor
	
	/**
	 * @return  the login
	 * @uml.property  name="login"
	 */
	public String getLogin() {
		return login;
	}//getLdLogin

	/**
	 * first the instance of this class has to check if the given ID
	 * is really in the DB
	 * @return boolean
	 */
	public boolean canBeLogged (){
		boolean toReturn = false;
	    try {
	        Class.forName("org.postgresql.Driver"); 
	    } catch (Exception e) {
	        logs.error("no driver");
	    }//catch
	    try {
		    String url = "jdbc:postgresql://localhost/portable";
		    Connection conn = DriverManager.getConnection(url, "portable", "gregory");
			Statement st = conn.createStatement();
			String [] queries;
			queries = new String[2];
			queries[1] = "select login, password from student";
			queries[0] = "select login, password from teacher";
			for (int i=0; i<2; i++) {
			logs.debug("query : "+queries[i]);
			ResultSet rs = st.executeQuery(queries[i]);
			while (rs.next()){
				if(login.equals(rs.getString(1)) && password.equals(rs.getString(2))) {
			    	isStudent=(i>0)? true:false;
			    	toReturn = true;
			    }//if
			}//while
			rs.close();
			}//for
			st.close();
			conn.close();
		} catch (SQLException e) {
			logs.error(e.getMessage());
	    }//catch
	return toReturn;
	}//canbelogged
	
	/**
	 * then the program will have to know if the ID checked is from a student or a teacher
	 * @return  boolean
	 * @uml.property  name="isStudent"
	 */
	public boolean isStudent (){
		//returns true if isStudent, false if is a teacher
		return isStudent;
	}//isStudent
	
	
}
