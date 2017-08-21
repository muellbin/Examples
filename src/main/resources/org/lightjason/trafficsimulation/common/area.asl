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


// calculates the penality probability with the formula: 1 / ( 1 + exp( -( CurrentSpeed - ( AllowedSpeed + 0.1 * AllowedSpeed ) ) ) )
penalityprobability( S, P ):-
	P = AllowedSpeed + 0.1 * AllowedSpeed;
    P = S - P;
    P *= -1;
    P = math/exp( P );
    P += 1;
    P = 1 / P
.


+!vehicle/move( vehicle(V), speed(S), distance(D) ) <-
	Penality = 0;
   	$penalityprobability( S, Penality );
    InverseHalfPenality = 1 - Penality;
    InverseHalfPenality *= 0.5;

    P = math/statistic/linearselection( ["penalty", "slow", "nothing"], [Penality, InverseHalfPenality, InverseHalfPenality] );
    !!P( V, S )
.


+!vehicle/leave(V) <-
    generic/print( "#Area Agent Leave", "vehicle leave area" )
.


+!penalty( V, S ) <-
	generic/print( "#Area Penalty", S )
.

+!slow( V, S ) <-
	generic/print( "#Area Slowdriving", S )
.

+!nothing( V, S ) <- success.

// penalty: ( targetspeed/10 / targetspeed * speeddifference )^4
