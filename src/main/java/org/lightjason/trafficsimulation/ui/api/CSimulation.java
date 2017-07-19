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

import org.apache.commons.lang3.tuple.Pair;
import org.lightjason.trafficsimulation.common.CCommon;
import org.lightjason.trafficsimulation.runtime.CRuntime;
import org.lightjason.trafficsimulation.runtime.CTask;
import org.lightjason.trafficsimulation.ui.CHTTPServer;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.NotFoundException;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.Locale;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;


/**
 * rest-api of main functionality
 */
@Path( "/simulation" )
public final class CSimulation
{

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
        return Response.status( Response.Status.OK ).entity( CCommon.languagestring( this, "shutdown" ) ).build();
    }

    /**
     * runs a simulation task
     *
     * @return response
     */
    @GET
    @Path( "/run" )
    @Produces( MediaType.APPLICATION_JSON )
    public final Response run()
    {
        if ( CRuntime.INSTANCE.running() )
            return Response.status( Response.Status.CONFLICT ).entity( CCommon.languagestring( this, "isrunning" ) ).build();

        CRuntime.INSTANCE.supplier( CTask::new ).run();
        return Response.status( Response.Status.OK ).build();
    }


    /**
     * returns a list of agents with asl code
     *
     * @return map of agents with asl code
     */
    @GET
    @Path( "/agents" )
    @Produces( MediaType.APPLICATION_JSON )
    public final Set<String> agents()
    {
        return CRuntime.INSTANCE
                       .agents()
                       .entrySet()
                       .stream()
                       .filter( i -> i.getValue().getLeft() )
                       .map( Map.Entry::getKey )
                       .collect( Collectors.toSet() );
    }

    /**
     * returns asl code of an agent
     *
     * @param p_id identifier of the agent
     * @return repsonse or asl code
     */
    @GET
    @Path( "/asl/get/{id}" )
    @Produces( MediaType.TEXT_PLAIN )
    public final Object getasl( @PathParam( "id" ) final String p_id )
    {
        final Pair<Boolean, String> l_data = CRuntime.INSTANCE.agents().get( p_id );
        if ( l_data == null )
            return Response.status( Response.Status.NOT_FOUND ).entity( CCommon.languagestring( this, "agentnotfound", p_id ) ).build();
        if ( !l_data.getLeft() )
            return Response.status( Response.Status.FORBIDDEN ).entity( CCommon.languagestring( this, "agentnotaccessable", p_id ) ).build();

        return l_data.getRight();
    }


    /**
     * sets the asl code of an agent
     *
     * @param p_id identifier of the agent
     * @param p_content source code
     * @return response
     */
    @POST
    @Path( "/asl/set/{id}" )
    @Consumes( MediaType.TEXT_PLAIN )
    public final Response setasl( @PathParam( "id" ) final String p_id, final String p_content )
    {
        final Pair<Boolean, String> l_data = CRuntime.INSTANCE.agents().get( p_id );
        if ( l_data == null )
            return Response.status( Response.Status.NOT_FOUND ).entity( CCommon.languagestring( this, "agentnotfound", p_id ) ).build();
        if ( !l_data.getLeft() )
            return Response.status( Response.Status.FORBIDDEN ).entity( CCommon.languagestring( this, "agentnotaccessable", p_id ) ).build();

        l_data.setValue( p_content );
        return Response.status( Response.Status.OK ).entity( CCommon.languagestring( this, "agentchanged", p_id ) ).build();
    }

    /**
     * returns the language labels of the ui
     *
     * @param p_label labels as comma seperated list
     * @return response
     */
    @GET
    @Path( "/language/{label}" )
    @Produces( MediaType.TEXT_PLAIN )
    public final Object language( @PathParam( "label" ) final String p_label )
    {
        if ( p_label.isEmpty() )
            return Response.status( Response.Status.NOT_FOUND ).entity( CCommon.languagestring( this, "labelempty" ) ).build();

        final String l_label = p_label.trim().toLowerCase( Locale.ROOT );
        try
        {
            final String l_translation = CCommon.languagestring( this, "ui_" + l_label );
            if ( l_translation.isEmpty() )
                throw new NotFoundException( CCommon.languagestring( this, "languagelabelnotfound", l_label ) );

            return l_translation;
        }
        catch ( final NotFoundException l_exception )
        {
            return Response.status( Response.Status.NOT_FOUND ).entity( l_exception.getLocalizedMessage() ).build();
        }
    }


}
