package fr.univtln.ganne882.project2007.algos;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;

/**
 * calling this class with be usefull in the students interface,
 * it allows them to indicate whether he has learnt it or not yet
 * @author Gregory ANNE
 */
public class StudentKnowledge {

	static Logger logs = Logger.getRootLogger();
	
	public static void switchKnowledge (String student, int indAlgo, char cIsLearnt){
	    PropertyConfigurator.configure("log4j.prop");
		cIsLearnt = (cIsLearnt == 't')? 'l' : 't';
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
			String query = "update StudentKnowledge set islearnt = '" + cIsLearnt +
			"' where algorithm = "+ Integer.toString(indAlgo) + 
			" and student = '" + student + "' ";
			st.executeUpdate(query);
			logs.debug("query : "+query);
			st.close();
			conn.close();
		} catch (SQLException e) {
			logs.error(e.getMessage());
		}//catch
	}//switchKnowledge
	
}//class
