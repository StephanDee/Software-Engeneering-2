package model;

import java.util.Map;
/**
 * @author Florian Breslich, Stephan Dünkel
 * 
 * Beinhaltet alle Elemente des Autos.
 */
public class AutoVerwaltung {

    private Map<String, Regulierbar> regulierbar;
    private Map<String, Schaltbar> schaltbar;

    /**
     * Default constructor
     */
    public AutoVerwaltung(Map<String, Regulierbar> regulierbar, Map<String, Schaltbar> schaltbar) {
        this.regulierbar = regulierbar;
        this.schaltbar = schaltbar;
    }

    /**
     * @return Liste aller regulierbaren Elemente
     */
    public Map<String, Regulierbar> getRegulierbar() {
        return this.regulierbar;
    }

    /**
     * @return Liste aller schaltbaren Elemente
     */
    public Map<String, Schaltbar> getSchaltbar() {
        return this.schaltbar;
    }

}