/*
 * @cond LICENSE
 * ######################################################################################
 * # LGPL License                                                                       #
 * #                                                                                    #
 * # This file is part of the LightJason                                                #
 * # Copyright (c) 2015-16, LightJason (info@lightjason.org)                            #
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


// initial-goal
!main.


// initial plan (triggered by the initial goal)
+!main <-

    // generates a random string with a random length with maximum 12 and minimum 4
    I = math/statistic/randomsimple() * 12 + 4;
    R = string/random( "abcdefghijklmnopqrstuvwxyz", I );

    // run standalone-action and get return value
    N = my/standalone-action( R );

    // run object-action and get return value
    L = my/object-action( N );

    // just print the results
    generic/print( "agent uses string", R, "gets from standalone action", N, "and from object action", L )
.

