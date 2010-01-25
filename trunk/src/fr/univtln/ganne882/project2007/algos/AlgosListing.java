package fr.univtln.ganne882.project2007.algos;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

/**
 * Aim of this class is to list 
 * all stored algorithms in the db
 * @author Gregory ANNE
 */
public class AlgosListing extends Algorithm {

	/**
	 * Main useful function.
	 * returns all the list available in db
	 * @return List<Algorithm>
	 */
	public  static List<Algorithm> run (){
		List<Algorithm> l = new ArrayList<Algorithm>();
		//loading postgres driver
	    try {
	        Class.forName("org.postgresql.Driver");  //$NON-NLS-1$
	    } catch (Exception e) {
	    	logs.error("no driver");
	    }//catch
		try {
			String url = "jdbc:postgresql://localhost/portable";
			Connection conn = DriverManager.getConnection(url, "portable", "gregory");
			Statement st = conn.createStatement();
			String query = "select * from Algorithm order by name";
			logs.debug("query : "+query);
			ResultSet rs = st.executeQuery(query);
			while (rs.next()){
				String [] ifNull = new String [2];
				ifNull[0] = Messages.getString("AlgosListing.6"); //$NON-NLS-1$
				ifNull[1] = Messages.getString("AlgosListing.7"); //$NON-NLS-1$
				if (rs.getString(6) != null) ifNull[0] = rs.getString(6);
				if (rs.getString(7) != null) ifNull[1] = rs.getString(7);
				String comingLevel = rs.getString(5);
				int indComingLevel = 4;
				try {
					indComingLevel = Integer.parseInt(comingLevel);
				} catch (NumberFormatException e) {
					logs.debug("level field is empty");
				}//catch
				Algorithm it = new Algorithm(rs.getBoolean(10) ,rs.getInt(1), rs.getString(2), 
						rs.getString(3), rs.getString(4), indComingLevel, ifNull[0], 
						ifNull[1], rs.getString(8), rs.getString(9));
				l.add(it);
			}//while
			rs.close(); 
			st.close();
			conn.close();
		} catch (SQLException e) {
			logs.error(e.getMessage());
		}//catch
		return l;
	}//doIt
	
	public static List<Algorithm> runWithNameSetAndIsLearnt(String student, char cIsLearnt, char cSet, String step){
		List<Algorithm> l = new ArrayList<Algorithm>();
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
			String query = "select idalgo, name, domain_, type_, level_, description," +
					" codec, functionheader, algoproposition, news from Algorithm a, " +
					"studentknowledge s, isrelatedtoprogram i where s.islearnt = '" + cIsLearnt + 
					"' and a.IDAlgo = s.algorithm and s.student = '" + student + "'  " +
					"and a.IDAlgo = i.algorithm  and i.set_ = '" + cSet + "' and " +
							"i.step = '" + step + "' order by name";
			logs.debug("query : "+query);
			ResultSet rs = st.executeQuery(query);
			while (rs.next()){
				Algorithm it = new Algorithm(rs.getBoolean(10) ,rs.getInt(1), rs.getString(2), 
						rs.getString(3), rs.getString(4), rs.getInt(5), rs.getString(6), 
						rs.getString(7), rs.getString(8), rs.getString(9));
				l.add(it);
			}//while
			rs.close(); 
			st.close();
			conn.close();
		} catch (SQLException e) {
			logs.error(e.getMessage());
		}//catch
		return l;
	}
	
	/**
	 * the algos corresponding to the step where 
	 * the student is in, and which the field 'learnt' or 'tolearn'
	 * @param student
	 * @param step
	 * @param cSet
	 * @return List<Algorithm>
	 */
	public  static List<Algorithm> remainingAlgos (String student, String step, char cSet){
		List<Algorithm> l = new ArrayList<Algorithm>();
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
			String query1 = "(select idalgo, name, domain_, type_, level_, description," +
			" codec, functionheader, algoproposition, news from Algorithm a, isrelatedtoprogram i " +
			"where a.IDAlgo = i.algorithm and i.set_ = '" + cSet + "' and " +
					"i.step = '" + step + "')";
			String query2 = "(select idalgo, name, domain_, type_, level_, description," +
			" codec, functionheader, algoproposition, news from Algorithm a, " +
			"studentknowledge s, isrelatedtoprogram i where " + 
			" a.IDAlgo = s.algorithm and s.student = '" + student + "'  " +
			"and a.IDAlgo = i.algorithm  and i.set_ = '" + cSet + "' and " +
					"i.step = '" + step + "')";
			logs.debug("query : "+query1+" except "+query2+" order by name");
			ResultSet rs = st.executeQuery(query1+" except "+query2+" order by name");
			while (rs.next()){
				Algorithm it = new Algorithm(rs.getBoolean(10) ,rs.getInt(1), rs.getString(2), 
						rs.getString(3), rs.getString(4), rs.getInt(5), rs.getString(6), 
						rs.getString(7), rs.getString(8), rs.getString(9));
				l.add(it);
			}//while
			rs.close(); 
			st.close();
			conn.close();
		} catch (SQLException e) {
			logs.error(e.getMessage());
		}//catch
		return l;
	}//remainingalgos
	
}//class
