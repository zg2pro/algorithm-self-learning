package fr.univtln.ganne882.project2007.algos;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

import javax.tools.Diagnostic;
import javax.tools.DiagnosticCollector;
import javax.tools.JavaCompiler;
import javax.tools.JavaFileManager;
import javax.tools.JavaFileObject;
import javax.tools.ToolProvider;

import org.apache.log4j.PropertyConfigurator;

/**
 * class extending an algorithm and able to execute it
 * thanks to the compiler and others usefull tools declared in it.
 * @author Gregory ANNE
 * @adaptedfrom a code by Julien Seinturier
 */
public class AlgoDynamicCompiler extends Algorithm {
	
	String code;
	String headerParams;
	String params = "";
	String returningType;
	String function;	
	JavaCompiler compiler;	
	JavaFileManager standardFileManager;
	MemoryFileManager fileManager;
	DiagnosticCollector<JavaFileObject> diagnostics;

	/**
	 * In the gui, the header of the function 
	 * is written as a unique string, so this field has to be read 
	 * in order to know what is the type of the returned value,
	 * what is the name of the method, and what types are the parameters.
	 */
	private void setFunction() {
		int indFirstSpace = 0, indSecondSpace = 0;
		for (int i=0; i<functionHeader.length(); i++){
			if (functionHeader.charAt(i) == ' '){
				if (indFirstSpace == 0) indFirstSpace = i;
			}//if 
			if (functionHeader.charAt(i) == '('){
				indSecondSpace = i-1;
			}//if
			if (functionHeader.charAt(i) == ')') 
				headerParams = functionHeader.substring(indSecondSpace+1, i-1);
		}//for
		returningType = functionHeader.substring(0, indFirstSpace);
		function = functionHeader.substring(indFirstSpace+1, indSecondSpace);
		logs.debug("returning type : "+ returningType);
		logs.debug("function called : "+function);
		logs.debug("params : "+params);
	}//setFunction
	
	private void generateCode (){
		setFunction();
		code =	"public class AlgoToExecute { \n public "
			 + functionHeader + " { \n" + algoProposition
			+ ("\n } \n public " + returningType + 
			" functionNoArgs () { \n" +
			"System.out.println(\"resultat  :: \" +"+function+"("+params+")); \n" +
			"return " + function + "("+params+");" +
			"\n } \n public static void main (String [] args) { \n")
			+ "AlgoToExecute ae = new AlgoToExecute();\n "
			+ "System.out.println(\"result : \" + ae.functionNoArgs());\n"
			+ "}\n }\n";
	}//generatecode

  /**
   * Construction of the compiler within right data given from gui.
   */
  public AlgoDynamicCompiler(String theFunctionHeader, String theAlgoProposition, String theParams){
	  functionHeader = theFunctionHeader;
	  algoProposition = theAlgoProposition;
	  params = theParams;
	  generateCode();
	  //classpath has to be fully configurated
	  compiler = ToolProvider.getSystemJavaCompiler();
	  if (compiler == null){
		  logs.error("No compiler available");
	  }//if
	  logs.info("System Compiler: "+compiler);
	  standardFileManager = compiler.getStandardFileManager(null, null, null);
	  fileManager = new MemoryFileManager(standardFileManager);
	  diagnostics = new DiagnosticCollector<JavaFileObject>(); 
  }//construction
	
  /**
   * Compilation of <code>className</code> 
   * @param compilationUnits set of classes related for the execution
   */
  private void compile(List<JavaFileObject> compilationUnits){
		boolean success = false;
		if (compiler == null){
		  logs.error("no available compilator");
		}//if
		success = compiler.getTask(null, fileManager, diagnostics, null, null, compilationUnits).call();
		for (Diagnostic diagnostic : diagnostics.getDiagnostics()) {
		  logs.error("["+diagnostic.getKind()+"]");
		  logs.error(" "+diagnostic.getMessage(null));
		}//for
		if (success){
		  logs.info("Compilation improved");
		} else {
		  logs.error("compilation failed");
		}//else
  }//compile
	
  /**
   * to get the compiled class
   * @param className
   * @return Class compiled class (.class)
   */
  private Class getCompiledClass(String className){
    try {
    	logs.debug("classname : "+className);
	 	ClassLoader cl = fileManager.getClassLoader(null);
    	return cl.loadClass(className);
	} catch (SecurityException e) {
	  logs.error(e.getMessage());
	} catch (IllegalArgumentException e) {
	  logs.error(e.getMessage());
	} catch (ClassNotFoundException e) {
	  logs.error("unable to find class : "+e.getMessage());
	}//catch
    return null;
  }//getcompiledclass
  
  /**
   * static method to run execution of an algorithm
   * @param theFunctionHeader
   * @param theAlgoProposition
   * @param theParams
   * @return Object (the object returned by the algo)
   */
  public static Object runExecution (String theFunctionHeader, String theAlgoProposition, String theParams) {
	  PropertyConfigurator.configure("log4j.prop");
	  String className = "AlgoToExecute";
	  List<JavaFileObject> compilationUnits = new ArrayList<JavaFileObject>();
	  Class classe    = null;
	  Object instance = null;
	  Method entryPoint = null;
	  Object[] parameters = null;
	  Method[] methods = null;
	  Object returnedValue = "";
	  
	  logs.debug("before construction of instance");
	  AlgoDynamicCompiler compiler = new AlgoDynamicCompiler(theFunctionHeader, theAlgoProposition, theParams);
	  String codeToRun = compiler.getCode();
	  String entryPointName = "functionNoArgs";
	  String interfaceName = "IADC";
	  String functionHeader= compiler.getFunctionHeader();
	  logs.debug("function header : "+compiler.getFunctionHeader());
	  logs.debug("code   :   "+codeToRun);
	  String interfaceCode =   "public interface IADC {"
		        + "  public "+ functionHeader +";"  + "}";
	  compilationUnits.add(new JavaSourceFromString(interfaceName, interfaceCode));       
	  compilationUnits.add(new JavaSourceFromString(className, codeToRun));       
	  compiler.compile(compilationUnits);
	  logs.debug("before getcompiledclass");
	  classe = compiler.getCompiledClass(className);
	  if (classe != null){
	  try {
		  methods = classe.getMethods();
		  logs.debug("seeking searched method "+entryPointName);
		  int i = 0;
		  boolean found = false;
		  while((i < methods.length) && (!found)){
				logs.debug("Method "+i+": "+methods[i]);
			    if (methods[i].getName().equals(entryPointName)){
				      entryPoint = methods[i];
				      found = true;
				      logs.debug(" (right method found)");
			    }/* else {
			      System.out.println("");
			    }*/
			    i++;
		  }//while
		  logs.debug("Creating instance of class "+className);
		  instance = classe.newInstance();
		  if (entryPoint != null){
				Class[] parametersClass = entryPoint.getParameterTypes();
				if (parametersClass.length > 0){
					  parameters = new Object[parametersClass.length];
					  //parameters[0] = "Hello !";
				} else {
					parameters = null;
				}//else
			   returnedValue = entryPoint.invoke(instance, parameters);
		  }//if method found
	    
	    } catch (SecurityException e) {
	    	logs.error(e.getMessage());
	    } catch (IllegalArgumentException e) {
	    	logs.error(e.getMessage());
	    } catch (IllegalAccessException e) {
	    	logs.error(e.getMessage());
	    }  catch (InvocationTargetException e) {
	    	logs.error(e.getMessage());
	    } catch (InstantiationException e) {
	    	logs.error(e.getMessage());
		} //catch
	  }//if(classe)
	  return returnedValue;
  }//runExecution

private String getCode() {
	return code;
}//getCode

}//class
