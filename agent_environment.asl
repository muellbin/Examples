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
    NewPosition = MyPosition + 1;
    NewPosition = NewPosition % EnvSize;
    !!move( NewPosition )
.


/**
 * plan to move the agent to another cell
 *
 * @param X new position
 */
+!move(X) <-
    generic/print( "agent", MyName, "is on position", MyPosition, "move to ", X );
    env/move( X );
    X++;
    X = X % EnvSize;
    !move( X )
.


/**
 * if move-plan fails this plan will be triggered
 * @param X
 */
-!move(X) <-
    Y = MyPosition - 1;
    Y = Y < 0 ? EnvSize + Y : Y;
    generic/print( "agent", MyName, "cannot move from", MyPosition, "to ", X, "try to move to", Y );
    !move(Y)
.


/**
 * plan to get information about changing other agents
 *
 * @param X from position
 * @param Y to position
 */
+!other/agent-position/changed( from(X), to(Y) ) <-
    generic/print( "agent", MyName, "get information that other agent has moved from", X, "to", Y )
.
