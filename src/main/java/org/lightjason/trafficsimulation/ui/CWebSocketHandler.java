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

import org.eclipse.jetty.websocket.api.Session;
import org.eclipse.jetty.websocket.api.annotations.OnWebSocketClose;
import org.eclipse.jetty.websocket.api.annotations.OnWebSocketConnect;
import org.eclipse.jetty.websocket.api.annotations.OnWebSocketMessage;
import org.eclipse.jetty.websocket.api.annotations.WebSocket;
import org.eclipse.jetty.websocket.api.annotations.OnWebSocketError;

import java.io.IOException;
import java.text.MessageFormat;

/**
 * websocket class
 */
@WebSocket
public class CWebSocketHandler
{
    /**
     * session
     */
    private static Session s_session;

    /**
     * on message
     *
     * @param p_session session
     * @param p_message message
     * @throws IOException io exception
     */
    @OnWebSocketMessage
    public void onMessage( final Session p_session, final String p_message ) throws IOException
    {
        System.out.println( "Message received: " + p_message );
    }

    /**
     * on connect
     *
     * @param p_session session
     * @throws IOException io exception
     */
    @OnWebSocketConnect
    public void onConnect( final Session p_session ) throws IOException
    {
        System.out.println( p_session.getRemoteAddress().getHostString() + " connected!" );
        s_session = p_session;
    }

    /**
     * on close
     *
     * @param p_session session
     * @param p_status status
     * @param p_reason reason
     */
    @OnWebSocketClose
    public void onClose( final Session p_session, final int p_status, final String p_reason )
    {
        System.out.println( MessageFormat.format( "{0} closed: statusCode={1}, reason={2}", p_session.getRemoteAddress().getHostString(), p_status, p_reason ) );
    }

    /**
     * on error
     *
     * @param p_throwable throwable
     */
    @OnWebSocketError
    public void onError( final Throwable p_throwable )
    {
        System.out.println( "Error: " + p_throwable.getMessage() );
    }

    /**
     * get session
     *
     * @return session
     */
    public static Session session()
    {
        return s_session;
    }
}

