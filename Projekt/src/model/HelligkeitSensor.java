package model;

/**
 * @author Florian Breslich, Stephan Dünkel
 */
public class HelligkeitSensor implements Sensor {

    private final Wetterdaten daten;

    public HelligkeitSensor(Wetterdaten wd) {
        this.daten = wd;
    }

    @Override
    public int getWert() {
        return daten.getHelligkeit();
    }
}