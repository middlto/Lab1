package test.main; 

import main.Polynomial;
import org.junit.Assert;
import org.junit.Test;
import org.junit.Before; 
import org.junit.After;

import java.lang.reflect.Method;

/** 
* Polynomial Tester. 
* 
* @author <Authors name> 
* @since <pre>Ê®Ò»ÔÂ 7, 2016</pre> 
* @version 1.0 
*/ 
public class PolynomialTest { 
Polynomial polynomial;
@Before
public void before() throws Exception {
    polynomial = new Polynomial();
    polynomial.setEquation("a");
} 

@After
public void after() throws Exception { 
} 

/** 
* 
* Method: setEquation(String s) 
* 
*/ 
@Test
public void testSetEquation() throws Exception { 
//TODO: Test goes here... 
} 

/** 
* 
* Method: set() 
* 
*/ 
@Test
public void testSet() throws Exception { 
//TODO: Test goes here... 
} 

/** 
* 
* Method: expression() 
* 
*/ 
@Test
public void testExpression() throws Exception { 
//TODO: Test goes here... 
} 

/** 
* 
* Method: simplify(String command) 
* 
*/ 
@Test
public void testSimplify() throws Exception {
    try {
        Method method = polynomial.getClass().getDeclaredMethod("simplify", String.class);
        method.setAccessible(true);
        Object result = method.invoke(polynomial, "!simplifya=1");
        Assert.assertEquals("1",result);
    }catch (Exception e) {
        e.printStackTrace();
    }
} 

/** 
* 
* Method: derivative(String command) 
* 
*/ 
@Test
public void testDerivative() throws Exception {
    try {
        Method method = polynomial.getClass().getDeclaredMethod("derivative", String.class);
        method.setAccessible(true);
        Object result = method.invoke(polynomial, "!d/db");
        Assert.assertEquals(null,result);
    }catch (Exception e) {
        e.printStackTrace();
    }
} 


/** 
* 
* Method: makeTable(String item, String key, cell c[]) 
* 
*/ 
@Test
public void testMakeTable() throws Exception { 
//TODO: Test goes here... 
/* 
try { 
   Method method = Polynomial.getClass().getMethod("makeTable", String.class, String.class, cell.class); 
   method.setAccessible(true); 
   method.invoke(<Object>, <Parameters>); 
} catch(NoSuchMethodException e) { 
} catch(IllegalAccessException e) { 
} catch(InvocationTargetException e) { 
} 
*/ 
} 

/** 
* 
* Method: getNewEquation(String key, cell list[], int numberOfItem) 
* 
*/ 
@Test
public void testGetNewEquation() throws Exception { 
//TODO: Test goes here... 
/* 
try { 
   Method method = Polynomial.getClass().getMethod("getNewEquation", String.class, cell.class, int.class); 
   method.setAccessible(true); 
   method.invoke(<Object>, <Parameters>); 
} catch(NoSuchMethodException e) { 
} catch(IllegalAccessException e) { 
} catch(InvocationTargetException e) { 
} 
*/ 
} 

/** 
* 
* Method: getExp(String key, String item) 
* 
*/ 
@Test
public void testGetExp() throws Exception { 
//TODO: Test goes here... 
/* 
try { 
   Method method = Polynomial.getClass().getMethod("getExp", String.class, String.class); 
   method.setAccessible(true); 
   method.invoke(<Object>, <Parameters>); 
} catch(NoSuchMethodException e) { 
} catch(IllegalAccessException e) { 
} catch(InvocationTargetException e) { 
} 
*/ 
} 

/** 
* 
* Method: isExist(String str, String equation) 
* 
*/ 
@Test
public void testIsExist() throws Exception { 
//TODO: Test goes here... 
/* 
try { 
   Method method = Polynomial.getClass().getMethod("isExist", String.class, String.class); 
   method.setAccessible(true); 
   method.invoke(<Object>, <Parameters>); 
} catch(NoSuchMethodException e) { 
} catch(IllegalAccessException e) { 
} catch(InvocationTargetException e) { 
} 
*/ 
} 

/** 
* 
* Method: replace(String var, String number, String equation) 
* 
*/ 
@Test
public void testReplace() throws Exception { 
//TODO: Test goes here... 
/* 
try { 
   Method method = Polynomial.getClass().getMethod("replace", String.class, String.class, String.class); 
   method.setAccessible(true); 
   method.invoke(<Object>, <Parameters>); 
} catch(NoSuchMethodException e) { 
} catch(IllegalAccessException e) { 
} catch(InvocationTargetException e) { 
} 
*/ 
} 

} 
