package fr.univtln.ganne882.project2007.users;



import junit.framework.TestCase;

/**
 * JUnit test cases to test an 
 * as long and as usefull lot of tests as possible
 * @author Gregory ANNE
 */
public class TestsUsers extends TestCase {

	/**
	 * testing wrong password
	 * @throws Exception
	 */
	public void test1() throws Exception {
		CheckID cid;
		assertNotNull(cid = new CheckID("bruno", "wrong"));
		assertFalse(cid.canBeLogged());
	}//test1 


	/**
	 * testing right password
	 * @throws Exception
	 */
	public void test2() throws Exception {
		CheckID cid;
		assertNotNull(cid = new CheckID("bruno", "test"));
		assertTrue(cid.canBeLogged());
		assertFalse(cid.isStudent());
	}//test2


	public static void main (String [] args) throws Exception {
		TestsUsers t = new TestsUsers();
		t.test1();
		t.test2();
	}//main

}//junitalgos

