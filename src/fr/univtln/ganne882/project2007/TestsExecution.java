package fr.univtln.ganne882.project2007;

import junit.framework.Test;
import junit.framework.TestSuite;

/**
 * To perform all the JUnit tests cases of every package.
 * @author Gregory ANNE
 */
public class TestsExecution {

	/**
	 * Declaring TestSuite and performing the tests
	 */
	  public static Test all() {
		  TestSuite all = new TestSuite("Performing all tests  :  ");
		  all.addTestSuite(fr.univtln.ganne882.project2007.algos.TestsAlgos.class);
		  all.addTestSuite(fr.univtln.ganne882.project2007.gui.TestsGUI.class);
		  all.addTestSuite(fr.univtln.ganne882.project2007.users.TestsUsers.class);
		  return all;
	  }//all

	  public static void main(String args[]) {
		  	junit.textui.TestRunner.run(all());
	  }//main

}//class
