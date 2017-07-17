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


package org.lightjason.trafficsimulation.ui.api;

import org.lightjason.trafficsimulation.common.ITree;
import org.lightjason.trafficsimulation.ui.IWebSocket;

import java.util.function.BiConsumer;


/**
 * class to define control-flow
 */
public final class CControlFlow extends IWebSocket.IBaseWebSocket
{
    /**
     * ctor
     *
     * @param p_messageconsumer message consumer
     */
    protected CControlFlow( final BiConsumer<ITree, IWebSocket> p_messageconsumer )
    {
        super( ( i, j ) ->
        {

        } );
    }
}
