package gui;

import model.*;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.swing.*;
import javax.swing.Timer;
import java.awt.*;
import java.awt.event.*;
import java.io.File;

/**
 * @author Florian Breslich, Stephan Dünkel
 *
 * Diese Klasse stellt die Benutzeroberfläche Des Hauptmenues dar.
 */
public class MainGUI extends JFrame implements ActionListener {

    // Aufbau Auto
    private Auto auto = new Auto();

    // Extras
    private boolean optionsAlreadyClicked = false;
    private Timer timer;
    private int counter;
    private int delay = 1000;

    // Aufbau Hauptmenü
    private final JLabel label, labelIcon;
    private final JButton exitButton, lightsOnButton, lightsOffButton, wipersOnButton, wipersOffButton, motorOnButton, motorOffButton, optionsButton;
    private final Container container = this.getContentPane();

    // Aufbau Einstellungsmenü
    private final JPanel settingsPanel = new JPanel(new FlowLayout());

    private JButton speichernButton, resetButton;
    private JComboBox<Integer> sitzHoeheBox;
    private JComboBox<Integer> lenkradHoeheBox;
    private JComboBox<Integer> spiegelWinkelBox;
    private JComboBox<Integer> temperaturBox;

    // Benutzerprofile
    private final DefaultListModel<String> dlm = new DefaultListModel<>();
    private final JList<String> list = new JList<>(dlm);
    private JTextField textField;
    private JButton neuButton, loeschenButton;

    // Images
    private final ImageIcon defaultIcon = new ImageIcon("images/light/1.jpg");
    private final ImageIcon lightOnIcon = new ImageIcon("images/light/2.jpg");
    private final ImageIcon seatsIcon = new ImageIcon("images/seats/1.jpg");

    // Audio
    private boolean clipClicked = false;
    private Clip clip;

    /**
     * Der Konstruktor initialisiert die Benutzeroberfäche des Hauptmenues.
     */
    public MainGUI() {
        this.setTitle("CarControllingUnit");
        this.setIconImage(new ImageIcon("images/favicon/1.jpg").getImage());

        // Labels
        label = new JLabel("Willkommen im Hauptmenü.");

        // Icons
        labelIcon = new JLabel(defaultIcon);

        // Buttons
        motorOnButton = new JButton("Motor an");
        motorOffButton = new JButton("Motor aus");
        lightsOnButton = new JButton("Licht an");
        lightsOffButton = new JButton("Licht aus");
        optionsButton = new JButton("Einstellungen");
        wipersOnButton = new JButton("Scheibenwischer an");
        wipersOffButton = new JButton("Scheibenwischer aus");
        exitButton = new JButton("Beenden");

        // ActionListener
        motorOnButton.addActionListener(this);
        motorOffButton.addActionListener(this);
        lightsOnButton.addActionListener(this);
        lightsOffButton.addActionListener(this);
        optionsButton.addActionListener(this);
        wipersOnButton.addActionListener(this);
        wipersOffButton.addActionListener(this);
        exitButton.addActionListener(this);

        // Panels
        final JPanel menuPanel = new JPanel(new FlowLayout());
        menuPanel.add(motorOnButton);
        menuPanel.add(motorOffButton);
        menuPanel.add(lightsOnButton);
        menuPanel.add(lightsOffButton);
        menuPanel.add(wipersOnButton);
        menuPanel.add(wipersOffButton);
        menuPanel.add(optionsButton);
        menuPanel.add(exitButton);

        final JPanel labelPanel = new JPanel();
        labelPanel.add(label, BorderLayout.PAGE_START);
        labelPanel.add(labelIcon, BorderLayout.CENTER);

        // Container
        container.add(labelPanel, BorderLayout.CENTER);
        container.add(menuPanel, BorderLayout.PAGE_END);

        // JFrame
        this.pack();
        this.setSize(1080, 550);
        this.setVisible(true);

        // initialisiert die Einstellungen des Fahrzeugs
        auto.carInterface();

        // Beendet das Programm
        this.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(final WindowEvent i_) {
                System.exit(0);
            }
        });
    }

    /**
     * Diese Methode behandelt alle ActionEvents.
     *
     * @param ae ActionEvent
     */
    public void actionPerformed(ActionEvent ae) {
        if (ae.getSource() == this.motorOnButton) {
            label.setText("");
            auto.motor.an();
            try {
                AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(new File("sounds/1.wav"));
                clip = AudioSystem.getClip();
                clip.open(audioInputStream);
                clip.start();
                clipClicked = true;
            } catch (Exception ex) {
                System.out.println("Error with playing sound.");
                ex.printStackTrace();
            }
        } else if (ae.getSource() == this.motorOffButton) {
            if (clipClicked) {
                clip.stop();
            }
            label.setText("");
            auto.motor.aus();
        } else if (ae.getSource() == this.lightsOnButton) {
            label.setText("");
            labelIcon.setIcon(lightOnIcon);
            auto.scheinwerfer.an();
        } else if (ae.getSource() == this.lightsOffButton) {
            label.setText("");
            labelIcon.setIcon(defaultIcon);
            auto.scheinwerfer.aus();
        } else if (ae.getSource() == this.optionsButton) {
            label.setText("");
            labelIcon.setIcon(seatsIcon);
            if (!optionsAlreadyClicked) {
                einstellungen();
                optionsAlreadyClicked = true;
            }
            settingsPanel.setVisible(true);
        } else if (ae.getSource() == this.wipersOnButton) {
            label.setText("Scheibenwischer an.");
            auto.scheibenwischer.an();
            timer();
        } else if (ae.getSource() == this.wipersOffButton) {
            label.setText("Scheibenwischer aus.");
            auto.scheibenwischer.aus();
            timer();
        } else if (ae.getSource() == speichernButton) {
            if (list.isSelectionEmpty()) {
                label.setText("Wähle oder erstelle ein Profil.");
            }
            if (!list.isSelectionEmpty()) {
                auto.profilV.select(list.getSelectedValue());
                auto.profilV.getAktProfil().setSitzhoehe(sitzHoeheBox.getItemAt(sitzHoeheBox.getSelectedIndex()));
                auto.profilV.getAktProfil().setLenkradHoehe(lenkradHoeheBox.getItemAt(lenkradHoeheBox.getSelectedIndex()));
                auto.profilV.getAktProfil().setSpiegelWinkel(spiegelWinkelBox.getItemAt(spiegelWinkelBox.getSelectedIndex()));
                auto.profilV.getAktProfil().setTemperatur(temperaturBox.getItemAt(temperaturBox.getSelectedIndex()));

                auto.sitz.setWert(sitzHoeheBox.getItemAt(sitzHoeheBox.getSelectedIndex()));
                auto.lenkrad.setWert(lenkradHoeheBox.getItemAt(lenkradHoeheBox.getSelectedIndex()));
                auto.spiegel.setWert(spiegelWinkelBox.getItemAt(lenkradHoeheBox.getSelectedIndex()));
                auto.temperatur.setWert(temperaturBox.getItemAt(temperaturBox.getSelectedIndex()));
                labelIcon.setIcon(defaultIcon);
                if (!auto.scheinwerfer.getZustand()) {
                    labelIcon.setIcon(defaultIcon);
                }
                if (auto.scheinwerfer.getZustand()) {
                    labelIcon.setIcon(lightOnIcon);
                }
                settingsPanel.setVisible(false);
            }
        } else if (ae.getSource() == resetButton) {
            sitzHoeheBox.setSelectedIndex(0);
            lenkradHoeheBox.setSelectedIndex(0);
            spiegelWinkelBox.setSelectedIndex(0);
            temperaturBox.setSelectedIndex(0);
        } else if (ae.getSource() == this.neuButton) {
            boolean allElementsPassed = false;
            String name = textField.getText();
            if (name.isEmpty()) {
                label.setText("Bitte geben Sie einen Namen ein.");
                timer();
            }
            if (list.isSelectionEmpty()) {
                allElementsPassed = true;
            }
            if (!name.isEmpty()) {
                for (int i = 0; i < list.getModel().getSize(); i++) {
                    if (name.equals(list.getModel().getElementAt(i))) {
                        label.setText("Der Name " + name + " ist schon vorhanden. Versuchen Sie es erneuert.");
                        allElementsPassed = false;
                        break;
                    }
                    if (i == list.getLastVisibleIndex()) {
                        allElementsPassed = true;
                    }
                }
                if (allElementsPassed) {
                    Benutzerprofil newUser = new Benutzerprofil();
                    auto.profilV.add(textField.getText(), newUser);
                    dlm.addElement(textField.getText());
                    textField.setText("");
                    label.setText(("Profil wurde erstellt."));
                    timer();
                }
            }
        } else if (ae.getSource() == this.loeschenButton) {
            if (list.isSelectionEmpty()) {
                label.setText("Bitte wählen oder erstellen Sie ein Profil.");
                timer();
            }
            if (!list.isSelectionEmpty()) {
                auto.profilV.delete(list.getSelectedValue());
                int index = list.getSelectedIndex();
                if (index != -1) {
                    dlm.remove(index);
                    label.setText("Profil wurde gelöscht.");
                    timer();
                }
            }
        } else if (ae.getSource() == this.exitButton) {
            label.setText("Das Programm wird geschlossen.");
            System.out.println("Das Programm wird geschlossen.");
            System.exit(0);
        }
    }

    /**
     * Menü mit verschiedenen Einstellungen werden initialisiert.
     * Hier können die Profile ausgewählt und konfiguriert werden.
     */
    public void einstellungen() {

        // Einstellungsmenü
        speichernButton = new JButton("Speichern");
        resetButton = new JButton("Reset");

        // Labels
        final JLabel sitzHoeheLabel = new JLabel("Sitzhöhe");
        final JLabel lenkradHoeheLabel = new JLabel("Lenkradhöhe");
        final JLabel spiegelWinkelLabel = new JLabel("Spiegelwinkel");
        final JLabel temperaturLabel = new JLabel("Temperatur");


        // Patterns
        Integer[] sitzHoehePattern = {0, 15, 30, 45, 60, 75, 100};
        Integer[] lenkradHoehePattern = {0, 15, 30, 45, 60, 75, 100};
        Integer[] spiegelWinkelPattern = {0, 15, 30, 45, 60, 75, 100};
        Integer[] temperaturPattern = {0, 5, 10, 15, 20, 25, 30};

        // Boxen
        sitzHoeheBox = new JComboBox<>(sitzHoehePattern);
        lenkradHoeheBox = new JComboBox<>(lenkradHoehePattern);
        spiegelWinkelBox = new JComboBox<>(spiegelWinkelPattern);
        temperaturBox = new JComboBox<>(temperaturPattern);

        // Panel
        settingsPanel.setLayout(new BoxLayout(settingsPanel, BoxLayout.Y_AXIS));

        // Ausgewähltes Profil in der Liste
        if (optionsAlreadyClicked) {
            // Aktuelles Profil
            auto.profilV.select(list.getSelectedValue());
        }
        if (!optionsAlreadyClicked) {
            // Standard Profil, wenn noch nichts ausgewählt
            auto.profilV.select("Standardprofil");
            optionsAlreadyClicked = true;
        }

        // Layout sitzHoeheBox
        settingsPanel.add(sitzHoeheLabel);
        sitzHoeheLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
        settingsPanel.add(sitzHoeheBox);
        sitzHoeheBox.setAlignmentX(Component.LEFT_ALIGNMENT);
        sitzHoeheBox.setMinimumSize(new Dimension(100, 28));
        sitzHoeheBox.setPreferredSize(new Dimension(150, 28));
        sitzHoeheBox.setMaximumSize(sitzHoeheBox.getPreferredSize());
        sitzHoeheBox.setSelectedItem(auto.profilV.getAktProfil().getSitzhoehe());

        // Layout lenkradHoeheBox
        settingsPanel.add(lenkradHoeheLabel);
        lenkradHoeheLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
        settingsPanel.add(lenkradHoeheBox);
        lenkradHoeheBox.setAlignmentX(Component.LEFT_ALIGNMENT);
        lenkradHoeheBox.setMinimumSize(new Dimension(100, 28));
        lenkradHoeheBox.setPreferredSize(new Dimension(150, 28));
        lenkradHoeheBox.setMaximumSize(lenkradHoeheBox.getPreferredSize());
        lenkradHoeheBox.setSelectedItem(auto.profilV.getAktProfil().getLenkradHoehe());

        // Layout spiegelWinkelBox
        settingsPanel.add(spiegelWinkelLabel);
        spiegelWinkelLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
        settingsPanel.add(spiegelWinkelBox);
        spiegelWinkelBox.setAlignmentX(Component.LEFT_ALIGNMENT);
        spiegelWinkelBox.setMinimumSize(new Dimension(100, 28));
        spiegelWinkelBox.setPreferredSize(new Dimension(150, 28));
        spiegelWinkelBox.setMaximumSize(spiegelWinkelBox.getPreferredSize());
        spiegelWinkelBox.setSelectedItem(auto.profilV.getAktProfil().getSpiegelWinkel());

        // Layout temperaturBox
        settingsPanel.add(temperaturLabel);
        temperaturLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
        settingsPanel.add(temperaturBox);
        temperaturBox.setAlignmentX(Component.LEFT_ALIGNMENT);
        temperaturBox.setMinimumSize(new Dimension(100, 28));
        temperaturBox.setPreferredSize(new Dimension(150, 28));
        temperaturBox.setMaximumSize(temperaturBox.getPreferredSize());
        temperaturBox.setSelectedItem(auto.profilV.getAktProfil().getTemperatur());

        // Layout speichernButton
        settingsPanel.add(speichernButton);
        speichernButton.setAlignmentX(Component.LEFT_ALIGNMENT);
        speichernButton.setMinimumSize(new Dimension(100, 28));
        speichernButton.setPreferredSize(new Dimension(150, 28));
        speichernButton.setMaximumSize(temperaturBox.getPreferredSize());
        settingsPanel.add(resetButton);

        // Layout resetButton
        resetButton.setAlignmentX(Component.LEFT_ALIGNMENT);
        resetButton.setMinimumSize(new Dimension(100, 28));
        resetButton.setPreferredSize(new Dimension(150, 28));
        resetButton.setMaximumSize(temperaturBox.getPreferredSize());

        // ActionListener
        speichernButton.addActionListener(this);
        resetButton.addActionListener(this);

        // Benutzerprofilmenü
        dlm.addElement("Standardprofil");

        // Field
        textField = new JTextField();

        // Buttons
        neuButton = new JButton("Neu");
        loeschenButton = new JButton("Löschen");

        // ActionListener
        neuButton.addActionListener(this);
        loeschenButton.addActionListener(this);

        // ScrollPane
        final JScrollPane scrollPane = new JScrollPane(list);
        scrollPane.setAlignmentX(Component.LEFT_ALIGNMENT);
        scrollPane.setMinimumSize(new Dimension(100, 100));
        scrollPane.setPreferredSize(new Dimension(150, 100));
        scrollPane.setMaximumSize(scrollPane.getPreferredSize());
        list.setSelectedIndex(0);
        settingsPanel.add(scrollPane);

        // Layout textField
        settingsPanel.add(textField);
        textField.setAlignmentX(Component.LEFT_ALIGNMENT);
        textField.setMinimumSize(new Dimension(100, 28));
        textField.setPreferredSize(new Dimension(150, 28));
        textField.setMaximumSize(textField.getPreferredSize());

        // Layout neuButton
        settingsPanel.add(neuButton);
        neuButton.setAlignmentX(Component.LEFT_ALIGNMENT);
        neuButton.setMinimumSize(new Dimension(100, 28));
        neuButton.setPreferredSize(new Dimension(150, 28));
        neuButton.setMaximumSize(neuButton.getPreferredSize());

        // Layout loeschenButton
        settingsPanel.add(loeschenButton);
        loeschenButton.setAlignmentX(Component.LEFT_ALIGNMENT);
        loeschenButton.setMinimumSize(new Dimension(100, 28));
        loeschenButton.setPreferredSize(new Dimension(150, 28));
        loeschenButton.setMaximumSize(loeschenButton.getPreferredSize());

        // Container
        container.add(settingsPanel, BorderLayout.LINE_END);

        /**
         * Nach 2 maligen klicken, soll das ausgewählte Profil ausgewählt werden und die Einstellungen in den Boxen angezeigt werden.
         */
        list.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent evt) {
                if (evt.getClickCount() == 2) {
                    label.setText("Profil wurde ausgewählt.");
                    auto.profilV.select(list.getSelectedValue());
                    sitzHoeheBox.setSelectedItem(auto.profilV.getAktProfil().getSitzhoehe());
                    lenkradHoeheBox.setSelectedItem(auto.profilV.getAktProfil().getLenkradHoehe());
                    spiegelWinkelBox.setSelectedItem(auto.profilV.getAktProfil().getSpiegelWinkel());
                    temperaturBox.setSelectedItem(auto.profilV.getAktProfil().getTemperatur());
                    timer();
                }
            }
        });
    }

    /**
     * Nach einer bestimmten Zeit blendet der Timer das JLabel aus.
     */
    public void timer() {
        counter = 30;
        ActionListener action = event -> {
            if (counter == 0) {
                timer.stop();
                label.setVisible(false);
            } else {
                counter--;
            }
        };
        timer = new Timer(delay, action);
        timer.setInitialDelay(0);
        timer.start();
        label.setVisible(true);
    }

    /**
     * Diese Methode startet die main.
     *
     * @param args
     */
    public static void main(String[] args) {
        new MainGUI();
    }
}