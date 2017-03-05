package model;

import java.util.*;

/**
 * @author Florian Breslich, Stephan Dünkel
 *
 * Diese Klasse repräsentiert die Benutzeroberfläche als Konsolenausgabe.
 */
public class Main {

    private static final Scanner scn = new Scanner(System.in);


    /**
     * Startet das Programm und nimmt Benutzereingaben entgegen.
     *
     * @param args Befehlszeilen Argument
     * @throws Exception
     */
    public static void main(String[] args) throws Exception {

        // Aufbau Auto
        Regulierbar lenkrad = new Regulierbar(0, 100);
        Regulierbar sitz = new Regulierbar(0, 100);
        Regulierbar spiegel = new Regulierbar(0, 100);
        Schaltbar scheibenwischer = new Schaltbar(50);
        Schaltbar scheinwerfer = new Schaltbar(50);
        Schaltbar motor = new Schaltbar(50);

        Map<String, Regulierbar> regulierbar = new HashMap<>();
        regulierbar.put("lenkrad", lenkrad);
        regulierbar.put("sitz", sitz);
        regulierbar.put("spiegel", spiegel);

        Map<String, Schaltbar> schaltbar = new HashMap<>();
        schaltbar.put("scheibenwischer", scheibenwischer);
        schaltbar.put("scheinwerfer", scheinwerfer);
        schaltbar.put("motor", motor);

        AutoVerwaltung autoV = new AutoVerwaltung(regulierbar, schaltbar);

        // Benutzerprofile
        Benutzerprofil peter = new Benutzerprofil();
        peter.setSitzhoehe(40);
        peter.setLenkradHoehe(80);
        peter.setSpiegelWinkel(60);

        Benutzerprofil paul = new Benutzerprofil();
        paul.setSitzhoehe(20);
        paul.setLenkradHoehe(60);
        paul.setSpiegelWinkel(80);

        ProfilVerwaltung profilV = new ProfilVerwaltung();
        profilV.add("peter", peter);
        profilV.add("paul", paul);

        // Sensoren
        Wetterdaten wd = new Wetterdaten(50, 50, 50);
        Sensor helligkeit = new HelligkeitSensor(wd);
        Sensor regen = new RegenSensor(wd);

        List<Sensor> sensoren = new ArrayList<>();
        sensoren.add(helligkeit);
        sensoren.add(regen);

        // Autosteuerung
        Autosteuerer steuerer = new Autosteuerer(profilV, autoV, sensoren);
        wd.addObserver(steuerer);

        String in;
        System.out.println("Hauptmenü \n");
        System.out.println("Licht einschalten (lichtan), Licht ausschalten (lichtaus),"
                + " analog (scheibenwischeran/aus), (scheinwerferan/aus)"
                + "\nSitz einstellen (sitz, analog (lenkrad), (spiegel)"
                + "\nProfil erstellen (profil)"
                + "\nBeenden (quit)");

        while (true) {

            System.out.print(">> ");
            in = scn.nextLine().trim();

            if (in.toLowerCase().equals("quit")) {
                System.out.println("Programm wird beendet...");
                break; // Beendet das Programm
            }

            switch (in.toLowerCase()) {
                case "profil": {
                    System.out.print("Geben Sie einen Namen ein: ");
                    String inKonsole = scn.nextLine();
                    inKonsole.trim();
                    Benutzerprofil neu = new Benutzerprofil();
                    profilV.add(inKonsole, neu);
                    System.out.println("Benutzerprofil '" + inKonsole + "' wurde erstellt.");
                    break;
                }
                case "lichtan": {
                    scheinwerfer.an();
                    System.out.println("Die Scheinwerfer wurden eingeschaltet.");
                    break;
                }
                case "lichtaus": {
                    scheinwerfer.aus();
                    System.out.println("Die Scheinwerfer wurden ausgeschaltet.");
                    break;
                }
                case "scheibenwischeran": {
                    scheibenwischer.an();
                    System.out.println("Die Scheibenwischer wurden eingeschaltet.");
                    break;
                }
                case "scheibenwischeraus": {
                    scheibenwischer.aus();
                    System.out.println("Die Scheibenwischer wurden ausgeschaltet.");
                    break;
                }
                case "sitz": {
                    if (!in.isEmpty()) {
                        System.out.print("Geben Sie die Sitzhoehe ein. (0, 20, 40, 60, 80, 100)\n>> ");
                        String inKonsole = scn.nextLine();
                        inKonsole.trim();
                        if (!inKonsole.isEmpty() && !inKonsole.matches("[\\s]+") && inKonsole.equals("0")) {
                            sitz.setWert(0);
                            System.out.println("Sitzhoehe wurde auf 0 gestellt.");
                            break;
                        }
                        if (!inKonsole.isEmpty() && !inKonsole.matches("[\\s]+") && inKonsole.equals("20")) {
                            sitz.setWert(20);
                            System.out.println("Sitzhoehe wurde auf 20 gestellt.");
                            break;
                        }
                        if (!inKonsole.isEmpty() && !inKonsole.matches("[\\s]+") && inKonsole.equals("40")) {
                            sitz.setWert(40);
                            System.out.println("Sitzhoehe wurde auf 40 gestellt.");
                            break;
                        }
                        if (!inKonsole.isEmpty() && !inKonsole.matches("[\\s]+") && inKonsole.equals("60")) {
                            sitz.setWert(60);
                            System.out.println("Sitzhoehe wurde auf 60 gestellt.");
                            break;
                        }
                        if (!inKonsole.isEmpty() && !inKonsole.matches("[\\s]+") && inKonsole.equals("80")) {
                            sitz.setWert(80);
                            System.out.println("Sitzhoehe wurde auf 80 gestellt.");
                            break;
                        }
                        if (!inKonsole.isEmpty() && !inKonsole.matches("[\\s]+") && inKonsole.equals("100")) {
                            sitz.setWert(100);
                            System.out.println("Sitzhoehe wurde auf 100 gestellt.");
                            break;
                        } else {
                            System.err.println("Ihre Eingabe war nicht Korrekt.\n");
                            System.out.println("\n\nHauptmenü\n");
                            System.out.println("Licht einschalten (lichtan), Licht ausschalten (lichtaus),"
                                    + " analog (scheibenwischeran/aus), (scheinwerferan/aus)"
                                    + "\nSitz einstellen (sitz), analog (lenkrad), (spiegel)"
                                    + "\nProfil erstellen (profil)"
                                    + "\nBeenden (quit)");
                        }
                    }
                    break;
                }
                case "lenkrad": {
                    if (!in.isEmpty()) {
                        System.out.print("Geben Sie die Lenkradhoehe ein. (0, 20, 40, 60, 80, 100)\n>> ");
                        String inKonsole = scn.nextLine();
                        inKonsole.trim();
                        if (!inKonsole.isEmpty() && !inKonsole.matches("[\\s]+") && inKonsole.equals("0")) {
                            lenkrad.setWert(0);
                            System.out.println("Lenkradhoehe wurde auf 0 gestellt.");
                            break;
                        }
                        if (!inKonsole.isEmpty() && !inKonsole.matches("[\\s]+") && inKonsole.equals("20")) {
                            lenkrad.setWert(20);
                            System.out.println("Lenkradhoehe wurde auf 20 gestellt.");
                            break;
                        }
                        if (!inKonsole.isEmpty() && !inKonsole.matches("[\\s]+") && inKonsole.equals("40")) {
                            lenkrad.setWert(40);
                            System.out.println("Lenkradhoehe wurde auf 40 gestellt.");
                            break;
                        }
                        if (!inKonsole.isEmpty() && !inKonsole.matches("[\\s]+") && inKonsole.equals("60")) {
                            lenkrad.setWert(60);
                            System.out.println("Lenkradhoehe wurde auf 60 gestellt.");
                            break;
                        }
                        if (!inKonsole.isEmpty() && !inKonsole.matches("[\\s]+") && inKonsole.equals("80")) {
                            lenkrad.setWert(80);
                            System.out.println("Lenkradhoehe wurde auf 80 gestellt.");
                            break;
                        }
                        if (!inKonsole.isEmpty() && !inKonsole.matches("[\\s]+") && inKonsole.equals("100")) {
                            lenkrad.setWert(100);
                            System.out.println("Lenkradhoehe wurde auf 100 gestellt.");
                            break;
                        } else {
                            System.err.println("Ihre Eingabe war nicht Korrekt.\n");
                            System.out.println("\n\nHauptmenü\n");
                            System.out.println("Licht einschalten (lichtan), Licht ausschalten (lichtaus),"
                                    + " analog (scheibenwischeran/aus), (scheinwerferan/aus)"
                                    + "\nSitz einstellen (sitz), analog (lenkrad), (spiegel)"
                                    + "\nProfil erstellen (profil)"
                                    + "\nBeenden (quit)");
                        }
                    }
                    break;
                }
                case "spiegel": {
                    if (!in.isEmpty()) {
                        System.out.print("Geben Sie die Spiegelwinkel ein. (0, 20, 40, 60, 80, 100)\n>> ");
                        String inKonsole = scn.nextLine();
                        inKonsole.trim();
                        if (!inKonsole.isEmpty() && !inKonsole.matches("[\\s]+") && inKonsole.equals("0")) {
                            spiegel.setWert(0);
                            System.out.println("Spiegelwinkel wurde auf 0 gestellt.");
                            break;
                        }
                        if (!inKonsole.isEmpty() && !inKonsole.matches("[\\s]+") && inKonsole.equals("20")) {
                            spiegel.setWert(20);
                            System.out.println("Spiegelwinkel wurde auf 20 gestellt.");
                            break;
                        }
                        if (!inKonsole.isEmpty() && !inKonsole.matches("[\\s]+") && inKonsole.equals("40")) {
                            spiegel.setWert(40);
                            System.out.println("Spiegelwinkel wurde auf 40 gestellt.");
                            break;
                        }
                        if (!inKonsole.isEmpty() && !inKonsole.matches("[\\s]+") && inKonsole.equals("60")) {
                            spiegel.setWert(60);
                            System.out.println("Spiegelwinkel wurde auf 60 gestellt.");
                            break;
                        }
                        if (!inKonsole.isEmpty() && !inKonsole.matches("[\\s]+") && inKonsole.equals("80")) {
                            spiegel.setWert(80);
                            System.out.println("Spiegelwinkel wurde auf 80 gestellt.");
                            break;
                        }
                        if (!inKonsole.isEmpty() && !inKonsole.matches("[\\s]+") && inKonsole.equals("100")) {
                            spiegel.setWert(100);
                            System.out.println("Spiegelwinkel wurde auf 100 gestellt.");
                            break;
                        } else {
                            System.err.println("Ihre Eingabe war nicht Korrekt.");
                            System.out.println("\n\nHauptmenü\n");
                            System.out.println("Licht einschalten (lichtan), Licht ausschalten (lichtaus),"
                                    + " analog (scheibenwischeran/aus), (scheinwerferan/aus)"
                                    + "\nSitz einstellen (sitz), analog (lenkrad), (spiegel)"
                                    + "\nProfil erstellen (profil)"
                                    + "\nBeenden (quit)");
                        }
                    }
                    break;
                }
            }
        }
    }
}