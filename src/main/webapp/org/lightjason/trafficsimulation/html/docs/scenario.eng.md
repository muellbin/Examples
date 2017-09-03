The goal of the simulation is to realise a traffic scenario by means of a [multi-agent system (MAS)](https://en.wikipedia.org/wiki/Multi-agent_system).
The scenario comprises the following problem

> A traffic scenario is shown which contains an agent vehicle (green) that should be programmed by the user, so that the vehicle drives optimally.
> Optimal is defined as driving as fast as you can, while being traffic rule compliant, i.e. not exceeding the speed limit.

The following events are available to the vehicle

* ```area/enter``` the vehicle enters a new area with a new speed definition.
* ```area/leave``` the vehicle leaves an area.

The following actions are available to the vehicle

* ```vehicle/accelerate( a )``` the vehicle accelerates with the factor $a \in [0,1]$,
    with $a=1$ being the maximum acceleration value. If the vehicle cannot accelerate anymore the action
    will fail.
* ```vehicle/decelerate( b )``` the vehicle decelerates with the factor $b \in [0,1]$,
    with $b=1$ being the maximum deceleration value. If the vehicle cannot decelerate anymore the action
    will fail.
* ```vehicle/pullout``` the vehicle swings out to left in driving direction.
* ```vehicle/pullin``` the vehicle goes back to right in driving direction.

The agent contains the following bounded constants

* ```ÌD``` agent unique name
* ```CurrentSpeed``` the current speed of the vehicle in $km/h$
* ```CurrentLane``` the current lane index (starts with 1 from top)
* ```Acceleration``` the acceleration value in $m/sec^2$
* ```Deceleration``` the deceleration value in $m/sec^2$

The agent beliefs can be shown within the UI widget _beliefs_ during runtime.