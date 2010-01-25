package fr.univtln.ganne882.project2007.gui;

import junit.framework.TestCase;
import fr.univtln.ganne882.project2007.algos.Algorithm;
import fr.univtln.ganne882.project2007.users.Student;

/**
 * JUnit test cases to test an 
 * as long and as usefull lot of tests as possible
 * @author Gregory ANNE
 */
public class TestsGUI extends TestCase {

	/**
	 * testing the construction of the teachers interface
	 * @throws Exception
	 */
	public void test1() throws Exception {
		MainFrameTeacher mft;
		assertNotNull(mft = new MainFrameTeacher("test"));
		mft.dispose();
	}//test1 

	/**
	 * testing the construction of the students interface
	 * @throws Exception
	 */
	public void test2() throws Exception {
		MainFrameStudent mfs;
		assertNotNull(mfs = new MainFrameStudent("test", new Student("ganne882")));
		mfs.dispose();
	}//test1 
	
	/**
	 * testing the construction of the algorithms interface
	 * accessible both from teachers and students
	 * @throws Exception
	 */
	public void test3() throws Exception {
		AlgosFrame af;
		assertNotNull(af = new AlgosFrame(new Algorithm(), false));
		af.dispose();
	}//test1 

/*
	public static void main (String [] args) throws Exception {
		TestsGUI t = new TestsGUI();
		t.test1();
		t.test2();
		t.test3();
	}//main
*/
}//junitalgos

