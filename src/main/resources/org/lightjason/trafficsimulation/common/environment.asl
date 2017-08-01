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

// initialize
+!main <-

    // grid size (100km with 4 lanes)
    simulation/initialize( 100, 4 );

    // default vehicle (maximum speed in km/h, acceleration in m/sec^2, deceleration in m/sec^2, lane index [0 right in driving direction])
    //vehicle/default/left( 250, 10, 20, 1 );

    // user vehicle (maximum speed in km/h, acceleration in m/sec^2, deceleration in m/sec^2)
    [ MaxSpeed | MaxAcceleration | MaxDeceleration ] = math/statistic/randomsimple(1, 1, 1);
    MaxSpeed = MaxSpeed * 125 + 150;
    MaxAcceleration = MaxAcceleration * 10 + 10;
    MaxDeceleration = -1 * MaxDeceleration * 15 + 15;
    vehicle/user( MaxSpeed, MaxAcceleration, MaxDeceleration )
.

// simulation loop
+!loop <-
    !loop
.

// plan to shutdown simulation execution
+!shutdown <- simulation/shutdown.

// plan on collision execution
+!collision <- simulation/shutdown.
