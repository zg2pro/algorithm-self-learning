package fr.univtln.ganne882.project2007.algos;

import java.net.URI;

import javax.tools.SimpleJavaFileObject;

/**
 * A file object used to represent source coming from a string.
 * Exemple tir� du site de Sun: http://java.sun.com/javase/6/docs/api/index.html?javax/tools/package-summary.html
 * @author Julien Seinturier
 */
public class JavaSourceFromString extends SimpleJavaFileObject {
    
	/**
	 * Le code source de la classe sous forme de chaine de caract�re
	 */
    final String code;

    /**
     * Construit un nouvel objet repr�sentant le source d'une classe Java stock� dans une chaine 
     * de caract�res.
     * 
     * @param name Le nom de la classe (package compris)
     * @param code Le code source de la classe
     */
    JavaSourceFromString(String name, String code) {
    	
        super(URI.create("string:///" + name.replace('.','/') + Kind.SOURCE.extension),
              Kind.SOURCE);
        
        this.code = code;
    }

    @Override
    public CharSequence getCharContent(boolean ignoreEncodingErrors) {
        return code;
    }
}