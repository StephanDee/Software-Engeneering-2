package model;

import java.util.Observer;
import java.util.Observable;

/**
 * @author Florian Breslich, Stephan Dünkel
 * 
 * Simuliert eine Wetterstation
 * @author fbreslich
 */
public class Wetterdaten extends Observable {

    private int temperatur;
    private int helligkeit;
    private int regen;

    public Wetterdaten(int temperatur, int helligkeit, int regen) {
        this.temperatur = temperatur;
        this.helligkeit = helligkeit;
        this.regen = regen;
    }

    public int getTemperatur() {
        return this.temperatur;
    }

    public int getHelligkeit() {
        return this.helligkeit;
    }

    public int getRegen() {
        return this.regen;
    }

    public void setTemperatur(int wert) {
        this.temperatur = wert;
        tell();
    }

    public void setHelligkeit(int wert) {
        this.helligkeit = wert;
        tell();
    }

    public void setRegen(int wert) {
        this.regen = wert;
        tell();
    }

    private void tell() {
        setChanged();
        notifyObservers();
    }
}