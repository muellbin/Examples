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


import com.codepoetics.protonpack.StreamUtils;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.tuple.ImmutablePair;
import org.lightjason.agentspeak.action.IAction;
import org.lightjason.agentspeak.common.CCommon;
import org.yaml.snakeyaml.Yaml;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.UncheckedIOException;
import java.nio.file.FileSystems;
import java.nio.file.Paths;
import java.util.Collections;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;
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
     * action set
     */
    public static final Set<IAction> ACTIONS = Collections.unmodifiableSet( CCommon.actionsFromPackage().collect( Collectors.toSet() ) );
    /**
     * asl sub directory
     */
    private static final String ASLDIRECTORY = "asl";
    /**
     * loading path
     */
    private String m_path = "";


    /**
     * ctor
     */

    private CConfiguration()
    {
        super( new ConcurrentHashMap<>() );
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
            {
                m_data.clear();
                m_data.putAll( l_result );
            }

        }
        catch ( final IOException l_exception )
        {
            throw new RuntimeException( l_exception );
        }

        return this;
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
        final Map<String, ?> l_result = (Map<String, Object>) new Yaml().load( p_yaml );
        if ( l_result != null )
        {
            m_data.clear();
            m_data.putAll( l_result );
        }
        return this;
    }

    /**
     * returns the file with the configuration path
     *
     * @param p_name file name
     * @return filename (except extension)
     */
    public final String aslfile( final String p_name )
    {
        return Paths.get( m_path, ASLDIRECTORY, p_name ).toString();
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

        StreamUtils.zip(
            Stream.of(
                "",
                ASLDIRECTORY,
                ASLDIRECTORY,
                ASLDIRECTORY,
                ASLDIRECTORY,
                ASLDIRECTORY
            ),

            Stream.of(
                "configuration.yaml",
                "area.asl",
                "communication.asl",
                "environment.asl",
                "defaultvehicle.asl",
                "uservehicle.asl"
            ),

            ImmutablePair::new

        )
                   .peek( i -> Paths.get( p_path, i.getLeft() ).toFile().mkdirs() )
                   .forEach( i ->
                   {
                       try
                       {
                           FileUtils.copyInputStreamToFile(
                               CConfiguration.class.getResourceAsStream( i.getRight() ),
                               FileSystems.getDefault().getPath( p_path, i.getLeft(), i.getRight() ).toFile()
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

