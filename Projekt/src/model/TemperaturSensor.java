package model;

/**
 * @author Florian Breslich, Stephan Dünkel
 */
public class TemperaturSensor implements Sensor {

    private final Wetterdaten daten;

    public TemperaturSensor(Wetterdaten wd) {
        this.daten = wd;
    }

    @Override
    public int getWert() {
        return daten.getTemperatur();
    }
}