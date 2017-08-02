/*
 * @cond LICENSE
 * ######################################################################################
 * # LGPL License                                                                       #
 * #                                                                                    #
 * # This file is part of the LightJason AgentSpeak(L++) Traffic-Simulation             #
 * # Copyright (c) 2017, LightJason (info@lightjason.org)                               #
 * # This program is free software: you can redistribute it and/or modify               #
 * # it under the terms of the GNU Lesser General Public License as                     #
 * # published by the Free Software Foundation, either version 3 of the                 #
 * # License, or (at your option) any later version.                                    #
 * #                                                                                    #
 * # This program is distributed in the hope that it will be useful,                    #
 * # but WITHOUT ANY WARRANTY; without even the implied warranty of                     #
 * # MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the                      #
 * # GNU Lesser General Public License for more details.                                #
 * #                                                                                    #
 * # You should have received a copy of the GNU Lesser General Public License           #
 * # along with this program. If not, see http://www.gnu.org/licenses/                  #
 * ######################################################################################
 * @endcond
 */

!main.


// function to generate maximum speed in km/h, acceleration in m/sec^2, deceleration in m/sec^2
vehicledata( MaxSpeed, MaxAcceleration, MaxDeceleration ) :-
    [ Speed | Acceleration | Deceleration ] = math/statistic/randomsimple(1, 1, 1);

    Speed = Speed + 0.5;
    Acceleration = Acceleration + 0.15;
    Deceleration = Deceleration + 0.25;

    MaxSpeed = Speed * MaxSpeed + 0.5 * MaxSpeed;
    MaxAcceleration = Acceleration * MaxAcceleration + 0.25 * MaxAcceleration;
    MaxDeceleration = Deceleration * MaxDeceleration + 0.25 * Deceleration
.


// initialize
+!main <-

    // grid size (100km with 2x2 lanes for each direction)
    simulation/initialize( 100, 2, 2 );
    generic/print( "#Environment Agent", "grid has been created" );

    !defaultvehicle
    //!uservehicle

    //area/create( 0, 1000, 1, 4, 50 );
.

+!defaultvehicle <-
    MaxSpeed = 120;
    MaxAcceleration = 15;
    MaxDeceleration = 25;

    $vehicledata( MaxSpeed, MaxAcceleration, MaxDeceleration );
    vehicle/default/left( MaxSpeed, MaxAcceleration, MaxDeceleration, 1 );

    generic/print( "#Environment Agent", "default vehicle generated" )
.

-!defaultvehicle <- !defaultvehicle.


+!uservehicle <-
    MaxSpeed = 200;
    MaxAcceleration = 15;
    MaxDeceleration = 25;

    $vehicledata( MaxSpeed, MaxAcceleration, MaxDeceleration );
    vehicle/user( MaxSpeed, MaxAcceleration, MaxDeceleration );

    generic/print( "#Environment Agent", "user vehicle has been created" )
.

-!uservehicle <- !uservehicle.



// plan to shutdown simulation execution
+!shutdown <-
    generic/print( "environment message", "shutdown plan has been called" );
    simulation/shutdown
.

// plan on collision execution
+!collision <-
    generic/print( "environment message", "collision plan hash been called" );
    simulation/shutdown
.

+!penalty(N) <-
    +value(N);
    !finish
.

+!finish <-
    simulation/penalty( 10 )
.
