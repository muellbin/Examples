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

package org.lightjason.trafficsimulation.ui;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import edu.umd.cs.findbugs.annotations.NonNull;
import org.eclipse.jetty.websocket.api.RemoteEndpoint;
import org.eclipse.jetty.websocket.api.Session;
import org.eclipse.jetty.websocket.api.WebSocketAdapter;
import org.eclipse.jetty.websocket.api.WebSocketListener;
import org.lightjason.trafficsimulation.common.ITree;

import javax.annotation.Nonnull;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.function.BiConsumer;
import java.util.stream.Collectors;
import java.util.stream.Stream;


/**
 * websocket interface
 */
public interface IWebSocket extends WebSocketListener
{

    /**
     * sends a dataset to sessions
     * as json dataset
     *
     * @param p_value values
     * @return self reference
     */
    @Nonnull
    IWebSocket send( @Nonnull final Object... p_value );

    /**
     * sends all stream data to the session
     *
     * @param p_stream stream
     * @return self reference
     */
    @Nonnull
    IWebSocket send( @Nonnull final Stream<Object> p_stream );


    /**
     * default websocket class
     */
    abstract class IBaseWebSocket extends WebSocketAdapter implements IWebSocket
    {
        /**
         * object mapper
         */
        private static final ObjectMapper MAPPER = new ObjectMapper();
        /**
         * message consumer
         */
        private final BiConsumer<ITree, IWebSocket> m_messageconsumer;

        /**
         * ctor
         *
         * @param p_messageconsumer message consumer
         */
        protected IBaseWebSocket( final BiConsumer<ITree, IWebSocket> p_messageconsumer )
        {
            m_messageconsumer = p_messageconsumer;
        }


        @Nonnull
        @Override
        public final IWebSocket send( @Nonnull final Object... p_value )
        {
            return this.send( Arrays.stream( p_value ) );
        }

        @Nonnull
        @Override
        public final IWebSocket send( @Nonnull final Stream<Object> p_stream )
        {
            final List<Object> l_data = p_stream.collect( Collectors.toList() );

            final String l_output;
            try
            {
                l_output = l_data.size() == 1
                           ? MAPPER.writeValueAsString( l_data.get( 0 ) )
                           : MAPPER.writeValueAsString( l_data );
            }
            catch ( final JsonProcessingException l_exception )
            {
                throw new RuntimeException( l_exception );
            }

            try
            {
                this.getRemote().sendString( l_output );
            }
            catch ( final IOException l_exception )
            {
                // ignore error
            }
            return this;
        }

        @Override
        @SuppressWarnings( "unchecked" )
        public final void onWebSocketText( final String p_message )
        {
            try
            {
                m_messageconsumer.accept( new ITree.CTree( MAPPER.readValue( p_message, Map.class ) ), this );
            }
            catch ( final IOException l_exception )
            {
                throw new RuntimeException( l_exception );
            }
        }

        @Override
        public final void onWebSocketBinary( final byte[] p_payload, final int p_offset, final int p_length )
        {
        }

        @Override
        public final RemoteEndpoint getRemote()
        {
            return super.getRemote();
        }

        @Override
        public final Session getSession()
        {
            return super.getSession();
        }

        @Override
        public final boolean isConnected()
        {
            return super.isConnected();
        }

        @Override
        public final boolean isNotConnected()
        {
            return super.isNotConnected();
        }
    }
}
