package fr.univtln.ganne882.project2007.algos;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;

/**
 * Aim of this class is to fill or modificate m/a/d attributes
 * of the algos
 * @author Gregory ANNE
 *
 */
public class IsRelatedToProgram {

	static Logger logs = Logger.getRootLogger();
	
	/**
	 * This static method is made just to fill the combobox in
	 * the teachers main frame where is indicated the "set" of
	 * this algo relatively to a given algorithm
	 * @param indAlgo
	 * @param step
	 * @return char
	 */	
	public static char run (int indAlgo, String step) {
	    PropertyConfigurator.configure("log4j.prop");
		char c  = 'n';
		//loading postgres driver
	    try {
	        Class.forName("org.postgresql.Driver");  //$NON-NLS-1$
	    } catch (Exception e) {
	        logs.error("no driver"); //$NON-NLS-1$
	    }//catch
		try {
			String url = "jdbc:postgresql://localhost/portable";
			Connection conn = DriverManager.getConnection(url, "portable", "gregory");
			Statement st = conn.createStatement();
			String query = "select set_ from isRelatedToProgram where algorithm = "+
			Integer.toString(indAlgo) + " and step = '" + step + "'";
			logs.debug("query : "+query);
			ResultSet rs = st.executeQuery(query);
			if (rs.next()) //value can be NULL in database
			c = (rs.getString(1)).charAt(0);
			rs.close(); 
			st.close();
			conn.close();
		} catch (SQLException e) {
			logs.error(e.getMessage());
		}//catch
		return c;
	}//doIt
	
	/**
	 * Into his main frame, the teacher is able to change the "set"
	 * of an algo relatively to a step
	 * @param indAlgo
	 * @param step
	 * @param cSet
	 */
	public static void setNewSet(int indAlgo, String step, char cSet){
	    PropertyConfigurator.configure("log4j.prop");
		//loading postgres driver
	    try {
	        Class.forName("org.postgresql.Driver");  //$NON-NLS-1$
	    } catch (Exception e) {
	        logs.error("no driver"); //$NON-NLS-1$
	    }//catch
		try {
			/*
			 * if the data already exists insert doesn't function and
			 * if it doesn't exist then the update doesn't function so
			 * both have to be managed.
			 */
			String url = "jdbc:postgresql://localhost/portable";
			Connection conn = DriverManager.getConnection(url, "portable", "gregory");
			Statement st = conn.createStatement();
			String queryFirstTime = "insert into isRelatedToProgram values ( " +
			Integer.toString(indAlgo) + ", '" + step + "', '" + cSet + "') ";
			String query = "update isRelatedToProgram set set_ = '" + cSet +
			"' where algorithm = "+ Integer.toString(indAlgo) + 
			" and step = '" + step + "' ";
			int indHasHappenedCorrectly = st.executeUpdate(query);
			//returns 1 if query is correct
			if (indHasHappenedCorrectly < 1){
				st.executeUpdate(queryFirstTime);
				logs.debug("query : "+queryFirstTime);
			} else logs.debug("query : "+query);
			st.close();
			conn.close();
		} catch (SQLException e) {
			logs.error(e.getMessage());
		}//catch
	}//setNewSet

	/**
	 * In the students tab of the teachers interface, the teacher can
	 * applicate the new set he has given to a step to students or group
	 * of students.
	 * @param indAlgo
	 * @param name
	 * @param cSet
	 */
	public static void setNewSetToAStudent(int indAlgo, String name, char cSet){
	    PropertyConfigurator.configure("log4j.prop");
		//loading postgres driver
	    try {
	        Class.forName("org.postgresql.Driver");  //$NON-NLS-1$
	    } catch (Exception e) {
	        logs.error("no driver"); //$NON-NLS-1$
	    }//catch
		try {
			String url = "jdbc:postgresql://localhost/portable";
			Connection conn = DriverManager.getConnection(url, "portable", "gregory");
			Statement st = conn.createStatement();
			String query = "insert into studentknowledge values ( '" +
			name + "', " + Integer.toString(indAlgo) + ", '" + cSet + "') ";
			logs.debug("query : "+query);
			st.executeUpdate(query);
			//returns 1 if query is correct
			st.close();
			conn.close();
		} catch (SQLException e) {
			logs.error(e.getMessage());
		}//catch
	}//setNewSet
	
}//class
