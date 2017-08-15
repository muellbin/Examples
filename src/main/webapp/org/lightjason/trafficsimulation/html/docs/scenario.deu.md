Ziel der Simulation ist es eine ein [Multi-Agenten System (MAS)](https://de.wikipedia.org/wiki/Multiagentensystem) zu beschreiben.
Das Szenario umfasst folgende Problemstellung

> Es ist ein Verkehrssystem beschrieben, in dem ein Agenten-Fahrzeug (grün) durch den Benutzer _programmiert_ werden soll,
> so dass es _optimal_  die Strecke von 100km abfährt. Optimal bedeutet, dass es sich regelkonform verhält, d.h. die
> Geschwindigkeit einhält aber trotzdem so schnell wie möglich die Strecke bewältigt.

Folgende Events stehen dem Fahrzeug zur Verfügung:

* ```area/enter``` das Fahrzeug betritt einen neuen Bereich mit ggf. veränderter Geschwindigkeit
* ```area/leave``` das Fahrzeug verlässt einen Bereich

Folgende Aktionen stehen dem Fahrzeug zur Verfügung

* ```vehicle/accelerate( <Beschleunigungsfaktor> )``` das Fahrzeug kann beschleunigt werden,
  der Faktor in [0,1] anzugeben, wobei 1 maximale Beschleinigung ist, kann das Fahrzeug nicht
  mehr beschleunigt werden, schlägt diese Aktion fehl
* ```vehicle/decelerate( <Bremsfaktor> )``` das Fahrzeug kann gebremst werden,
  der Faktor in [0,1] anzugeben, wobei 1 maximale Bremskraft ist, kann das Fahrzeug nicht
  mehr abgebremst werden, schlägt diese Aktion fehl
* ```vehicle/swingout``` lässt das Fahrzeug nach links in Fahrtrichtung ausscheren
* ```vehicle/goback``` lässt das Fahrzeug nach rechts in Fahrtrichtung einscheren
