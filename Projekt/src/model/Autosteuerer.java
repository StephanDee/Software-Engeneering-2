package model;

import java.util.List;
import java.util.Map;
import java.util.Observer;
import java.util.Observable;

/**
 * @author Florian Breslich, Stephan Dünkel
 * 
 * Steuert automatisch die schaltbaren Elemente, sobald sich Sensorwerte ändern,
 * sowie alle regulierbaren Elemente, sobald ein anderes Benutzerprofil ausgewählt wird.
 */
public class Autosteuerer implements Observer {

    private ProfilVerwaltung profile;
    private AutoVerwaltung auto;
    private List<Sensor> sensoren;

    /**
     * Default constructor
     */
    public Autosteuerer(ProfilVerwaltung profilV, AutoVerwaltung autoV, List<Sensor> sensoren) {
        this.profile = profilV;
        this.auto = autoV;
        this.sensoren = sensoren;
        
        updateRegulierbar();
        updateSchaltbar();
    }
    
    /**
     * Gibt Zustand eines schaltbaren Elements zurück.
     * 
     * @param schaltElement "scheibenwischer" oder "scheinwerfer"
     * @return Zustand (an oder aus)
     */
    public boolean getZustand(String schaltElement) {
        return this.auto.getSchaltbar().get(schaltElement).getZustand();
    }
    
    /**
     * Gibt eingestellten Wert eines regulierbaren Elements zurück.
     * 
     * @param schaltElement "lenkrad", "sitz" oder "spiegel"
     * @return Wert
     */
    public int getEingestellterWert(String schaltElement) {
        return this.auto.getRegulierbar().get(schaltElement).getWert();
    }

    @Override
    public void update(Observable o, Object arg) {
        if(o instanceof ProfilVerwaltung) {
            updateRegulierbar();
        }
        
        if(o instanceof Wetterdaten) {
            updateSchaltbar();
        }
    }
    
    /**
     * Stellt die regulierbaren Elemente ein,
     * sofern ein Benutzerprofil vorhanden ist.
     */
    private void updateRegulierbar() {
        Benutzerprofil aktProfil = this.profile.getAktProfil();
        if(aktProfil != null) {
            Map<String, Regulierbar> regulierbar = this.auto.getRegulierbar();
            regulierbar.get("lenkrad").setWert(aktProfil.getLenkradHoehe());
            regulierbar.get("sitz").setWert(aktProfil.getSitzhoehe());
            regulierbar.get("spiegel").setWert(aktProfil.getSpiegelWinkel());
        }
    }
    
    /**
     * Stellt die schaltbaren Elemente ein.
     * Ist ein Benutzer ausgewaehlt, der keine automatische Steuerung moechte,
     * so werden diese nicht automatisch ein- und ausgeschaltet.
     */
    private void updateSchaltbar() {
        Benutzerprofil aktProfil = this.profile.getAktProfil();
        if(aktProfil != null && !aktProfil.getAutomatik()) {
            return; // Benutzer moechte keine Automatik.
        }
        
        Map<String, Schaltbar> schaltbar = this.auto.getSchaltbar();

        Schaltbar sw1 = schaltbar.get("scheibenwischer");
        Schaltbar sw2 = schaltbar.get("scheinwerfer");
      
        for(Sensor s : this.sensoren) {
            int sensorWert = s.getWert();
            
            // Regensensor -> Scheibenwischer
            if(s instanceof RegenSensor) {
                if (sensorWert > sw1.getSchaltWert()) sw1.an();
                if (sensorWert < sw1.getSchaltWert()) sw1.aus();
            }
            
            // Helligkeitsensor -> Scheinwerfer
            if(s instanceof HelligkeitSensor) {
                if (sensorWert > sw2.getSchaltWert()) sw2.an();
                if (sensorWert < sw2.getSchaltWert()) sw2.aus();
            }
        }
    }
}