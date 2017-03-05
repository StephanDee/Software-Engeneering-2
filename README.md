# Software-Engeneering-2
Software-Engeneering-2 / WS16/17 / Dozent: Hakan Coskun

Teilnehmer: Stephan Dünkel, Florian Breslich


CarControllingUnit

Beschreibung:

Diese Software wird für die Bedienung eines Autos eingesetzt. Es steuert verschiedenste Funktionen und Kontrollmechanismen in einem Auto.
Funktionen, wie das Einstellen des Lenkrads, der Sitzposition und der Spiegel, werden mit dieser Software gesteuert.
Kontrollmechanismen werden durch die Kommunikation zwischen Sensoren und der Software umgesetzt und mittels unseren System geregelt.
So kann z.B. festgestellt werden, ob es außerhalb des Autos hell oder dunkel ist, um ggf. die Scheinwerfer einzuschalten.


Benutzung:

Das Programm ist in Java umgesetzt worden und in der Source in drei Packages aufgeteilt.


gui:

Die Gui ist die Benutzeroberfläche des Programms. Diese wird in der MainGUI-Klasse gestartet. In dieser GUI können Sie ein Fahrzeug
individuell konfigurieren.


model:

Es ist das Herzstück des Projektes. Diese wird in der MainGUI-Klasse oder auch in der Main-Klasse angesprochen.
Die Main-Klasse realisiert eine Konsolenausgabe, die die verschiedenen Optionen bereit hält.
Separat wurden JUnit Testklassen realisiert, die die korrekte Funktion der einzelnen Komponenten gewährleisten.


systemTest:

Hier sind sämtliche Testfälle mit konkreten Beispielen realisiert worden. Es wird in der SystemTest-Klasse gestartet.