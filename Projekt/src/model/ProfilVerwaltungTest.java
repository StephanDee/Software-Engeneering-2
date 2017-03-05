package model;

import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 * @author Florian Breslich, Stephan Dünkel
 */
public class ProfilVerwaltungTest {
    
    ProfilVerwaltung instance;
    Benutzerprofil testProfil1;
    Benutzerprofil testProfil2;
    String name1;
    String name2;
    
    @Before
    public void setUp() {
        instance = new ProfilVerwaltung();
        testProfil1 = new Benutzerprofil();
        testProfil2 = new Benutzerprofil();
        name1 = "Peter";
        name2 = "Paul";
    }
    
    @Test
    public void testAdd() {
        
        // Profil hinzufuegen
        instance.add(name1, testProfil1);
        
        // Profil unter anderem Namen hinzufuegen
        instance.add(name2, testProfil1);
        
        // Profil unter vorhandenem Namen hinzufuegen -> Exception
        try {
            instance.add(name1, testProfil2);
            fail("IllegalArgumentException expected");
        }
        catch (IllegalArgumentException expected) {
        }
    }
    
    @Test
    public void testDelete() {
        
        // Profil hinzufuegen
        instance.add(name1, testProfil1);

        // Loesche Profil1
        assertTrue(instance.delete(name1));

        // Versuche, das Profil erneut zu loeschen
        assertFalse(instance.delete(name1));
    }
    
    @Test
    public void testSelect() {
        
        // Profil hinzufuegen
        instance.add(name1, testProfil1);
        instance.add(name2, testProfil2);
        instance.select(name1);
        
        // testProfil1 wurde selektiert.
        assertTrue(instance.getAktProfil() == testProfil1);
        
        instance.delete(name2);
        
        // aktuelles Profil1 sollte immer noch testProfil1 sein,
        // da name2 nicht selektiert wurde.
        assertTrue(instance.getAktProfil() == testProfil1);
        
        instance.delete(name1);
        
        // aktuelles Profil1 sollte wieder auf 'null' gesetzt sein,
        // da name1 selektiert wurde.
        assertTrue(instance.getAktProfil() == null);
        
        // Überprüfe, ob IllegalArgumentException geworfen wird,
        // beim Zugriff auf nicht vorhandenes Profil.
        try {
            instance.select("Lisa");
            fail("IllegalArgumentException expected");
        }
        catch (IllegalArgumentException expected) {
        }
    }
}