# Einführung
Im Ausbildungsprogramm von dem Bachelorstudiengang von der Fachhochschule Landshut
muss jeder Studierender ein Projekt durchführen. Das Thema kann von Ihnen gesucht oder
von Professoren zur Verfügung gestellt werden.
Das Projekt Mobiles Bezahlsystem für Automaten (Plattform Android) wurde von Prof.
Dr. Nazareth betreut und durch die Studierende Ngoc Tho Nguyen, Odai Alasmar weiter
entwickelt und vervollständigt wird.
Die Projektgruppe bestand am Anfang aus 5 Studierenden, die aus Informatik- und
Wirtschaftinformatikstudiengang kommen. Aber nach einem halben Jahr verkürzt sich die
Nummer auf 2.

## Motivation der Projektgruppe
Die anfängliche Idee war, eine mobile Zahlungsanwendung zu erstellen, die die aktuelle
Zahlungsmethode in Automaten ersetzt, die nur Bargeld unterstützt.
Aus der ursprünglichen Anwendung hat das Entwicklungsteam weiterhin daran bearbeitet,
um die anderen gründlichen Funktionen jeder Zahlungsanwendung hinzuzufügen
wie Registrierung, Aktualisierung der Benutzerinformationen und insbesondere die
Aufladefunktion.
Das Team glaubt an eine vielversprechende Zukunft des Einsatzes der Anwendung.

##  Zielsetzung der Projektgruppe
Das Ziel der Projektgruppe ist die Entwicklung einer neue Bezahlmethode mit dem
Smartphone und dies auf eine einfachere, sichere und schnelle Methode.
Dabei sollen sowohl Endbenutzter und Betreiber von Automaten die Vorteile einer
Bargeldlosen Bezahlung nutzen können.

# Projektorganisation
Als Kommunikationsmittel benutzen wir Whatsapp. Wir haben darauf eine Gruppe erstellt
und eine E-Mail an alle Mitglieder gesendet, um sie zum Beitritt einzuladen.
Danach wurden wir von Professor Nazareth empfohlen, Trello zu benutzen. Trello ist eine
web-basierte Projektmanagementsoftware, die vom Unternehmen Atlassian betrieben wird.
Aber wegen die Personalabbau benutzen wir es nicht mehr häufig und kehren daraufhin
wieder zurück, Whatsapp zu benutzen.

## Kollaborative Versionsverwaltung
Für Quellecodeverwaltung benutzen wir Github. Github ist einen am meisten bekannten
Dienst, die quelloffener Software zu verwalten. Er bietet auch viele Möglichkeiten, eine
Gruppe zu verwalten.
Im Github spielt ein Gruppenmitglied eine Rolle als Administrator. Er hat uns das Recht
reteilt, damit können wir gemeinsamen Quellcode bearbeiten.
Wenn wir Angelegenheiten haben oder Aufgaben zuordnen möchten, können wir auch es
auf Github benutzen.

## Scrum
Als relevantes Vorgehensmodel für die Projektgruppe wurde Scrum bestimmt, da es großen
Wert auf Entwicklung eines Produktes im Team legt und in Firmen sehr oft eingesetzt wird.
Wir einigten uns auf zweiwöchige Gruppentreffen und schilderten Probleme und Erfolge in
den eigenen Themengruppen.

# Anforderungen
Für das Projekt Mobiles Bezahlsystem für Automaten (Plattform Android) wurden stetig in
Absprache mit dem Betreuer, der als Auftraggeber agiert, spezifiziert oder neu definiert. Das
hatte zum einen mit der Stabilität und Sicherheit des Bezahlvorgangs zu tun. Des Weiteren
auch über die Integrität in bestehende Systeme.

## Funktionale Anforderungen
* Günstige Hardwareumrüstung Automaten*
* Sicherer Bezahlvorgang
* Der Automat braucht keine eigene Internetverbindung
* Echtzeitüberwachung der Kaufvorgänge für Betreiber
* Kostenvorteil für Betreiber

## Aufbau des Systems

### Android Handy

### Software
#### Entwicklungsumgebung für Server und Datenbank
XAMPP ist ein Programmpaket von freier Software und dient als einen virtuellen Webservers
Apache mit der Skriptsprache PHP und der Datenbank MySQL.
#### Entwicklungsumgebung für Android App
Android Studio 3.4.1

## Implementierung
### Android App
Wir haben die Kontostellungsfunktion wie folgt gestaltet:

In der Datenbank haben wir auf der kunde Tabelle 7 folgenden Spalten: KundenID,
Kundenname, Passwort, Telefon, Geburtsdatum, Guthaben, Email.

KundenID ist innerlich für Firma benutzt deswegen zeigen wir nicht auf
Kontostellungsaktivität, die 6 Spalten schreiben wir aber kname, guthaben logisch
kann man nicht ändern, deswegen haben wir deaktiviert, kann man nur Passwort, Telefon,
Geburtsdatum, Email eingeben, um zu ändern.

Geburtsdatum haben wir ein Pickdate benutzt, deswegen ist das Eigabedatum immer richtig.

Bei der Abmeldefunktion haben wir alle Aktivitäten gelöscht, damit man nicht auf der
Hauptseite zurückgreifen kann.

Wir übernehmen ein neues Design.

Wir ausrichten Aufladefunktion mit Zahlungsmittel von Paypal. Zuerst haben Sandboxkonto
von Paypal registriert, um Paypal zu integrieren. Es ist einen Tool von Paypal für
Entwicklungsphase und wenn es geht mit diesem Konto, geht es auch mit normalen Konten.
Es gibt 2 Konten von Sandbox, Buyer-Konto und Facilitator-Konto, wir hardcoden ID von
Facilitator-Konto in unserer App. Wenn der Benutzer Aufladekopf klicke und dann seinen
Buyer-Benutzername und Passwort eingeben, danach Betrag eingeben, wenn alles richtig
eingegeben wird, dann wird der Betrag von Buyer-Konto abgezogen und der Betrag auf
Facilitator auf der Datenbank zugenommen. Damit alles läuft, mussen wir PHP-Seite
ändern. Wenn er falschen Benutzername oder Passwort oder mehr Geld als er hat eingibt,
dann wird Fehler auftreten und bekommt er eine Benachrichtigung.

Bei Registrierungsfunktion implementieren wir folgende Validierungen für das Passwort,
die Email, Geburtsdatum, die Telefonnummer und Kundenname: Null-Validierung und
Format-Korrektheit-Validierung. Kundenname wird auf Einzigartigkeit überprüft. Wenn kein
Kundenname vorhanden ist, wird ein neues Konto erstellt und Guthaben mit 0 initialisiert.
Das Passowrt wird dabei immer als gehasht gepspeichert
### Server und Datenbank
Wir haben nicht viel die Datenbankstruktur geändert, außer das Passwortfeld in der Tabelle
Kunde und die MAC-Adresse bei der Tabelle Automat. Wegen Sicherheit möchten wir
gehashtes Passwort speichern. Deswegen haben wir die Größen für Passwortfeld geändert
(mininum 60 Zeichen). Wir haben auch die API in der PHP-Seite geändert, damit unsere
App das gehashte Passwort mit unverschlüsseltem Passwort übereinstimmen und die
Informationen von Benutzer abrufen können.

Wir wurden ein vom letzten Projekt unterschiedlichen Raspberry-Pi-Gerät übergeben, so
muss auch der Eintrag für die MAC-Adresse geändert werden.

### Raspberry Pi 3
Ein Phyton Programm wurde entwickelt main.py indem sich eine Tan-Liste befindet. Diese
sollte und könnte bei weiter Entwicklung vom Server erneuert werden. Das Programm prüft
ob sich ein Bluetooth Gerät mit dem Automaten verbindet und die Richtige Tan versendet.
Die Tan wird vom Server an die App weitergeleitet und bietet sohin Schutz vor Attacken.
Ist diese Bedingung erfüllt, verbindet sich der Raspberry Pi mit dem Mobilen Telefon über
Bluetooth und erhält vom Server über das Handy den erfolgreich Bezahlvorgang zum
Auswerfen des Produktes. Danach beendet es die Verbindung und wartet auf Autorisierte
Verbindungen.
