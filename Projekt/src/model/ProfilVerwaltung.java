package model;

import java.util.Map;
import java.util.HashMap;
import java.util.Observable;

/**
 * @author Florian Breslich, Stephan Dünkel
 * 
 * Verwaltet die Benutzerprofile
 */
public class ProfilVerwaltung extends Observable{

    private Benutzerprofil aktuellesProfil;
    private Map<String, Benutzerprofil> profile;

    /**
     * Default constructor
     */
    public ProfilVerwaltung() {
        this.profile = new HashMap<>();
    }

    /**
     * @param name:  Name, unter dem das Profil gespeichert wird.
     * @param profil
     * @throw IllegalArgumentException: wenn der Name bereits vergeben ist.
     */
    public void add(String name, Benutzerprofil profil) {
        if (this.profile.containsKey(name)) {
            throw new IllegalArgumentException("Es existiert bereits ein Profil unter dem Namen " + name + "!");
        }
        this.profile.put(name, profil);
    }

    /**
     * @param name: Name des Profils, welches geloescht werden soll.
     * @return true, wenn das Objekt geloescht wurde.
     */
    public boolean delete(String name) {
        if (!this.profile.containsKey(name)) {
            return false;
        }
        if(this.profile.get(name).equals(this.aktuellesProfil)) {
            this.aktuellesProfil = null;
        }
        this.profile.remove(name);
        return true;
    }

    /**
     * @return Benutzerprofil
     * @throw IllegalArgumentException: wenn das Profil nicht vorhanden ist.
     */
    public void select(String name) {
        if (!this.profile.containsKey(name)) {
            throw new IllegalArgumentException("Es existiert kein Profil unter dem Namen " + name + "!");
        }
        this.aktuellesProfil = this.profile.get(name);
        tell();
    }

    public Benutzerprofil getAktProfil() {
        return this.aktuellesProfil;
    }

    private void tell() {
        setChanged();
        notifyObservers();
    }
}