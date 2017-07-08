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

import org.lightjason.rest.CCommon;
import org.lightjason.trafficsimulation.runtime.CRuntime;
import org.lightjason.trafficsimulation.ui.CHTTPServer;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.Map;
import java.util.stream.Collectors;


/**
 * rest-api of main functionality
 */
@Path( "/simulation" )
public final class CSimulation
{
    /**
     * returns execute a simulation run
     *
     * @return agent name list
     */
    @GET
    @Path( "/execute" )
    @Produces( MediaType.APPLICATION_JSON )
    public final Response execute()
    {
        if ( CRuntime.INSTANCE.running() )
            return Response.status( Response.Status.CONFLICT ).entity( CCommon.languagestring( this, "isrunning" ) ).build();

        CRuntime.INSTANCE.run();
        return Response.status( Response.Status.OK ).build();
    }

    /**
     * simulation is running
     *
     * @return running flag
     */
    @GET
    @Path( "/running" )
    @Produces( MediaType.APPLICATION_JSON )
    public final boolean running()
    {
        return CRuntime.INSTANCE.running();
    }

    /**
     * shutdown simulation
     *
     * @return response
     */
    @GET
    @Path( "/shutdown" )
    @Produces( MediaType.APPLICATION_JSON )
    public final Response shutdown()
    {
        if ( CRuntime.INSTANCE.running() )
            return Response.status( Response.Status.CONFLICT ).entity( CCommon.languagestring( this, "isrunning" ) ).build();

        new Thread( CHTTPServer::shutdown ).start();
        return Response.status( Response.Status.OK ).build();
    }


    /**
     * returns a list of agents
     *
     * @return map of agents with code
     */
    @GET
    @Path( "/agents" )
    @Produces( MediaType.APPLICATION_JSON )
    public final Map<String, String> agents()
    {
        return CRuntime.INSTANCE.agents()
                                .entrySet()
                                .stream()
                                .filter( i -> i.getValue().getRight() )
                                .collect( Collectors.toMap( Map.Entry::getKey, i -> i.getValue().getLeft() ) );
    }

}
