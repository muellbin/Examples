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
    generic/print("Hello World on agent", MyName);
    !mynextgoal
.


// plan with is run every cycle
+!mynextgoal <-
    generic/print( "Hello World again on agent", MyName );
    !mynextgoal
.


/**
 * trigger-plan which is called by the trigger-method call
 *
 * @param X any value which is pushed by the trigger call
 */
+!special-goal(X) <-
    generic/print( "special goal with value", X, "triggered on agent", MyName )
.
