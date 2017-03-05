package model;

/**
 * @author Florian Breslich, Stephan Dünkel
 */
public class RegenSensor implements Sensor {

    private final Wetterdaten daten;

    public RegenSensor(Wetterdaten wd) {
        this.daten = wd;
    }

    @Override
    public int getWert() {
        return daten.getRegen();
    }
}