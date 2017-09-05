Ziel der Simulation ist es ein Verkehrsszenario mit einem [Multi-Agenten System (MAS)](https://de.wikipedia.org/wiki/Multiagentensystem) zu realisieren.
Das Szenario umfasst folgende Problemstellung

> Es soll ein Verkehrssystem beschrieben werden, in dem ein Agenten-Fahrzeug (grün) durch den Benutzer _programmiert_ werden soll, so dass es _optimal_ die
> Strecke  abfährt. Optimal bedeutet, dass es sich regelkonform verhält, d.h. die Geschwindigkeitsbegrenzung einhält aber trotzdem so schnell wie möglich die
> Strecke bewältigt.

Folgende Events werden dem Fahrzeug zur Verfügung gestellt:

* ```area/enter( allowedspeed(S), length(D) )``` das Fahrzeug betritt einen neuen Bereich mit ggf. veränderter Geschwindigkeit.
* ```area/leave``` das Fahrzeug verlässt einen Bereich.

Folgende Aktionen stehen dem Fahrzeug zur Verfügung

* ```vehicle/accelerate( a )``` das Fahrzeug beschleunigt um den Faktor $a \in [0,1]$, wobei $a=1$ die maximale Beschleinigung ist. Kann das Fahrzeug nicht mehr beschleunigt werden, schlägt diese Aktion fehl.
* ```vehicle/decelerate( b )``` das Fahrzeug bremst um den Faktor $b \in [0,1]$, wobei $b=1$ die maximale Bremskraft ist. Kann das Fahrzeug nicht mehr abgebremst werden, schlägt diese Aktion fehl.
* ```vehicle/pullout``` lässt das Fahrzeug nach links in Fahrtrichtung ausscheren.
* ```vehicle/pullin``` lässt das Fahrzeug nach rechts in Fahrtrichtung einscheren.

Der Agent besitzt automisch folgende gebundene Konstanten

* ```ÌD``` seinen eigenen Namen (eindeutiger Identifier)
* ```CurrentSpeed``` aktuelle Geschwindigkeit $km/h$
* ```CurrentLane``` aktuelle Fahrspurnummer (beginnend mit 1 von oben)
* ```Acceleration``` Beschleunigungswert $m/sec^2$
* ```Deceleration``` Verzägerungswert $m/sec^2$

Die Beliefs des Agenten können zur Laufzeit über das UI Widget ```Beliefs``` angesehen werden.