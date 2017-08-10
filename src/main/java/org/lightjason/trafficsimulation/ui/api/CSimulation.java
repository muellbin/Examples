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

import org.apache.commons.lang3.tuple.MutablePair;
import org.apache.commons.lang3.tuple.Pair;
import org.lightjason.trafficsimulation.common.CCommon;
import org.lightjason.trafficsimulation.common.CConfiguration;
import org.lightjason.trafficsimulation.elements.IMap;
import org.lightjason.trafficsimulation.runtime.ERuntime;
import org.lightjason.trafficsimulation.runtime.CTask;
import org.lightjason.trafficsimulation.ui.EHTTPServer;

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
        return ERuntime.INSTANCE.running();
    }

    /**
     * shutdown simulation
     *
     * @return response
     */
    @GET
    @Path( "/shutdown" )
    @Produces( MediaType.TEXT_PLAIN )
    public final Response shutdown()
    {
        if ( ERuntime.INSTANCE.running() )
            return Response.status( Response.Status.CONFLICT ).entity( CCommon.languagestring( this, "isrunning" ) ).build();

        // asynchronized thread for shutdown, because server process cannot be disable during communication
        new Thread( EHTTPServer::shutdown ).start();
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
        if ( ERuntime.INSTANCE.running() )
            return Response.status( Response.Status.CONFLICT ).entity( CCommon.languagestring( this, "isrunning" ) ).build();

        ERuntime.INSTANCE.supplier( CTask::new ).run();
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
        return ERuntime.INSTANCE
                       .agents()
                       .entrySet()
                       .stream()
                       .filter( i -> i.getValue().getLeft() )
                       .map( Map.Entry::getKey )
                       .collect( Collectors.toSet() );
    }

    /**
     * creates a new agent
     *
     * @param p_id name
     * @return response
     */
    @GET
    @Path( "/asl/create/{id}" )
    @Produces( MediaType.TEXT_PLAIN )
    public final Object createasl( @PathParam( "id" ) final String p_id )
    {
        final Pair<Boolean, String> l_data = ERuntime.INSTANCE.agents().get( p_id );
        if ( l_data != null )
            return Response.status( Response.Status.CONFLICT ).entity( CCommon.languagestring( this, "agentexist", p_id ) ).build();

        ERuntime.INSTANCE.agents().put( p_id.toLowerCase( Locale.ROOT ), new MutablePair<>( true, "" ) );
        return Response.status( Response.Status.OK ).entity( CCommon.languagestring( this, "agentcreate", p_id ) ).build();
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
        final Pair<Boolean, String> l_data = ERuntime.INSTANCE.agents().get( p_id );
        if ( l_data == null )
            return Response.status( Response.Status.NOT_FOUND ).entity( CCommon.languagestring( this, "agentnotfound", p_id ) ).build();
        if ( !l_data.getLeft() )
            return Response.status( Response.Status.FORBIDDEN ).entity( CCommon.languagestring( this, "agentnotaccessable", p_id ) ).build();

        return l_data.getRight();
    }

    /**
     * removes asl code of an agent
     *
     * @param p_id identifier of the agent
     * @return response
     */
    @GET
    @Path( "/asl/remove/{id}" )
    @Produces( MediaType.TEXT_PLAIN )
    public final Object removeasl( @PathParam( "id" ) final String p_id )
    {
        final Pair<Boolean, String> l_data = ERuntime.INSTANCE.agents().get( p_id );
        if ( l_data == null )
            return Response.status( Response.Status.NOT_FOUND ).entity( CCommon.languagestring( this, "agentnotfound", p_id ) ).build();
        if ( ( !l_data.getLeft() ) || ( CConfiguration.defaultagents().anyMatch( p_id::equals ) ) )
            return Response.status( Response.Status.FORBIDDEN ).entity( CCommon.languagestring( this, "agentnotaccessable", p_id ) ).build();

        ERuntime.INSTANCE.agents().remove( p_id.toLowerCase( Locale.ROOT ) );
        return Response.status( Response.Status.OK ).entity( CCommon.languagestring( this, "agentremove", p_id ) ).build();
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
        final Pair<Boolean, String> l_data = ERuntime.INSTANCE.agents().get( p_id );
        if ( l_data == null )
            return Response.status( Response.Status.NOT_FOUND ).entity( CCommon.languagestring( this, "agentnotfound", p_id ) ).build();
        if ( !l_data.getLeft() )
            return Response.status( Response.Status.FORBIDDEN ).entity( CCommon.languagestring( this, "agentnotaccessable", p_id ) ).build();

        if ( !l_data.getValue().equals( p_content ) )
        {
            l_data.setValue( p_content );
            return Response.status( Response.Status.OK ).entity( CCommon.languagestring( this, "agentchanged", p_id ) ).build();
        }

        return Response.status( Response.Status.OK ).build();
    }

    /**
     * sets the simulation time
     *
     * @param p_time simulation time
     * @return response
     */
    @GET
    @Path( "/time/set/{value}" )
    @Produces( MediaType.TEXT_PLAIN )
    public final Response settime( @PathParam( "value" ) final int p_time )
    {
        if ( p_time < 1 )
            return Response.status( Response.Status.CONFLICT ).entity( CCommon.languagestring( this, "timeerror", p_time ) ).build();

        ERuntime.INSTANCE.time().set( p_time );
        return Response.status( Response.Status.OK ).entity( CCommon.languagestring( this, "simulationtime", p_time ) ).build();
    }

    /**
     * gets the simulation time
     *
     * @return response
     */
    @GET
    @Path( "/time/get" )
    @Produces( MediaType.TEXT_PLAIN )
    public final Integer gettime()
    {
        return ERuntime.INSTANCE.time().get();
    }

    /**
     * returns the language labels of the ui
     *
     * @param p_label labels as comma seperated list
     * @return response
     */
    @GET
    @Path( "/language/label/{label}" )
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

    /**
     * returns the current language
     *
     * @return language code
     */
    @GET
    @Path( "/language/current" )
    @Produces( MediaType.TEXT_PLAIN )
    public final String currentlanguage()
    {
        return CCommon.languagebundle().getLocale().getISO3Language();
    }


    /**
     * returns the cookie expire of the ui
     *
     * @return cookie expire
     */
    @GET
    @Path( "/cookie/expire" )
    @Produces( MediaType.TEXT_PLAIN )
    public final int cookieexpire()
    {
        return CConfiguration.INSTANCE.getOrDefault( 120, "ui", "cookieexpire_in_seconds" );
    }


    /**
     * returns a list of all current active simulation elements
     *
     * @return simulation elements
     */
    @GET
    @Path( "/elements" )
    @Produces( MediaType.APPLICATION_JSON )
    public final Object elements()
    {
        return ERuntime.INSTANCE.elements().values().stream().map( i -> i.map( IMap.EStatus.EXECUTE ) ).collect( Collectors.toList() );
    }

    /**
     * returns state of the music play
     *
     * @return music state
     */
    @GET
    @Path( "/music" )
    @Produces( MediaType.TEXT_PLAIN )
    public final Object music()
    {
        return CConfiguration.INSTANCE.getOrDefault( true, "ui", "music" );
    }

    /**
     * returns unique system instance identifier
     *
     * @return identifier
     */
    @GET
    @Path( "/systemid" )
    @Produces( MediaType.TEXT_PLAIN )
    public final Object systemid()
    {
        return CConfiguration.SYSTEMID;
    }

}
