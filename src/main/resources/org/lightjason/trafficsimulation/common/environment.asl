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

    Speed += 0.2;
    Acceleration += 0.15;
    Deceleration += 0.3;

    MaxSpeed = Speed * MaxSpeed + 0.25 * MaxSpeed;
    MaxAcceleration = Acceleration * MaxAcceleration + 0.25 * MaxAcceleration;
    MaxDeceleration = Deceleration * MaxDeceleration + 0.25 * MaxDeceleration
.


areadata( From, To, Minimum, Maximum, MinimumSize ) :-
    [ P25 | P50 | P75 ] = math/statistic/multiplepercentile( PenaltyStatistic, 25, 50, 75 );
    PVariance = P25 / P75;
    PStd = P75 - P25
    PStd  /= P50;

    PMin = math/min( 1, PVariance, PStd )
.

// -------------------------------------------------------------------------------------------------------------------------------------------------------------



// --- plans ---------------------------------------------------------------------------------------------------------------------------------------------------

// --- initializing plan ---
+!main <-

    // grid size (100km with 2x2 lanes for each direction)
    simulation/initialize( 50, 2, 2 );
    generic/print( "#Environment Agent", "Street world has been created" );

    // create areas
    area/initialize( 125, 0, 3, 5, 15 );
    area/initialize( 145, 0, 3, 15, 25 );
    area/initialize( 150, 0, 3, 25, 35 );
    area/initialize( 175, 0, 3, 35, 45 );
    generic/print( "#Environment Agent", "Areas with different speed ranges have been created" );

    !defaultvehicle( 100, 10 )
.

-!main <-
    generic/print( "#Environment Agent", "Initializing fails, simulation shutdown" );
    simulation/shutdown
.



// --- creating and repair user-vehicle plan ---
+!uservehicle <-
    MaxSpeed = 260;
    MaxAcceleration = 6;
    MaxDeceleration = 7;

    $vehicledata( MaxSpeed, MaxAcceleration, MaxDeceleration );
    vehicle/user( MaxSpeed, MaxAcceleration, MaxDeceleration );
    generic/print( "#Environment Agent", "Your vehicle has been created" )

     //vehicle/default/position( 160, 2, 3, 2, 0 );
     //vehicle/default/position( 160, 2, 3, 2, 30 )
.

-!uservehicle <-
    generic/print( "#Environment Agent", "Cannot create your vehicle, try again..." );
    !uservehicle
.



// --- creating default-vehicle plan ---
+!defaultvehicle( Count, EndCycle )
	: EndCycle == 1 <- !uservehicle
    : EndCycle > 0 <-
        C = math/statistic/randomsimple;
        C *= Count;

        V = collection/list/range(0, C);
        @(V) -> I : {
            [Position | Lane] = math/statistic/randomsimple(1, 1);
            Position *= StreetPositions;
            Help = Lanes;
            Lane *= Help;

            MaxSpeed = 220;
            MaxAcceleration = 6;
            MaxDeceleration = 7;

            $vehicledata( MaxSpeed, MaxAcceleration, MaxDeceleration );
            vehicle/default/position( MaxSpeed, MaxAcceleration, MaxDeceleration, Lane, Position ) << true
        };

        generic/print( "#Environment Agent", string/concat( "Other vehicles are created in call [", EndCycle, "]" ) );
        EndCycle--;
        !defaultvehicle( Count, EndCycle )
.


// --- successful finishing plan to calculate penalty values ---
+!finish( V ) <-
    simulation/penalty( vehicle/penalty( V ) );
    generic/print( "#Environment Agent", "You have finished the tour successfully" );
    !!shutdown
.



// --- plan for user-collision execution ---
+!vehicle/usercollision( V ) <-
    PV = math/max( vehicle/penalty( V ), 1.25 );
    P = simulation/maxpenalty;
    P *= PV;
    simulation/penalty( P );
    generic/print( "#Environment Agent", "You have been crashed into another car, you get a high punishment" );
    !!shutdown
.



// --- plan to shutdown simulation execution / called by the UI ---
+!shutdown <-
    simulation/shutdown
.
