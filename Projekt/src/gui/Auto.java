package gui;

import model.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * @author Florian Breslich, Stephan Dünkel
 *
 * Diese Klasse repräsentiert den Aufbau des Fahrzeugs.
 */
public class Auto {

    // Aufbau Auto
    final Regulierbar lenkrad = new Regulierbar(0, 100);
    final Regulierbar sitz = new Regulierbar(0, 100);
    final Regulierbar spiegel = new Regulierbar(0, 100);
    final Regulierbar temperatur = new Regulierbar(0, 30);
    final Schaltbar scheibenwischer = new Schaltbar(50);
    final Schaltbar scheinwerfer = new Schaltbar(50);
    final Schaltbar motor = new Schaltbar(50);
    private Benutzerprofil defaultUser;
    ProfilVerwaltung profilV;

    /**
     * Einstellungen des Fahrzeuges werden hier initialisiert.
     */
    public void carInterface() {

        Map<String, Regulierbar> regulierbar = new HashMap<>();
        regulierbar.put("lenkrad", lenkrad);
        regulierbar.put("sitz", sitz);
        regulierbar.put("spiegel", spiegel);
        regulierbar.put("temperatur", temperatur);

        Map<String, Schaltbar> schaltbar = new HashMap<>();
        schaltbar.put("scheibenwischer", scheibenwischer);
        schaltbar.put("scheinwerfer", scheinwerfer);
        schaltbar.put("motor", motor);

        AutoVerwaltung autoV = new AutoVerwaltung(regulierbar, schaltbar);

        // Benutzerprofile
        defaultUser = new Benutzerprofil();
        defaultUser.setSitzhoehe(15);
        defaultUser.setLenkradHoehe(45);
        defaultUser.setSpiegelWinkel(30);
        defaultUser.setTemperatur(15);

        profilV = new ProfilVerwaltung();
        profilV.add("Standardprofil", defaultUser);

        // Sensoren
        Wetterdaten wd = new Wetterdaten(30, 20, 40);
        Sensor helligkeit = new HelligkeitSensor(wd);
        Sensor regen = new RegenSensor(wd);

        java.util.List<Sensor> sensoren = new ArrayList<>();
        sensoren.add(helligkeit);
        sensoren.add(regen);

        // Autosteuerung
        Autosteuerer steuerer = new Autosteuerer(profilV, autoV, sensoren);
        wd.addObserver(steuerer);
    }
}
