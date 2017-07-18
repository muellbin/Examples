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
import org.lightjason.trafficsimulation.elements.vehicle.IVehicle;
import org.lightjason.trafficsimulation.ui.IWebSocket;

import java.util.Map;
import java.util.Set;
import java.util.concurrent.CopyOnWriteArraySet;
import java.util.stream.Collectors;
import java.util.stream.Stream;


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
            CInstance.INSTANCE.initializegrid( 40, 4, 32 );
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

        private CInstance()
        {
        }

        /**
         * initialize grid
         *
         * @param p_width width
         * @param p_height heigth
         * @param p_cellsize cellsize
         * @return animation instance
         */
        public final CInstance initializegrid( final int p_width, final int p_height, final int p_cellsize )
        {
            final Map<Object, Object> l_data = StreamUtils.zip(
                Stream.of( "operation", "width", "height", "cellsize" ),
                Stream.of( "initializegrid", p_width, p_height, p_cellsize ),
                ImmutablePair::new
            ).collect( Collectors.toMap( ImmutablePair::getLeft, ImmutablePair::getRight ) );

            CONNECTIONS.parallelStream().forEach( i -> i.send( l_data ) );
            return this;
        }

        /**
         * generate vehicle
         * @param p_vehicle vehicle
         * @return animation insctance
         */
        public IVehicle generatevehicle( final IVehicle p_vehicle )
        {
            final Map<Object, Object> l_data = StreamUtils.zip(
                Stream.of( "operation", "vehicletype", "vehicle" ),
                Stream.of( "generatevehicle", p_vehicle.id(), p_vehicle ),
                ImmutablePair::new
            ).collect( Collectors.toMap( ImmutablePair::getLeft, ImmutablePair::getRight ) );

            CONNECTIONS.parallelStream().forEach( i -> i.send( l_data ) );
            return p_vehicle;
        }


    }

}
