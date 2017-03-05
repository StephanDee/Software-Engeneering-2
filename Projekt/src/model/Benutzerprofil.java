package model;

/**
 * @author Florian Breslich, Stephan Dünkel
 *
 * Beinhaltet Einstellungen der regulierbaren Elemente
 * eines Nutzers.
 */
public class Benutzerprofil {

    private int sitzhoehe;
    private int lenkradHoehe;
    private int spiegelWinkel;
    private int temperatur;
    public boolean automatik;

    /**
     * Default constructor
     */
    public Benutzerprofil() {
        this.sitzhoehe = 0;
        this.lenkradHoehe = 0;
        this.spiegelWinkel = 0;
        this.automatik = true;
    }

    public int getSitzhoehe() {
        return this.sitzhoehe;
    }

    public void setSitzhoehe(int wert) {
        this.sitzhoehe = wert;
    }

    public int getLenkradHoehe() {
        return this.lenkradHoehe;
    }

    public void setLenkradHoehe(int wert) {
        this.lenkradHoehe = wert;
    }

    public int getSpiegelWinkel() {
        return this.spiegelWinkel;
    }

    public void setSpiegelWinkel(int wert) { this.spiegelWinkel = wert; }

    public int getTemperatur() { return this.temperatur; }

    public void setTemperatur(int wert) { this.temperatur = wert; }

    public boolean getAutomatik() {
        return this.automatik;
    }

    public void setAutomatik(boolean wert) {
        this.automatik = wert;
    }
}