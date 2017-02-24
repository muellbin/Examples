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

    // generates a random string
    R = generic/string/random( "abcdefghijklmnopqrstuvwxyz", 12 );

    // calls actions and sends generated messages to "agent 0", agent names
    // are defined by the Java object / agent generator
     message/send("agent 0", R)

.


/**
 * receive plan which is execute iif a message is received,
 * plan trigger-literal is defined by the send action
 *
 * @param Message received message
 * @param AgentName name of the sending agent
 */
+!message/receive(  message( Message ), from( AgentName )  ) <-

    // variable MyName is set by the variable builder, Cycle is set by default, other variables will be unified
    generic/print( MyName, " received message [", Message, "] from [", AgentName, "] in cycle [", Cycle, "]")

.
