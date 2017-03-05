package systemTest;

import model.*;
import org.junit.Assert;
import org.junit.FixMethodOrder;
import org.junit.Before;
import org.junit.Test;
import org.junit.runners.MethodSorters;

import java.util.Map;
import java.util.HashMap;
import java.util.List;
import java.util.ArrayList;

/**
 * Diese Klasse testet Funktionen des CarControllingUnit mittels konkreten Testfällen.
 */
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class SystemTest extends Assert {

    Benutzerprofil benutzerProfilTester1;
    Benutzerprofil benutzerProfilTester2;
    ProfilVerwaltung profilV;
    Autosteuerer steuerer;
    Wetterdaten wetterDaten;
    Map<String, Regulierbar> regulierbar;
    Map<String, Schaltbar> schaltbar;
    List<Sensor> sensoren;

    @Before
    public void makeUp() {

        // Aufbau Auto
        Regulierbar lenkrad = new Regulierbar(0, 100);
        Regulierbar sitz = new Regulierbar(0, 100);
        Regulierbar spiegel = new Regulierbar(0, 100);
        Schaltbar scheibenwischer = new Schaltbar(50);
        Schaltbar scheinwerfer = new Schaltbar(50);

        regulierbar = new HashMap<>();
        regulierbar.put("lenkrad", lenkrad);
        regulierbar.put("sitz", sitz);
        regulierbar.put("spiegel", spiegel);

        schaltbar = new HashMap<>();
        schaltbar.put("scheibenwischer", scheibenwischer);
        schaltbar.put("scheinwerfer", scheinwerfer);

        AutoVerwaltung autoV = new AutoVerwaltung(regulierbar, schaltbar);

        // Benutzerprofile
        benutzerProfilTester1 = new Benutzerprofil();
        benutzerProfilTester1.setSitzhoehe(50);
        benutzerProfilTester1.setLenkradHoehe(80);
        benutzerProfilTester1.setSpiegelWinkel(70);

        benutzerProfilTester2 = new Benutzerprofil();
        benutzerProfilTester2.setSitzhoehe(40);
        benutzerProfilTester2.setLenkradHoehe(60);
        benutzerProfilTester2.setSpiegelWinkel(80);

        profilV = new ProfilVerwaltung();
        profilV.add("peter", benutzerProfilTester1);
        profilV.add("paul", benutzerProfilTester2);

        // Sensoren
        wetterDaten = new Wetterdaten(50, 50, 50);
        Sensor helligkeit = new HelligkeitSensor(wetterDaten);
        Sensor regen = new RegenSensor(wetterDaten);

        sensoren = new ArrayList<>();
        sensoren.add(helligkeit);
        sensoren.add(regen);

        // Autosteuerung
        steuerer = new Autosteuerer(profilV, autoV, sensoren);

        // Beobachtet Profilverwaltung und Sensoren
        wetterDaten.addObserver(steuerer);
        profilV.addObserver(steuerer);
    }

    @Test
    public void t01_ProfilSelektierenUndLoeschen() throws Exception {

        // Profil in die Verwaltung hinzufügen
        profilV.add("Henry", benutzerProfilTester1);
        profilV.select("Henry");

        // Überprüfe, ob das Profil in die Verwaltung hinzugefügt wurde.
        assertEquals(benutzerProfilTester1, profilV.getAktProfil());

        // Profil in der Verwaltung löschen
        profilV.delete("Henry");

        // Überprüfe, ob das Profil gelöscht wurde.
        assertEquals(null, profilV.getAktProfil());

        // Überprüfe, ob IllegalArgumentException geworfen wird,
        // beim Zugriff auf gelöschtes Profil.
        try {
            profilV.select("Henry");
            fail("IllegalArgumentException expected");
        }
        catch (IllegalArgumentException expected) {
        }
    }

    @Test
    public void t02_KomponentenUpdateBeiProfilUpdate() throws Exception {
        int sitz = 45; int lenkrad = 77; int spiegel = 23;

        // Setze Daten bei Profil 1 (Peter)
        benutzerProfilTester1.setSitzhoehe(sitz);
        benutzerProfilTester1.setLenkradHoehe(lenkrad);
        benutzerProfilTester1.setSpiegelWinkel(spiegel);

        // Profil von Peter != Profil von Paul
        profilV.select("paul");

        assertFalse(steuerer.getEingestellterWert("sitz") == sitz);
        assertFalse(steuerer.getEingestellterWert("lenkrad") == lenkrad);
        assertFalse(steuerer.getEingestellterWert("spiegel") == spiegel);

        // Profil von Peter ausgewählt
        profilV.select("peter");

        assertTrue(steuerer.getEingestellterWert("sitz") == sitz);
        assertTrue(steuerer.getEingestellterWert("lenkrad") == lenkrad);
        assertTrue(steuerer.getEingestellterWert("spiegel") == spiegel);

        // Einstellungen von Peter bleiben trotzdem noch erhalten
        profilV.delete("peter");

        assertTrue(steuerer.getEingestellterWert("sitz") == sitz);
        assertTrue(steuerer.getEingestellterWert("lenkrad") == lenkrad);
        assertTrue(steuerer.getEingestellterWert("spiegel") == spiegel);
    }

    @Test
    public void t03_Regensensor() throws Exception {

        // Scheibenwischer aus?
        wetterDaten.setRegen(0);
        assertFalse(steuerer.getZustand("scheibenwischer"));

        // unter Schaltwert: Scheibenwischer bleibt aus?
        wetterDaten.setRegen(40);
        assertFalse(steuerer.getZustand("scheibenwischer"));

        // ueber Schaltwert: Scheibenwischer geht an?
        wetterDaten.setRegen(80);
        assertTrue(steuerer.getZustand("scheibenwischer"));

        // ueber Schaltwert: Scheibenwischer bleibt an?
        wetterDaten.setRegen(60);
        assertTrue(steuerer.getZustand("scheibenwischer"));

        // unter Schaltwert: Scheibenwischer geht aus?
        wetterDaten.setRegen(20);
        assertFalse(steuerer.getZustand("scheibenwischer"));

        // Scheinwerfer sollte vom Regensensor unabhaengig sein.
        keineReaktion_Regensensor("scheinwerfer");
    }

    @Test
    public void t04_Helligkeitsensor() throws Exception {

        // Scheinwerfer aus?
        wetterDaten.setHelligkeit(0);
        assertFalse(steuerer.getZustand("scheinwerfer"));

        // unter Schaltwert: Scheinwerfer bleibt aus?
        wetterDaten.setHelligkeit(40);
        assertFalse(steuerer.getZustand("scheinwerfer"));

        // ueber Schaltwert: Scheinwerfer geht an?
        wetterDaten.setHelligkeit(80);
        assertTrue(steuerer.getZustand("scheinwerfer"));

        // ueber Schaltwert: Scheinwerfer bleibt an?
        wetterDaten.setHelligkeit(60);
        assertTrue(steuerer.getZustand("scheinwerfer"));

        // unter Schaltwert: Scheinwerfer geht aus?
        wetterDaten.setHelligkeit(20);
        assertFalse(steuerer.getZustand("scheinwerfer"));

        // Scheibenwischer sollte vom Helligkeitsensor unabhaengig sein.
        keineReaktion_Helligkeitsensor("scheibenwischer");
    }

    @Test
    public void t05_keineAutomatik() {

        // Profil in die Verwaltung hinzufügen
        profilV.add("Daniel", benutzerProfilTester1);
        profilV.select("Daniel");

        // keine Automatik
        profilV.getAktProfil().setAutomatik(false);

        // keine automatische Steuerung
        keineReaktion_Regensensor("scheibenwischer");
        keineReaktion_Regensensor("scheinwerfer");
        keineReaktion_Helligkeitsensor("scheibenwischer");
        keineReaktion_Helligkeitsensor("scheinwerfer");
    }

    private void keineReaktion_Regensensor(String schaltbar) {
        boolean zustand = steuerer.getZustand(schaltbar);

        // unter Schaltwert
        wetterDaten.setRegen(0);
        assertEquals(steuerer.getZustand(schaltbar), zustand);

        // ueber Schaltwert
        wetterDaten.setRegen(80);
        assertEquals(steuerer.getZustand(schaltbar), zustand);

        // unter Schaltwert
        wetterDaten.setRegen(20);
        assertEquals(steuerer.getZustand(schaltbar), zustand);
    }

    private void keineReaktion_Helligkeitsensor(String schaltbar) {
        boolean zustand = steuerer.getZustand(schaltbar);

        // unter Schaltwert
        wetterDaten.setHelligkeit(0);
        assertEquals(steuerer.getZustand(schaltbar), zustand);

        // ueber Schaltwert
        wetterDaten.setHelligkeit(80);
        assertEquals(steuerer.getZustand(schaltbar), zustand);

        // unter Schaltwert
        wetterDaten.setHelligkeit(20);
        assertEquals(steuerer.getZustand(schaltbar), zustand);
    }
}
