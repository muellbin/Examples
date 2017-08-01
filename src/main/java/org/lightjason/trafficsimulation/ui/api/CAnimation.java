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

import org.eclipse.jetty.websocket.api.Session;
import org.lightjason.trafficsimulation.elements.IMap;
import org.lightjason.trafficsimulation.elements.IObject;
import org.lightjason.trafficsimulation.runtime.CRuntime;
import org.lightjason.trafficsimulation.ui.IWebSocket;

import javax.annotation.Nonnull;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.CopyOnWriteArraySet;


/**
 * websocket for animation
 */
public final class CAnimation extends IWebSocket.IBaseWebSocket
{
    /**
     * current websocket connections
     */
    private static final Set<CAnimation> CONNECTIONS = new CopyOnWriteArraySet<>();

    /**
     * ctor
     */
    public CAnimation()
    {
        super( ( i, j ) ->
        {
            final IObject<?> l_object = CRuntime.INSTANCE.element( i.get( "id" ) );
            if ( l_object != null )
                j.send( l_object.map( IMap.EStatus.EXECUTE ) );
        } );
    }


    @Override
    public final void onWebSocketConnect( final Session p_session )
    {
        CONNECTIONS.add( this );
        super.onWebSocketConnect( p_session );
    }

    @Override
    public final void onWebSocketClose( final int p_statuscode, final String p_reason )
    {
        CONNECTIONS.remove( this );
        super.onWebSocketClose( p_statuscode, p_reason );
    }

    /**
     * singleton instance of all websocket connections
     */
    public static final class CInstance
    {
        /**
         * singleton instance
         */
        public static final CInstance INSTANCE = new CInstance();

        /**
         * ctor
         */
        private CInstance()
        {
        }

        /**
         * sends an object
         *
         * @param p_status status of the object
         * @param p_object object
         */
        public final void send( @Nonnull final IMap.EStatus p_status, final IMap p_object )
        {
            final Map<String, Object> l_data = p_object.map( p_status );
            if ( !l_data.isEmpty() )
                CONNECTIONS.parallelStream().forEach( i -> i.send( l_data ) );
        }

    }

}
