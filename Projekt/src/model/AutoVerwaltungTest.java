package model;

import java.util.Map;
import java.util.HashMap;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 * @author Florian Breslich, Stephan Dünkel
 */
public class AutoVerwaltungTest {
    Map<String, Regulierbar> regulierbar;
    Map<String, Schaltbar> schaltbar;
    AutoVerwaltung instance;
    
    @Before
    public void setUp() {
        regulierbar = new HashMap<>();
        schaltbar = new HashMap<>();
        instance = new AutoVerwaltung(regulierbar, schaltbar);
    }

    @Test
    public void testGetRegulierbar() {
        Map<String, Regulierbar> expResult = this.regulierbar;
        Map<String, Regulierbar> actResult = instance.getRegulierbar();
        
        // Listen verweisen auf dasselbe Objekt
        assertTrue(expResult == actResult);
    }

    @Test
    public void testGetSchaltbar() {
        Map<String, Schaltbar> expResult = this.schaltbar;
        Map<String, Schaltbar> actResult = instance.getSchaltbar();
        
        // Listen verweisen auf dasselbe Objekt
        assertTrue(expResult == actResult);
    }
}