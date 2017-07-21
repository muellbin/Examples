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

package org.lightjason.trafficsimulation.runtime;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.tuple.MutablePair;
import org.apache.commons.lang3.tuple.Pair;
import org.lightjason.trafficsimulation.common.CCommon;
import org.lightjason.trafficsimulation.common.CConfiguration;

import javax.annotation.Nonnull;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.UncheckedIOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Collections;
import java.util.Locale;
import java.util.Map;
import java.util.TreeMap;
import java.util.concurrent.atomic.AtomicReference;
import java.util.function.Function;
import java.util.stream.Collectors;


/**
 * runtime singletone instance
 */
public final class CRuntime implements IRuntime
{
    /**
     * instance
     */
    public static final CRuntime INSTANCE = new CRuntime();
    /**
     * execution task
     */
    private final AtomicReference<ITask> m_task = new AtomicReference<>( ITask.EMPTY );
    /**
     * supplier of tasks
     */
    private AtomicReference<Function<Map<String, String>, ITask>> m_supplier = new AtomicReference<>( ( i ) -> ITask.EMPTY );
    /**
     * map with agents and asl codes and visibility
     */
    private final Map<String, Pair<Boolean, String>> m_agents = Collections.synchronizedMap( new TreeMap<>( String.CASE_INSENSITIVE_ORDER ) );


    /**
     * ctor
     */
    private CRuntime()
    {
        try
        {
            Files.walk( Paths.get( CConfiguration.INSTANCE.path() ) )
                 .filter( Files::isRegularFile )
                 .filter( i -> i.toFile().getName().endsWith( CConfiguration.ASLEXTENSION ) )
                 .forEach( i -> this.read( i.toFile() ) );
        }
        catch ( final IOException l_exception )
        {
            throw new UncheckedIOException( l_exception );
        }

        // check nessessary agents
        CConfiguration.defaultagents()
                      .filter( i -> !m_agents.containsKey( i ) )
                      .findAny()
                      .ifPresent( i ->
                      {
                          throw new RuntimeException( CCommon.languagestring( this, "agentnotfound", i ) );
                      } );
    }


    /**
     * read agetn data
     *
     * @param p_file agent file
     * @return data can be read
     */
    private void read( @Nonnull final File p_file )
    {
        final String l_id = filetoagent( p_file );
        try
        (
            final InputStream l_stream = new FileInputStream( p_file )
        )
        {
            m_agents.put(
                l_id,
                new MutablePair<>(
                    CConfiguration.INSTANCE.getOrDefault( true, "agent", l_id, "visible" ),
                    IOUtils.toString( l_stream, "UTF-8" )
                )
            );
        }
        catch ( final IOException l_exception )
        {
            // ignore errors
        }
    }

    /**
     * formats the file to an agent name
     *
     * @param p_file file
     * @return agent name
     */
    @Nonnull
    private static String filetoagent( @Nonnull final File p_file )
    {
        return p_file.getName().replace( CConfiguration.ASLEXTENSION, "" );
    }


    @Override
    public final IRuntime save()
    {
        // update existing agents
        m_agents.entrySet()
                .parallelStream()
                .forEach( i ->
                {
                    try
                    {
                        FileUtils.writeStringToFile(
                            Paths.get( CConfiguration.INSTANCE.path(), i.getKey().toLowerCase( Locale.ROOT ) + CConfiguration.ASLEXTENSION ).toFile(),
                            i.getValue().getRight(),
                            "UTF-8"
                        );
                    }
                    catch ( final IOException l_exception )
                    {
                        throw new UncheckedIOException( l_exception );
                    }
                } );

        // remove all unused agents
        try
        {
            Files.walk( Paths.get( CConfiguration.INSTANCE.path() ) )
                 .filter( Files::isRegularFile )
                 .filter( i -> i.toFile().getName().endsWith( CConfiguration.ASLEXTENSION ) )
                 .filter( i -> !m_agents.containsKey( filetoagent( i.toFile() ) ) )
                 .forEach( i ->
                 {
                     try
                     {
                         Files.delete( i );
                     }
                     catch ( final IOException l_exception )
                     {
                         // ignore io exception
                     }
                 } );
        }
        catch ( final IOException l_exception )
        {
            // ignore io exception
        }

        return this;
    }


    /**
     * agents map
     *
     * @return map with agent names and visibilites
     */
    public final Map<String, Pair<Boolean, String>> agents()
    {
        return m_agents;
    }


    @Override
    public final void run()
    {
        try
        {
            m_task.updateAndGet(
                ( i ) -> i.running()
                         ? i
                         : m_supplier.get().apply(
                             Collections.unmodifiableMap(
                                 m_agents.entrySet().stream().collect( Collectors.toMap(
                                     Map.Entry::getKey,
                                     j -> j.getValue().getRight(),
                                     ( n, m ) -> n,
                                     () -> new TreeMap<>( String.CASE_INSENSITIVE_ORDER )
                                 ) )
                             )
                         )
            ).call();
        }
        catch ( final Exception l_exception )
        {
            // ignore errors
        }
    }


    @Override
    public final IRuntime supplier( @Nonnull final Function<Map<String, String>, ITask> p_supplier )
    {
        m_supplier.set( p_supplier );
        return this;
    }


    @Override
    public final boolean running()
    {
        return m_task.get().running();
    }
}
