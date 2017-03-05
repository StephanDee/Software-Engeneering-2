package model;

import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 * @author Florian Breslich, Stephan Dünkel
 */
public class WetterdatenTest {
    int temperatur;
    int helligkeit;
    int regen;
    Wetterdaten instance;
    
    @Before
    public void setUp() {
        temperatur = 5;
        helligkeit = 8;
        regen = 11;
        instance = new Wetterdaten(temperatur, helligkeit, regen);
    }
    
    @Test
    public void testGetTemperatur() {
        int expResult = temperatur;
        int actResult = instance.getTemperatur();
        assertEquals(expResult, actResult);
    }
    
    @Test
    public void testGetHelligkeit() {
        int expResult = helligkeit;
        int actResult = instance.getHelligkeit();
        assertEquals(expResult, actResult);
    }
    
    @Test
    public void testGetRegen() {
        int expResult = regen;
        int actResult = instance.getRegen();
        assertEquals(expResult, actResult);
    }
    
    @Test
    public void testSetTemperatur() {
        testSetTemperatur(Integer.MIN_VALUE);
        testSetTemperatur(-1);
        testSetTemperatur(0);
        testSetTemperatur(1);
        testSetTemperatur(Integer.MAX_VALUE);
    }
    
    private void testSetTemperatur(int x) {
        int wert = x;
        instance.setTemperatur(wert);
        assertTrue(instance.getTemperatur() == wert);
    }
    
    @Test
    public void testSetHelligkeit() {
        testSetHelligkeit(Integer.MIN_VALUE);
        testSetHelligkeit(-1);
        testSetHelligkeit(0);
        testSetHelligkeit(1);
        testSetHelligkeit(Integer.MAX_VALUE);
    }
    
    private void testSetHelligkeit(int x) {
        int wert = x;
        instance.setHelligkeit(wert);
        assertTrue(instance.getHelligkeit() == wert);
    }
    
    @Test
    public void testSetRegen() {
        testSetRegen(Integer.MIN_VALUE);
        testSetRegen(-1);
        testSetRegen(0);
        testSetRegen(1);
        testSetRegen(Integer.MAX_VALUE);
    }
    
    private void testSetRegen(int x) {
        int wert = x;
        instance.setRegen(wert);
        assertTrue(instance.getRegen() == wert);
    }
}