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

+!vehicle/move( vehicle(V), speed(S), distance(D) )
    : S > AllowedSpeed <-
        generic/print( "#Area Agent Move", "Vehicle Movement", S, D )

        // Value = Penality Value
        // Inverse = 0.5 * ( 1 - Value )
        // P = math/statistic/linearselection( ["penalty", "slowdriving", "nothing"], [Value, Inverse, Inverse] )
        // !!P(S, V)
.


+!vehicle/leave(V) <-
    generic/print( "#Area Agent Leave", "vehicle leave area" )
.


//+!penaly(V,S) <-
//+!slowdriving(V,S) <-
+!donothing(V,S) <- success.

// probability to act: 1 - 1/ ( 1+ exp( - smooth * ( x - ( targetspeed + procent_targetspeed ) ) ) )
// penalty: ( targetspeed/10 / targetspeed * speeddifference )^4
