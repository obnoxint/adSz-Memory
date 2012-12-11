# adSz - Memory

## Lizenz
Der Quellcode, die Bin�rdateien und die Ressourcen im Verzeichnis _/res_ des
Distributionsverzeichnisses unterliegen der GPLv3. Die Lizenz liegt in
englischer Sprache der Distribution bei. Eine deutschsprachige �bersetzung der
GPLv3 finden Sie hier: https://www.gnu.org/licenses/gpl-3.0.de.html

adSz-Memory nutzt die [LWJGL](http://www.lwjgl.org/) von Caspian Prince und
[Slick](http://slick.cokeandcode.com/) von Kevin Glass.

* [LWJGL Lizenz](http://www.lwjgl.org/license.php)
* [Slick Lizenz](http://slick.cokeandcode.com/static.php?page=license)

Die der Distribution beiliegenden Beispiele im Verzeichnis _/cards_
unterliegen keiner Lizenz.

## Haftungsausschluss

Die Software, ihr Quellcode und ihre Dokumentation wird "wie sie ist" und ohne
jede Gew�hrleistung f�r Funktion, Korrektheit oder Fehlerfreiheit zur Verf�gung
gestellt.

F�r jedweden direkten oder indirekten Schaden - insbesondere Schaden an anderer
Software, Schaden an Hardware, Schaden durch Nutzungsausfall und Schaden durch
Funktionsunt�chtigkeit der Software, kann der Autor nicht haftbar gemacht
werden. Ausschlie�lich der Benutzer haftet f�r die Folgen der Benutzung dieser
Software.

Diese Software wurde mit gr��ter Sorgfalt entwickelt, jedoch k�nnen Fehler
niemals ausgeschlossen werden. Es kann daher keine Gew�hr f�r die Sicherheit
Ihrer Daten �bernommen werden.

## Quellcode
Der Quellcode der Anwendung befindet sich im Verzeichnis _/src_ innerhalb der
ausgelieferten .jar-Datei. Dar�ber hinaus steht die jeweils aktuellste Version
des Quellcodes unter folgender Adresse zur Verf�gung:
https://github.com/obnoxint/adSz-Memory

## Systemanforderungen
Zus�tzlich zu den Systemanforderungen Ihres Betriebssystems bestehen folgende
Anforderungen zum Ausf�hren von adSz-Memory:

Software:

* [Oracle Java Runtime Environment](http://www.java.com/de/download/) (JRE)
Version 1.7 oder h�her
* OpenGL-kompatibler Grafikkartentreiber

Hardware:

* 128 MB Arbeitsspeicher
* 1 GHz CPU
* 16 MB Festplattenspeicher
* Maus oder vergleichbares Eingabeger�t

## Installation
1. Laden Sie die jeweils aktuellste Distribution von der Seite
[_Downloads_](https://github.com/obnoxint/adSz-Memory/downloads) herunter.
2. Entpacken Sie den Inhalt der .zip-Datei in ein beliebiges Verzeichnis. Achten
Sie darauf, dass Ihr Benutzerkonto f�r dieses Verzeichnis �ber Lese- und
Schreibrechte verf�gt.

## Starten der Anwendung
Wenn die Java Runtime Environment (JRE) korrekt installiert und konfiguriert
wurde, k�nnen Sie die Anwendung mit einem Doppelklick auf die .jar-Datei
starten.

Anderenfalls k�nnen Sie auch die beigef�gte .bat-Datei (f�r Windows-Nutzer) oder
.sh-Datei (f�r Linux-/Mac-Nutzer) verwenden. Dies setzt allerdings voraus, dass
die PATH-Umgebungsvariable korrekt auf die installierte JRE verweist.

Bitten Sie ggf. Ihren Systemadministrator um Unterst�tzung.

## �ber das Spiel
adSz-Memory ist eine Rekonstruktion des bekannten Kartenspiels _Memory_ f�r
PC/Mac.

Das Spielfeld besteht aus einer zuvor ausgew�hlten Anzahl von Kartenpaaren, die
verdeckt und willk�rlich auf dem Spielfeld verteilt werden. Als Kartenpaar
gelten im Falle von adSz-Memory zwei optisch identische Karten.

Je Spielzug deckt der Spieler zwei beliebige Karten auf. Sind die Karten
identisch, bleiben sie offen liegen und der Spieler setzt das Spiel mit dem
n�chsten Zug fort. Deckt der Spieler w�hrend eines Spielzuges kein Kartenpaar
auf, so sollte er sich die Position der gerade aufgedeckten Karten merken und
beide Karten werden wieder verdeckt.

Das Spiel endet, wenn der Spieler alle Kartenpaare aufgedeckt hat. Ziel des
Spiels ist, alle Kartenpaare in m�glichst kurzer Zeit und mit m�glichst wenigen
Spielz�gen aufzudecken.

## Kartenpaare und Bewertung der Spielleistung
Hat der Spieler das Spielziel erreicht wird ihm eine Leistungsbewertung
angezeigt. Diese richtet sich nach der Anzahl der Kartenpaare, der Dauer des
Spiels und der Anzahl der Spielz�ge in denen kein Kartenpaar aufgedeckt wurde.

adSz-Memory unterst�tzt 17 verschiedene Auswahlm�glichkeiten f�r die Anzahl der
Kartenpaare auf dem Spielfeld.

<table width="300px" align="center">
  <tr align="center">
      <td width="10%"><b>#</b></td><td width="50%"><b>Kartenpaare</b></td><td width="20%"><b>horizontal</b></td><td width="20%"><b>vertikal</b></td>
  </tr>
  <tr align="center">
    <td><b>1</b></td><td>3</td><td>3</td><td>2</td>
  </tr>
  <tr align="center">
    <td><b>2</b></td><td>4</td><td>4</td><td>2</td>
  </tr>
  <tr align="center">
    <td><b>3</b></td><td>5</td><td>5</td><td>2</td>
  </tr>
  <tr align="center">
    <td><b>4</b></td><td>6</td><td>4</td><td>3</td>
  </tr>
  <tr align="center">
    <td><b>5</b></td><td>7</td><td>7</td><td>2</td>
  </tr>
  <tr align="center">
    <td><b>6</b></td><td>8</td><td>4</td><td>4</td>
  </tr>
  <tr align="center">
    <td><b>7</b></td><td>9</td><td>6</td><td>3</td>
  </tr>
  <tr align="center">
    <td><b>8</b></td><td>10</td><td>5</td><td>4</td>
  </tr>
  <tr align="center">
    <td><b>9</b></td><td>12</td><td>6</td><td>4</td>
  </tr>
  <tr align="center">
    <td><b>10</b></td><td>14</td><td>7</td><td>4</td>
  </tr>
  <tr align="center">
    <td><b>11</b></td><td>15</td><td>6</td><td>5</td>
  </tr>
  <tr align="center">
    <td><b>12</b></td><td>16</td><td>8</td><td>4</td>
  </tr>
  <tr align="center">
    <td><b>13</b></td><td>18</td><td>6</td><td>6</td>
  </tr>
  <tr align="center">
    <td><b>14</b></td><td>20</td><td>8</td><td>5</td>
  </tr>
  <tr align="center">
    <td><b>15</b></td><td>21</td><td>7</td><td>6</td>
  </tr>
  <tr align="center">
    <td><b>16</b></td><td>24</td><td>8</td><td>6</td>
  </tr>
  <tr align="center">
    <td><b>17</b></td><td>28</td><td>8</td><td>7</td>
  </tr>
</table>

Die Bewertung der Spielleistung erfolgt in Punkten und in Prozent. 100%
entspricht dabei einer Punktzahl von (Paare * 100). Die Punktzahl wird wie folgt
errechnet:

<p align="center">Punktzahl = (Paare * 100) - (Spieldauer in Sekunden - Verz�gerung durch anzeigen aufgedeckter Karten) - (Fehler * 10)</p>

Da eine Bewertung mit 100% praktisch nicht erreicht werden kann, wird zus�tzlich
eine verbale Bewertung angezeigt, die besonders f�r sehr junge Spieler als
repr�sentativer zu betrachten ist.

<table width="300px" align="center">
  <tr align="center">
      <td width="10%"><b>#</b></td><td width="20%"><b>Leistung</b></td><td width="70%"><b>Bewertung</td>
  </tr>
  <tr align="center">
    <td><b>1</b></td><td>ab 90%</td><td>Wow!<br />Sehr beeindruckend!</td>
  </tr>
  <tr align="center">
    <td><b>2</b></td><td>ab 80%</td><td>Hervorragend!</td>
  </tr>
  <tr align="center">
    <td><b>3</b></td><td>ab 60%</td><td>Sehr gut!</td>
  </tr>
  <tr align="center">
    <td><b>4</b></td><td>ab 40%</td><td>Gut!<br />Aber das kannst Du besser!</td>
  </tr>
  <tr align="center">
    <td><b>5</b></td><td>ab 20%</td><td>�be weiter!<br />Das kannst Du besser!</td>
  </tr>
  <tr align="center">
    <td><b>6</b></td><td>ab 0%</td><td>Schade!<br />Gib nicht auf und versuche es nochmal!</td>
  </tr>
</table>

## Konfiguration
Die Konfiguration erfolgt zum einen durch Ablegen von Bilddateien im Verzeichnis
/cards und zum anderen durch Bearbeiten der Datei game.properties.

Bitte starten Sie die Anwendung erneut, wenn Sie �nderungen w�hrend der
Programmlaufzeit vorgenommen haben.

### Konfigurieren von Karten
Die Bilddateien f�r Kartentexturen werden im Verzeichnis /cards ablegt. Ihr
Dateiname kann frei gew�hlt werden, sollte allerdings nur Buchstaben, Zahlen und
Unterstriche enthalten. Ausnahme ist die Bilddatei "\_hidden.png", welche die
Textur f�r verdeckte Karten enth�lt.

Die Bilddateien f�r Kartentexturen m�ssen folgende Anforderungen erf�llen:

* Abz�glich der Textur f�r verdeckte Karten ("\_hidden.png") m�ssen mindestens
drei Texturen vorhanden sein.
* Die Bilddatei muss im Portable Network Graphics Format (PNG) vorliegen.
* Die Bilddatei muss exakt 96 Pixel hoch und 96 Pixel breit sein.
* Es ist empfehlenswert auf Transparenz zu verzichten.

Um eine bestimmte Anzahl von Kartenpaaren im Spiel ausw�hlen zu k�nnen, muss
eine entsprechende Anzahl von g�ltigen Texturen im Verzeichnis /cards vorhanden
sein. Anderenfalls kann nur jeweils die h�chste Anzahl der Kartenpaare
ausgew�hlt werden, f�r die das Programm entsprechende Texturen finden kann. Sind
z.B. 11 Texturen vorhanden k�nnen h�chstens 10 Kartenpaare ausgew�hlt werden.
Sind mehr Texturen vorhanden als Kartenpaare im Spiel ausgew�hlt wurden, werden
die zu verwendenden Texturen von der Anwendung vor jedem Spiel zuf�llig
ausgew�hlt.

### Einstellungen in der Datei game.properties

Die Datei game.properties ist eine einfache Textdatei, die mit einem beliebigen
Texteditor bearbeitet werden kann. Sie enth�lt folgende Optionen:

__pairs__: Diese Option enth�lt standardm��ig keinen Wert. Mit dieser Option
k�nnen die Auswahlm�glichkeiten der Kartenpaare im Spiel eingeschr�nkt werden.
Ist kein Wert angegeben, entscheidet die Anwendung anhand der Anzahl der
verf�gbaren Texturen welche Auswahlm�glichkeiten verf�gbar sind.

Um die Auswahlm�glichkeiten einzuschr�nken, legen Sie die ausw�hlbare Anzahl der
Kartenpaare durch Kommas getrennt als Wert fest. Beispiel:

<p align="center">pairs=5,9</p>

In diesem Beispiel lassen sich nur die Anzahl von 5 oder 9 Kartenpaaren im Spiel
ausw�hlen. Alle anderen Auswahlm�glichkeiten stehen nicht zur Verf�gung auch
wenn eine ausreichende Anzahl von Texturen zur Verf�gung steht.

Achten Sie darauf, dass die angegebene Anzahl von Kartenpaaren tats�chlich
unterst�tzt wird (siehe Tabelle oben) und, dass gen�gend Texturen im Verzeichnis
/cards vorhanden sind. Anderenfalls zeigt die Anwendung eine Fehlermeldung und
wird beendet.

__hideDelay__: Diese Option enth�lt standardm��ig den Wert 1000. Mit dieser
Option legen Sie fest, wie viele Millisekunden die Karten, die kein Paar bilden,
nach einem Spielzug angezeigt werden, bevor sie wieder verdeckt werden und der
n�chste Spielzug m�glich wird.

Es wird empfohlen keinen Wert unter 500 zu verwenden oder den Wert f�r sehr
junge Spieler zu erh�hen.

## Unterst�tzung
Wenn Sie ein Problem melden oder einen Verbesserungsvorschlag einreichen
m�chten, nutzen Sie bitte den
[Issue-Tracker](https://github.com/obnoxint/adSz-Memory/issues) des Projekts
(GitHub-Konto erforderlich).

Bitte machen Sie im Fall eines Problemberichts folgende Angaben:

* Betriebssystem, -version und -architektur
* Programmversion
* Falls vorhanden, Inhalt der _error\_[Zahl].txt_-Datei.
* Welche Aktionen Sie durchgef�hrt haben, bevor der Fehler aufgetreten ist.

