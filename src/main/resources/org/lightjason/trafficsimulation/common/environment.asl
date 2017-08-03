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


// --- rules ---------------------------------------------------------------------------------------------------------------------------------------------------

/**
 * function to generate vehcile data
 * @param MaxSpeed speed in km/h
 * @param MaxAcceleration in m/sec^2
 * @param MaxDeceleration in m/sec^2
 */
vehicledata( MaxSpeed, MaxAcceleration, MaxDeceleration ) :-
    [ Speed | Acceleration | Deceleration ] = math/statistic/randomsimple( 1, 1, 1 );

    Speed += 0.5;
    Acceleration += 0.15;
    Deceleration += 0.25;

    MaxSpeed = Speed * MaxSpeed + 0.5 * MaxSpeed;
    MaxAcceleration = Acceleration * MaxAcceleration + 0.25 * MaxAcceleration;
    MaxDeceleration = Deceleration * MaxDeceleration + 0.25 * Deceleration
.

// -------------------------------------------------------------------------------------------------------------------------------------------------------------



// --- plans ---------------------------------------------------------------------------------------------------------------------------------------------------


// --- initializing plan ---
+!main <-

    // grid size (100km with 2x2 lanes for each direction)
    simulation/initialize( 100, 2, 2 );
    generic/print( "#Environment Agent", "grid has been created" );

    !defaultvehicle( 3 );
    !uservehicle

    //area/create( 0, 1000, 1, 4, 50 );
.


// --- creating default-vehicle plan ---
+!defaultvehicle( Lane ) <-
    MaxSpeed = 120;
    MaxAcceleration = 15;
    MaxDeceleration = 25;

    Position = math/statistic/randomsimple();
    Position *= 40000;

    $vehicledata( MaxSpeed, MaxAcceleration, MaxDeceleration );
    vehicle/default/position( MaxSpeed, MaxAcceleration, MaxDeceleration, Lane, Position );

    generic/print( "#Environment Agent", "default vehicle generated" );
    !defaultvehicle( 3 )
.

-!defaultvehicle( Lane ) <- !defaultvehicle( Lane ).


// --- creating and repair user-vehicle plan ---
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
