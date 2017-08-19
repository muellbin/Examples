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

package org.lightjason.trafficsimulation.common;


import org.apache.commons.io.FileUtils;
import org.lightjason.agentspeak.action.IAction;
import org.lightjason.agentspeak.action.builtin.generic.CPrint;
import org.lightjason.agentspeak.common.CCommon;
import org.lightjason.trafficsimulation.ui.api.CMessage;
import org.yaml.snakeyaml.Yaml;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.UncheckedIOException;
import java.nio.file.FileSystems;
import java.nio.file.Paths;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Stream;


/**
 * configuration
 */
public final class CConfiguration extends ITree.CTree
{
    /**
     * singleton instance
     */
    public static final CConfiguration INSTANCE = new CConfiguration();
    /**
     * default configuration path
     */
    public static final String DEFAULTPATH = Paths.get( System.getProperty( "user.home" ), ".lightjason", "trafficsimulation" ).toString();
    /**
     * instantiation system id
     */
    public static final String SYSTEMID = UUID.randomUUID().toString();
    /**
     * asl sub directory
     */
    public static final String ASLEXTENSION = ".asl";
    /**
     * loading path
     */
    private String m_path = "";
    /**
     * action set
     */
    private final Set<IAction> m_actions = new HashSet<>();


    /**
     * ctor
     */

    private CConfiguration()
    {
        super( new ConcurrentHashMap<>() );
    }

    /**
     * returns the configuration path
     *
     * @return configuration path
     */
    public final String path()
    {
        return m_path;
    }

    /**
     * loads the configuration from a string
     *
     * @param p_yaml yaml string
     * @return self reference
     */
    @SuppressWarnings( "unchecked" )
    public final CConfiguration loadstring( final String p_yaml )
    {
        try
        {
            final Map<String, ?> l_result = (Map<String, Object>) new Yaml().load( p_yaml );
            if ( l_result != null )
                this.setdata( l_result );
        }
        catch ( final Exception l_exception )
        {
            throw new RuntimeException( l_exception );
        }

        return this;
    }

    /**
     * loads the configuration
     * @param p_path path elements
     * @return self reference
     */
    @SuppressWarnings( "unchecked" )
    public final CConfiguration loadfile( final String p_path )
    {
        m_path = orDefaultConfig( p_path );

        try
        (
            final InputStream l_stream = new FileInputStream( Paths.get( m_path, "configuration.yaml" ).toFile()  )
        )
        {

            final Map<String, ?> l_result = (Map<String, Object>) new Yaml().load( l_stream );
            if ( l_result != null )
                this.setdata( l_result );

        }
        catch ( final Exception l_exception )
        {
            throw new RuntimeException( l_exception );
        }

        return this;
    }

    /**
     * set the internal data defintion
     * @param p_data map with dara
     * @throws Exception is thrown on action error
     */
    private void setdata( final Map<String, ?> p_data ) throws Exception
    {
        m_data.clear();
        m_data.putAll( p_data );

        // build agent actions
        m_actions.clear();
        Stream.concat(
            Stream.of(
                new CPrint(
                    () -> CMessage.CInstance.INSTANCE.stream(
                        CMessage.EType.NOTICE, this.getOrDefault( 2000, "ui", "messagedelay", "agentprint" )
                    ),
                    "\n"
                )
            ),
            CCommon.actionsFromPackage()
        ).forEach( m_actions::add );
    }


    /**
     * agent actions
     *
     * @return action stream
     */
    public final Stream<IAction> actions()
    {
        return m_actions.stream();
    }

    /**
     * set default path
     *
     * @param p_config path or null / empty
     * @return default path on empty or input path
     */
    private static String orDefaultConfig( final String p_config )
    {
        return ( p_config == null ) || ( p_config.isEmpty() ) ? DEFAULTPATH : p_config;
    }

    /**
     * returns a stream of base agent names
     *
     * @return name stream (without file extension)
     */
    public static Stream<String> baseagents()
    {
        return Stream.of( "area", "environment", "defaultvehicle" );
    }

    /**
     * returns a stream of activable agent names
     *
     * @return name stream (without file extension)
     */
    public static Stream<String> activatableagents()
    {
        return Stream.of( "uservehicle" );
    }

    /**
     * creates the default configuration
     *
     * @param p_path base path
     * @return full path
     * @throws IOException on any io error
     */
    public static String createdefault( final String p_path ) throws IOException
    {
        if ( new File( p_path ).exists() )
            return p_path;

        Paths.get( p_path ).toFile().mkdirs();
        Stream.concat(
            Stream.of( "configuration.yaml" ),
            Stream.concat( baseagents(), activatableagents() ).map( i -> i + ASLEXTENSION )
        )
            .forEach( i ->
            {
                try
                {
                    FileUtils.copyInputStreamToFile(
                        CConfiguration.class.getResourceAsStream( i ),
                        FileSystems.getDefault().getPath( p_path, i ).toFile()
                    );
                }
                catch ( final IOException l_exception )
                {
                    throw new UncheckedIOException( l_exception );
                }
            } );

        return p_path;
    }

}

