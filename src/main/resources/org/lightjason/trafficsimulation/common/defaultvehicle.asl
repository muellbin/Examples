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

!drive.

// --- driving call equal to Nagel-Schreckenberg driving model, on success accelerate ---
+!drive
    : ~>>allowedspeed(_) <-
		!!linger;
    	vehicle/accelerate(1);
        !driveright;
    	!drive

	: >>allowedspeed(S) <-
    	CurrentSpeed < S;
        !!linger;
    	vehicle/accelerate(1);
        !driveright;
    	!drive
.


// --- linger possibility ---
+!linger <-
	L = math/statistic/randomsimple;
    L > 0.3
.


// --- on driving failing decelerate ---
-!drive <-
    vehicle/decelerate(1);
    !drive
.


// --- try to drive-right ---
+!driveright <-
    L = math/statistic/randomsimple;
    L >= 0.35;
    vehicle/pullin
.


// --- possible collision decelerate ---
+!vehicle/collision <- vehicle/pullout.


// --- pull-out fails then decelerate ---
-!vehicle/collision <- vehicle/decelerate(1).
