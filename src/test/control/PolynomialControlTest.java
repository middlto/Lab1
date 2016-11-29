package test.control; 

import control.PolynomialControl;
import entity.Polynomial;
import org.junit.Assert;
import org.junit.Test;
import org.junit.Before; 
import org.junit.After;

import java.lang.reflect.Method;

/** 
* PolynomialControl Tester. 
* 
* @author <Authors name> 
* @since <pre>Ê®Ò»ÔÂ 29, 2016</pre> 
* @version 1.0 
*/ 
public class PolynomialControlTest { 
    Polynomial polynomial;
    PolynomialControl polynomialControl;
@Before
public void before() throws Exception {
    polynomialControl = new PolynomialControl();
    polynomial = new Polynomial();
    polynomial.setEquation("a");
}

@After
public void after() throws Exception { 
} 

/** 
* 
* Method: expression(final String oriEquation) 
* 
*/ 
@Test
public void testExpression() throws Exception { 
//TODO: Test goes here... 
} 

/** 
* 
* Method: simplify(final String command, final String oriEquation) 
* 
*/ 
@Test
public void testSimplify() throws Exception { 
//TODO: Test goes here...
    try {
        Method method = polynomialControl.getClass().getDeclaredMethod("simplify", String.class, String.class);
        method.setAccessible(true);
        Object result = method.invoke(polynomialControl, "!simplifya=1a=1b=2", polynomial.getEquation());
        Assert.assertEquals(null, result);
    }catch (Exception e) {
        e.printStackTrace();
    }
} 

/** 
* 
* Method: derivative(final String command, final String oriEquation) 
* 
*/ 
@Test
public void testDerivative() throws Exception { 
//TODO: Test goes here...
    try {
        Method method = polynomialControl.getClass().getDeclaredMethod("derivative", String.class, String.class);
        method.setAccessible(true);
        Object result = method.invoke(polynomialControl, "!d/da", polynomial.getEquation());
        Assert.assertEquals("1", result);
    }catch (Exception e) {
        e.printStackTrace();
    }
} 


/** 
* 
* Method: getExp(final String key, final String item) 
* 
*/ 
@Test
public void testGetExp() throws Exception { 
//TODO: Test goes here... 
/* 
try { 
   Method method = PolynomialControl.getClass().getMethod("getExp", final.class, final.class); 
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
* Method: makeTable(final String item, final String key, final Cell[] c) 
* 
*/ 
@Test
public void testMakeTable() throws Exception { 
//TODO: Test goes here... 
/* 
try { 
   Method method = PolynomialControl.getClass().getMethod("makeTable", final.class, final.class, final.class); 
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
* Method: getNewEquation(final String key, final Cell[] list, final int numberOfItem) 
* 
*/ 
@Test
public void testGetNewEquation() throws Exception { 
//TODO: Test goes here... 
/* 
try { 
   Method method = PolynomialControl.getClass().getMethod("getNewEquation", final.class, final.class, final.class); 
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
* Method: isExistent(final String str, final String equation) 
* 
*/ 
@Test
public void testIsExistent() throws Exception { 
//TODO: Test goes here... 
/* 
try { 
   Method method = PolynomialControl.getClass().getMethod("isExistent", final.class, final.class); 
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
* Method: replace(final String var, final String number, final String equation) 
* 
*/ 
@Test
public void testReplace() throws Exception { 
//TODO: Test goes here... 
/* 
try { 
   Method method = PolynomialControl.getClass().getMethod("replace", final.class, final.class, final.class); 
   method.setAccessible(true); 
   method.invoke(<Object>, <Parameters>); 
} catch(NoSuchMethodException e) { 
} catch(IllegalAccessException e) { 
} catch(InvocationTargetException e) { 
} 
*/ 
} 

} 
