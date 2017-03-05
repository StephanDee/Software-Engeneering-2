package model;

import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 * @author Florian Breslich, Stephan Dünkel
 */
public class RegulierbarTest {
    
    int min;
    int max;
    Regulierbar instance;
    
    @Before
    public void setUp() {
        min = 0;
        max = 100;
        instance = new Regulierbar(min, max);
    }
    
    @Test
    public void constructor() {
        Regulierbar testObject;
        
        // Integer-Grenzwerte
        testObject = new Regulierbar(Integer.MIN_VALUE, Integer.MAX_VALUE);
        
        // min = max -> sollte moeglich sein.
        testObject = new Regulierbar(0, 0);
        
        // min > max -> sollte scheitern
        try {
            testObject = new Regulierbar(1, 0);
            fail("IllegalArgumentException expected");
        }
        catch (IllegalArgumentException expected) {}
    }
    
    @Test
    public void testGetMinWert() {
        int expResult = min;
        int actResult = instance.getMinWert();
        assertEquals(expResult, actResult);
    }
    
    @Test
    public void testGetMaxWert() {
        int expResult = max;
        int actResult = instance.getMaxWert();
        assertEquals(expResult, actResult);
    }
    
    @Test
    public void testGetWert() {
        
        // Erwartet: Gueltiger Wert zwischen Min- und Max-Wert
        assertNotNull(instance.getWert());
        assertTrue(instance.getMinWert() <= instance.getWert());
        assertTrue(instance.getWert() <= instance.getMaxWert());
    }
    
    @Test
    public void testSetWert() {
        int wert = (min + max) / 2;
        instance.setWert(wert);
        
        // Wert wurde korrekt gesetzt
        assertTrue(instance.getWert() == wert);
        
        // Grenzwerte
        instance.setWert(0);
        instance.setWert(100);
        
        try { // zu kleiner Wert
            instance.setWert(-1);
            fail("IllegalArgumentException expected");
        }
        catch (IllegalArgumentException expected) {}
        
        try { // zu grosser Wert
            instance.setWert(101);
            fail("IllegalArgumentException expected");
        }
        catch (IllegalArgumentException expected) {}
    }
}