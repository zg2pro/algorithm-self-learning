package fr.univtln.ganne882.project2007.gui;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.UnknownHostException;

import javax.swing.JEditorPane;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;

import fr.univtln.ganne882.project2007.algos.Algorithm;

/**
 * This tab in the algos frame contents the main attributes
 * of the given algorithm
 * @author Gregory ANNE
 */
public class DescriptionTab extends JPanel {

	JLabel [] lAttributes;
	JTextArea [] taAttributes;
	JLabel lHTMLDescription;
	JTextArea taHTMLDescription;
	JEditorPane epHTMLDescription;
	JScrollPane spHTMLDescription;
	JPanel pAttributes;
	static Logger logs = Logger.getRootLogger();
	
	/**
	 * Class Constructor
	 * @param theAlgorithm
	 * @param isStudent
	 */
	public DescriptionTab (Algorithm theAlgorithm, boolean isStudent){
	    PropertyConfigurator.configure("log4j.prop");
		lAttributes = new JLabel[4];
		lAttributes[0] = new JLabel(Messages.getString("DescriptionTab.1")); //$NON-NLS-1$
		lAttributes[1] = new JLabel(Messages.getString("DescriptionTab.2")); //$NON-NLS-1$
		lAttributes[2] = new JLabel(Messages.getString("DescriptionTab.3")); //$NON-NLS-1$
		lAttributes[3] = new JLabel(Messages.getString("DescriptionTab.4")); //$NON-NLS-1$
		taAttributes = new JTextArea[4];
		taAttributes[0] = new JTextArea(theAlgorithm.getName());
		taAttributes[1] = new JTextArea(theAlgorithm.getDomain_());
		taAttributes[2] = new JTextArea(theAlgorithm.getType_());
		int lev = theAlgorithm.getIndLevel_();
		if (lev<4) taAttributes[3] = new JTextArea(Integer.toString(lev));
		else taAttributes[3] = new JTextArea();
		for (int i=0; i<4; i++) if (isStudent) taAttributes[i].setEditable(false);
		lHTMLDescription = new JLabel(Messages.getString("DescriptionTab.5")); //$NON-NLS-1$
		pAttributes = new JPanel();
		pAttributes.setLayout(new GridLayout(0, 2));
		pAttributes.add(lAttributes[0]);
		pAttributes.add(taAttributes[0]);
		pAttributes.add(lAttributes[1]);
		pAttributes.add(taAttributes[1]);
		pAttributes.add(lAttributes[2]);
		pAttributes.add(taAttributes[2]);
		pAttributes.add(lAttributes[3]);
		pAttributes.add(taAttributes[3]);
		pAttributes.add(lHTMLDescription);
		setLayout(new BorderLayout());
		add(pAttributes, BorderLayout.NORTH);
		displayDescription(theAlgorithm.getDescription(), isStudent);
		taAttributes[1].setToolTipText("<html> Domaine : NUM = numerique <br>" +
				"DON = traitement de donnees <br>" +
				"COD = transcodage des bits de donnees <br>" +
				"GEO = geometrie <br>" +
				"GRA = structures en graphes <br>" +
				"si vous n'utilisez pas ces normes, la mise a jour de l'attribut ne se fera pas a l'enregistrement </html>");
		taAttributes[2].setToolTipText("<html> Type : REC = recursif <br>" +
				"ITR = iteratif <br>" +
				"si vous n'utilisez pas ces normes, la mise a jour de l'attribut ne se fera pas a l'enregistrement</html>");
		taAttributes[3].setToolTipText("<html> 0 <= Niveau <= 3 <br>" +
				"si vous donnez une valeur de difficulte en dehors de ces bornes,  la mise a jour de l'attribut ne se fera pas a l'enregistrement<html>");
	}//constructor
	
	/**
	 * if the description contended on the db, it's transformed into
	 * its contents, else the text is normally displayed
	 * @param descriptionFromDB
	 * @param isStudent
	 */
	private void displayDescription (String descriptionFromDB, boolean isStudent){
		if (descriptionFromDB.startsWith("http://")){
			try {
				epHTMLDescription = new JEditorPane();
				spHTMLDescription = new JScrollPane(epHTMLDescription);
				epHTMLDescription.setEditable(false);
				epHTMLDescription.setPage(new URL(descriptionFromDB));
				add (spHTMLDescription);
			} catch (MalformedURLException e) {
				logs.error(e.getMessage());
			}//catch
			catch (UnknownHostException e){
				taHTMLDescription = new JTextArea(Messages.getString("DescriptionTab.7") + //$NON-NLS-1$
						Messages.getString("DescriptionTab.8")+descriptionFromDB); //$NON-NLS-1$
				if (isStudent) taHTMLDescription.setEditable(false);
				add(taHTMLDescription);
				logs.error(e.getMessage());
			}//catch
			catch (IOException e) {
				logs.error(e.getMessage());
			}//catch
		} else {
			taHTMLDescription = new JTextArea(descriptionFromDB);
			if (isStudent) taHTMLDescription.setEditable(false);
			add(taHTMLDescription);
		}//else
	}//displayDescription
	
	public String [] getDescriptionParams(String htmlDescription){
		String [] params = new String[5];
		for (int i =0; i<4; i++){
			params[i] = taAttributes[i].getText();
		}
		try {
			String s  = taHTMLDescription.getText();
			if (s.startsWith(Messages.getString("DescriptionTab.7")) || s.startsWith(Messages.getString("AlgosListing.6")))
				params [4] = htmlDescription;
			else params[4] = s;
		} catch (Exception e){
			logs.debug("taHTMLDescription not allocated => " +
					"a web page is loaded => no change");
			logs.error(e.getMessage());
			params [4] = htmlDescription;
		}//catch
		return params;
	}//getDescriptionParams
	
}//classs
