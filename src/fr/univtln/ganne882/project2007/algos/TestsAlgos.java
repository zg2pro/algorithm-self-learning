package fr.univtln.ganne882.project2007.algos;

import junit.framework.TestCase;

/**
 * JUnit test cases to test an 
 * as long and as usefull lot of tests as possible
 * @author Gregory ANNE
 */
public class TestsAlgos extends TestCase {

	/**
	 * testing the construction of new algorithms
	 * @throws Exception
	 */
	public void test1() throws Exception {
		//test 1.1
		assertNotNull(new Algorithm());
		//test 1.2
		assertNotNull(new Algorithm(false, -1, "'Chercher une valeur dans un tableau'",
					"'NUM'", "'ITR'", 0,
				"'http://zanotti.univ-tln.fr/ALGORITHMIQUE/CHERCHER-IDX.html'", 
				 "'http://zanotti.univ-tln.fr/ALGORITHMIQUE/solutions/chercher.c'", 
				 "'int searchValue (int [] tab, int val)'", "'{" + 
				 		"for (int i = 0; i<tab.length; i++) if(tab[i]==val) return val;" + 
				 		"return -1;" + "}'"));

	}//test1 
	
	/**
	 * testing the dynamic compilation of an algorithm and its
	 * corresponding java code.
	 * @throws Exception
	 */
	public void test2() throws Exception {
			Object o = AlgoDynamicCompiler.runExecution("int mult (int a, int b)", 
					"return (a*b);", "2, 3");
			System.out.println("object : 2 x 3 = "+ o);
			//so this is useless : 
			int a = ((Integer)o).intValue();
			System.out.println("integer : 2 x 3 = "+ a);
	}//test2
	
	/**
	 * testing the dynamic compilation of an algorithm and its
	 * corresponding java code. (second test)
	 * @throws Exception
	 */
	public void test3() throws Exception {
			Object o = AlgoDynamicCompiler.runExecution("String displaySomething (int a)", 
					"if (a<4) return \"test OK\"; else return \"test messed up\";", "2");
			System.out.println("result for second junit test  = "+ o);
	}//test3
/*
	public static void main (String [] args) throws Exception {
		TestsAlgos t = new TestsAlgos();
		t.test1();
		t.test2();
		t.test3();
	}//main
*/
}//junitalgos
