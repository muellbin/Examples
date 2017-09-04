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

// driving vehicle with Nagel-Schreckenberg model
+!drive

	// if we don't know the allowed speed, try to accelerate
    : ~>>allowedspeed(_) <-
    	vehicle/accelerate(1);
    	!drive

	// if we know the allowed speed, try to accelerate
	: >>allowedspeed(S) <-
    	CurrentSpeed < S;
    	vehicle/accelerate(1);
    	!drive
.


// if anything goes wrong on driving, decelerate
-!drive <-
    vehicle/decelerate(1);
    !drive
.


// we know the we enter a new area
+!area/enter( allowedspeed(S), length(D) )

	// if we have got an information about the allowed speed,
    // we replaced it with the new information
	: >>allowedspeed(X) <-
    	-allowedspeed(X);
        +allowedspeed(S)

	// if we have got no information about the current allowed
    // speed, we put it in our beliefbase
    : ~>>allowedspeed(_) <-
    	+allowedspeed(S)
.


// we ignoring the information, that we leave an area
+!area/leave <- success.


// give some information to the ui, that the allowed speed has been changed
+allowedspeed(S) <-
	generic/print( "#Vehicle", string/concat( "maximum speed has been set to [", S, "]" ) )
.


// give some information to the ui, that the allowed speed has been changed
-allowedspeed(S) <-
   generic/print( "#Vehicle", string/concat("maximum speed [", S, "] has been removed") )
.
