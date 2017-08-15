The goal of this simulation is a description of a [multi-agent system (MAS)](https://en.wikipedia.org/wiki/Multi-agent_system).
The scenario defines the following problem

> A traffic management is shown which contains an agent vehicle (green) that should be programmed by the user, so that
> the vehicle should drive optimal. Optimal is defined as compliant with the traffic rules, so not faster than the
> allowed speed but fast as you can.

The following events are available for the vehicle

* ```area/enter``` the vehicle enters a new area with a new speed defintion
* ```area/leave``` the vehicle leavs an area 

The following actions are available for the vehicle

* ```vehicle/accelerate( <acceleration factor> )``` the vehicle accelerate with the factor [0,1],
    with 1 is the maximum acceleration value, if the vehcicle cannot accelerate anymore the action
    will fail
* ```vehicle/decelerate( <deceleration factor> )``` the vehicle can be decelerated with the factor [0,1],
    with 1 defines the maximum deceleration value, if the vehicle cannot decelerate anymore the action
    will fail
* ```vehicle/swingout``` the vehicle swings out to left in driving directory
* ```vehicle/goback``` the vehicle goes back to right in driving directory
