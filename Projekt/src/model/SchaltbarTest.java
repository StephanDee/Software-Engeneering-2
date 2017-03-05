package model;

import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 * @author Florian Breslich, Stephan Dünkel
 */
public class SchaltbarTest {
    int schaltwert;
    Schaltbar instance;
    
    @Before
    public void setUp() {
        schaltwert = 50;
        instance = new Schaltbar(schaltwert);
    }
    
    @Test
    public void testGetSchaltWert() {
        int expResult = schaltwert;
        int actResult = instance.getSchaltWert();
        assertEquals(expResult, actResult);
    }
    
    @Test
    public void testAnAus() {
        
        // Defaultwert: aus
        assertFalse(instance.getZustand());
        
        // aus -(aus)> aus
        instance.aus();
        assertFalse(instance.getZustand());
        
        // aus -(an)> an
        instance.an();
        assertTrue(instance.getZustand());
        
        // an -(an)> an
        instance.an();
        assertTrue(instance.getZustand());
        
        // an -(aus)> aus
        instance.aus();
        assertFalse(instance.getZustand());
    }
}