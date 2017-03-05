package model;

/**
 * @author Florian Breslich, Stephan Dünkel
 * 
 * Klasse fuer regulierbare Komponenten
 */
public class Regulierbar {

    private final int minWert;
    private final int maxWert;
    private int wert;

    /**
     * @param minWert, maxWert: Grenzwerte
     */
    public Regulierbar(int minWert, int maxWert) {
        if(minWert > maxWert) {
            throw new IllegalArgumentException("minWert darf nicht groesser als maxWert sein!");
        }
        this.minWert = minWert;
        this.maxWert = maxWert;
        this.wert = minWert;
    }

    public int getMinWert() {
        return this.minWert;
    }

    public int getMaxWert() {
        return this.maxWert;
    }

    public int getWert() {
        return this.wert;
    }

    public void setWert(int wert) {
        if (this.minWert <= wert && wert <= this.maxWert) {
            this.wert = wert;
        } else {
            throw new IllegalArgumentException("Wert " + wert + " muss zwischen " + this.minWert + " und " + this.maxWert + " liegen!");
        }
    }
}