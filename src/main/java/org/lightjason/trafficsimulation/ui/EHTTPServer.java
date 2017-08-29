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

import org.apache.commons.lang3.tuple.Triple;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.FilterHolder;
import org.eclipse.jetty.servlet.ServletHolder;
import org.eclipse.jetty.servlets.CrossOriginFilter;
import org.eclipse.jetty.webapp.WebAppContext;
import org.eclipse.jetty.websocket.servlet.WebSocketServlet;
import org.eclipse.jetty.websocket.servlet.WebSocketServletFactory;
import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.servlet.ServletContainer;
import org.lightjason.rest.CApplication;
import org.lightjason.trafficsimulation.common.CCommon;
import org.lightjason.trafficsimulation.common.CConfiguration;
import org.lightjason.trafficsimulation.elements.IObject;
import org.lightjason.trafficsimulation.runtime.ERuntime;
import org.lightjason.trafficsimulation.ui.api.CAPI;
import org.lightjason.trafficsimulation.ui.api.CAnimation;
import org.lightjason.trafficsimulation.ui.api.CStatistic;
import org.lightjason.trafficsimulation.ui.api.CMessage;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import javax.servlet.DispatcherType;
import java.awt.*;
import java.net.InetSocketAddress;
import java.net.URI;
import java.text.MessageFormat;
import java.util.EnumSet;
import java.util.stream.Stream;

/**
 * Jersey-Jetty-HTTP server for UI
 *
 * debug: -Dorg.eclipse.jetty.servlet.LEVEL=ALL
 *
 */
public enum EHTTPServer
{
    INSTANCE;

    /**
     * server default port
     */
    private static final int DEFAULTPORT = 12345;
    /**
     * server default host
     */
    private static final String DEFAULTHOST = "localhost";
    /**
     * server instance
     */
    private final Server m_server;
    /**
     * REST-API application
     */
    private final CApplication m_restagent = new CApplication();

    /**
     * ctor
     */
    EHTTPServer()
    {
        // server process
        m_server = new Server(
            new InetSocketAddress( CConfiguration.INSTANCE.getOrDefault( DEFAULTHOST, "ui", "host" ),
                                   CConfiguration.INSTANCE.<Integer>getOrDefault( DEFAULTPORT, "ui", "port" )
            )
        );

        // set web application
        final WebAppContext l_webapp = new WebAppContext();
        m_server.setHandler( l_webapp );

        l_webapp.setServer( m_server );
        l_webapp.setContextPath( "/" );
        l_webapp.setInitParameter( "Cache-Control", "max-age=0,no-cache,no-store,post-check=0,pre-check=0" );
        l_webapp.setWelcomeFiles( new String[]{"index.html", "index.htm"} );
        l_webapp.setResourceBase(
            EHTTPServer.class.getResource(
                MessageFormat.format( "/{0}/html", CCommon.PACKAGEROOT.replace( ".", "/" ) )
            ).toExternalForm()
        );

        // set rest-apis
        restapi( l_webapp, m_restagent, "/lightjason/*" );
        restapi( l_webapp, new CAPI(), "/api/*" );

        // set websockets
        websocket( l_webapp, CAnimation.class, CAnimation.PATH );
        websocket( l_webapp, CMessage.class, CMessage.PATH );
        websocket( l_webapp, CStatistic.class, CStatistic.PATH );
    }


    /**
     * initialize the rest api call
     *
     * @param p_context web-app context
     * @param p_resource resource
     * @param p_path path
     */
    private static void restapi( final WebAppContext p_context, final ResourceConfig p_resource, final String p_path )
    {
        p_context.addServlet( new ServletHolder( new ServletContainer( p_resource ) ), p_path );
        p_context.addFilter( new FilterHolder( new CrossOriginFilter() ), p_path, EnumSet.of( DispatcherType.REQUEST ) );
    }

    /**
     * add a websocket servlet
     *
     * @param p_context we-app context
     * @param p_websocketfectory websocket factory
     * @param p_path path
     */
    private static void websocket( final WebAppContext p_context, final Class<?> p_websocketfectory, final String p_path )
    {
        p_context.addServlet( new ServletHolder( new CWebSocketSupplier( p_websocketfectory ) ), p_path );
    }




    /**
     * execute the server instance
     */
    public static void execute()
    {
        try
        {
            INSTANCE.m_server.start();

            // open browser if possible
            if ( ( CConfiguration.INSTANCE.<Boolean>getOrDefault( true, "ui", "openbrowser" ) ) && ( Desktop.isDesktopSupported() ) )
                Desktop.getDesktop().browse( new URI(
                                                 "http://" + CConfiguration.INSTANCE.<String>getOrDefault( DEFAULTHOST, "ui", "host" )
                                                 + ":" + CConfiguration.INSTANCE.<Integer>getOrDefault( DEFAULTPORT, "ui", "port" )
                                             )
                );

            INSTANCE.m_server.join();
        }
        catch ( final Exception l_exception )
        {
            throw new RuntimeException( l_exception );
        }
    }

    /**
     * server shutdown
     */
    public static void shutdown()
    {
        ERuntime.INSTANCE.save();
        try
        {
            INSTANCE.m_server.stop();
        }
        catch ( final Exception l_exception )
        {
            // errors are ignored
        }
        finally
        {
            INSTANCE.m_server.destroy();
        }
    }

    /**
     * register agent if server is started
     *
     * @param p_agent agent object
     * @param p_group additional group
     * @return agent object
     */
    public static <T extends IObject<?>> T register( @Nonnull final T p_agent, final boolean p_register, @Nullable final String... p_group )
    {
        if ( !p_register )
            return p_agent;

        INSTANCE.m_restagent.register( p_agent.id(), p_agent, p_group );
        return p_agent;
    }

    /**
     * register agent if server started
     *
     * @param p_agentgroup tupel of agent and stream of group names
     * @return agent object
     * @tparam T agent type
     */
    public static <T extends IObject<?>> T register( @Nonnull final Triple<T, Boolean, Stream<String>> p_agentgroup )
    {
        return register( p_agentgroup.getLeft(), p_agentgroup.getMiddle(), p_agentgroup.getRight().toArray( String[]::new ) );
    }


    /**
     * websocket handler
     */
    private static final class CWebSocketSupplier extends WebSocketServlet
    {
        /**
         * serial id
         */
        private static final long serialVersionUID = 8659755712925612125L;
        /**
         * class reference
         */
        private final Class<?> m_class;

        /**
         * ctor
         *
         * @param p_class class
         */
        CWebSocketSupplier( final Class<?> p_class )
        {
            m_class = p_class;
        }

        @Override
        public final void configure( final WebSocketServletFactory p_factory )
        {
            p_factory.register( m_class );
        }
    }

}
