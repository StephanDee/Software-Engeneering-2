package model;

import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 * @author Florian Breslich, Stephan Dünkel
 */
public class BenutzerprofilTest {
    int sitzhoehe;
    int lenkradHoehe;
    int spiegelWinkel;
    Benutzerprofil instance;
    
    @Before
    public void setUp() {
        sitzhoehe = 5;
        lenkradHoehe = 8;
        spiegelWinkel = 11;
        instance = new Benutzerprofil();
    }
    
    @Test
    public void testSitzhoehe() {
        testSitzhoehe(Integer.MIN_VALUE);
        testSitzhoehe(-1);
        testSitzhoehe(0);
        testSitzhoehe(sitzhoehe);
        testSitzhoehe(Integer.MAX_VALUE);
    }
    
    private void testSitzhoehe(int x) {
        instance.setSitzhoehe(x);
        int expResult = x;
        int actResult = instance.getSitzhoehe();
        assertEquals(expResult, actResult);
    }
    
    @Test
    public void testLenkradHoehe() {
        testLenkradHoehe(Integer.MIN_VALUE);
        testLenkradHoehe(-1);
        testLenkradHoehe(0);
        testLenkradHoehe(lenkradHoehe);
        testLenkradHoehe(Integer.MAX_VALUE);
    }
    
    private void testLenkradHoehe(int x) {
        instance.setLenkradHoehe(x);
        int expResult = x;
        int actResult = instance.getLenkradHoehe();
        assertEquals(expResult, actResult);
    }
    
    @Test
    public void testSpiegelWinkel() {
        testSpiegelWinkel(Integer.MIN_VALUE);
        testSpiegelWinkel(-1);
        testSpiegelWinkel(0);
        testSpiegelWinkel(spiegelWinkel);
        testSpiegelWinkel(Integer.MAX_VALUE);
    }
    
    private void testSpiegelWinkel(int x) {
        instance.setSpiegelWinkel(x);
        int expResult = x;
        int actResult = instance.getSpiegelWinkel();
        assertEquals(expResult, actResult);
    }
    
    @Test
    public void testAutomatik() {
        testAutomatik(true);
        testAutomatik(false);
    }
    
    private void testAutomatik(boolean x) {
        instance.setAutomatik(x);
        boolean expResult = x;
        boolean actResult = instance.getAutomatik();
        assertEquals(expResult, actResult);
    }
}