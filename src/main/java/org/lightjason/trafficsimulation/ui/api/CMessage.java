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

import com.codepoetics.protonpack.StreamUtils;
import org.apache.commons.lang3.tuple.ImmutablePair;
import org.eclipse.jetty.websocket.api.Session;
import org.lightjason.trafficsimulation.common.CConfiguration;
import org.lightjason.trafficsimulation.ui.IWebSocket;

import java.util.Locale;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.CopyOnWriteArraySet;
import java.util.stream.Collectors;
import java.util.stream.Stream;


/**
 * websocket for any message
 */
public final class CMessage extends IWebSocket.IBaseWebSocket
{
    /**
     * default message delay
     */
    private static final int DELAY = CConfiguration.INSTANCE.getOrDefault( 2500, "main", "messagedelay", "default" );
    /**
     * current websocket connections
     */
    private static final Set<CMessage> CONNECTIONS = new CopyOnWriteArraySet<>();

    /**
     * ctor
     */
    public CMessage()
    {
        super( ( i, j ) ->
        {

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
     * message type
     */
    public enum EType
    {
        NOTICE,
        INFO,
        SUCCESS,
        ERROR
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

        private CInstance()
        {
        }

        /**
         * writes a message
         *
         * @param p_type type
         * @param p_title title
         * @param p_message message
         * @return self reference
         */
        public final CInstance write( final EType p_type, final String p_title, final String p_message )
        {
            return this.write( DELAY, p_type, p_title, p_message );
        }


        /**
         * write message
         *
         * @param p_delay delay
         * @param p_type type
         * @param p_title title
         * @param p_message message
         * @return self reference
         */
        public final CInstance write( final int p_delay, final EType p_type, final String p_title, final String p_message )
        {
            final Map<Object, Object> l_data = StreamUtils.zip(
                Stream.of( "delay", "type", "title", "text" ),
                Stream.of( p_delay, p_type.toString().toLowerCase( Locale.ROOT ), p_title, p_message ),
                ImmutablePair::new
            ).collect( Collectors.toMap( ImmutablePair::getLeft, ImmutablePair::getRight ) );

            CONNECTIONS.parallelStream().forEach( i -> i.send( l_data ) );
            return this;
        }

    }
}
