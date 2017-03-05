package model;

/**
 * @author Florian Breslich, Stephan Dünkel
 * 
 * Klasse fuer ein-/ ausschaltbare Komponenten
 */
public class Schaltbar {
    private final int schaltWert;
    private boolean zustand; // true: 'An', false: 'Aus'

    public Schaltbar(int schaltWert) {
        this.schaltWert = schaltWert;
        this.zustand = false; // default: Komponente ausgeschaltet
    }

    /**
     * @return Zustand (true: 'An', false: 'Aus')
     */
    public boolean getZustand() {
        return this.zustand;
    }

    public int getSchaltWert() {
        return this.schaltWert;
    }

    public void an() {
        this.zustand = true;
    }

    public void aus() {
        this.zustand = false;
    }
}