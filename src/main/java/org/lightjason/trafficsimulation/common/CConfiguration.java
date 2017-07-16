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
    public static final String DEFAULTPATH = Stream.of( System.getProperty( "user.home" ), ".lightjason", "trafficsimulation" )
                                                   .collect( Collectors.joining( File.separator ) );
    /**
     * asl name
     */
    public static final String DEfAULTASLPATH = Stream.of( DEFAULTPATH, "asl" ).collect( Collectors.joining( File.separator ) );
    /**
     * action set
     */
    public static final Set<IAction> ACTIONS = Collections.unmodifiableSet( CCommon.actionsFromPackage().collect( Collectors.toSet() ) );
    /**
     * default configuration file
     */
    private static final String DEFAULTCONFIG = Stream.of( DEFAULTPATH, "configuration.yaml" ).collect( Collectors.joining( File.separator ) );


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

        try
            (
                    final InputStream l_stream = new FileInputStream( orDefaultConfig( p_path ) );
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
     * set default path
     *
     * @param p_config path or null / empty
     * @return default path on empty or input path
     */
    private static String orDefaultConfig( final String p_config )
    {
        return ( p_config == null ) || ( p_config.isEmpty() ) ? DEFAULTCONFIG : p_config;
    }

    /**
     * creates the default configuration
     *
     * @return full path
     * @throws IOException on any io error
     */
    public static String createdefault() throws IOException
    {
        StreamUtils.zip(
            Stream.of(
                "",
                "asl",
                "asl",
                "asl",
                "asl",
                "asl"
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
                   .peek( i -> new File( DEFAULTPATH + File.separator + i.getLeft() ).mkdirs() )
                   .forEach( i ->
                   {
                       try
                       {
                           FileUtils.copyInputStreamToFile(
                               CConfiguration.class.getResourceAsStream( i.getRight() ),
                               FileSystems.getDefault().getPath( DEFAULTPATH, i.getLeft(), i.getRight() ).toFile()
                           );
                       }
                       catch ( final IOException l_exception )
                       {
                           throw new UncheckedIOException( l_exception );
                       }
                   } );

        return DEFAULTPATH;
    }

}

