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
import org.lightjason.trafficsimulation.common.CCommon;
import org.lightjason.trafficsimulation.common.CConfiguration;
import org.lightjason.trafficsimulation.elements.IObject;

import javax.annotation.Nonnull;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
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
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;
import java.util.function.BiFunction;
import java.util.stream.Collectors;


/**
 * runtime singletone instance
 */
public enum ERuntime implements IRuntime
{
    INSTANCE;

    /**
     * execution task
     */
    private final AtomicReference<ITask> m_task = new AtomicReference<>( ITask.EMPTY );
    /**
     * supplier of tasks
     */
    private AtomicReference<BiFunction<Map<String, CAgentDefinition>, Map<String, IObject<?>>, ITask>> m_supplier = new AtomicReference<>( ( i, j ) -> ITask.EMPTY );
    /**
     * map with agents and asl codes and visibility
     */
    private final Map<String, CAgentDefinition> m_agents = Collections.synchronizedMap( new TreeMap<>( String.CASE_INSENSITIVE_ORDER ) );
    /**
     * thread-sleep time
     */
    private final AtomicInteger m_threadsleeptime = new AtomicInteger( CConfiguration.INSTANCE.getOrDefault( 500, "main", "simulationspeed" ) );
    /**
     * execution objects
     */
    private final Map<String, IObject<?>> m_elements = new ConcurrentHashMap<>();


    /**
     * ctor
     */
    ERuntime()
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
     * read agent data
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
                new CAgentDefinition(
                    CConfiguration.INSTANCE.getOrDefault( true, "agent", l_id, "visible" ),
                    false,
                    l_id,
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
    @Nonnull
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
                            i.getValue().getasl(),
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

    @Override
    @Nonnull
    public final AtomicInteger time()
    {
        return m_threadsleeptime;
    }


    @Override
    @Nonnull
    public final Map<String, CAgentDefinition> agents()
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
                                     Map.Entry::getValue,
                                     ( n, m ) -> n,
                                     () -> new TreeMap<>( String.CASE_INSENSITIVE_ORDER )
                                 ) )
                             ),
                             m_elements
                         )
            ).call();
        }
        catch ( final Exception l_exception )
        {
            // ignore errors
        }
    }


    @Override
    @Nonnull
    public final IRuntime supplier( @Nonnull final BiFunction<Map<String, CAgentDefinition>, Map<String, IObject<?>>, ITask> p_supplier )
    {
        m_supplier.set( p_supplier );
        return this;
    }


    @Override
    @Nonnull
    public final Map<String, IObject<?>> elements()
    {
        return Collections.unmodifiableMap( m_elements );
    }


    @Override
    public final boolean running()
    {
        return m_task.get().running();
    }

    /**
     * agent object
     */
    @XmlAccessorType( XmlAccessType.NONE )
    @XmlRootElement( name = "agentdefinition" )
    public static final class CAgentDefinition
    {
        /**
         * visiblity
         */
        private final boolean m_visibility;
        /**
         * active
         */
        @XmlElement( name = "active" )
        private boolean m_active;
        /**
         * is-activiable
         */
        @XmlElement( name = "activatable" )
        private boolean m_isactivatable;
        /**
         * asl source
         */
        private String m_asl;
        /**
         * name of agent
         */
        @XmlElement( name = "id" )
        private final String m_name;

        /**
         * ctor
         */
        public CAgentDefinition( @Nonnull final String p_name )
        {
            m_asl = "";
            m_name = p_name;
            m_visibility = true;
            m_isactivatable = true;
        }

        /**
         * ctor
         *
         * @param p_visiblity visible
         * @param p_isactivatable activatable flag
         * @param p_asl asl code
         */
        CAgentDefinition( final boolean p_visiblity, final boolean p_isactivatable, @Nonnull final String p_name, @Nonnull final String p_asl )
        {
            m_asl = p_asl;
            m_name = p_name;
            m_visibility = p_visiblity;
            m_isactivatable = p_isactivatable;
        }

        @Override
        public final int hashCode()
        {
            return m_name.hashCode();
        }

        @Override
        public final boolean equals( final Object p_object )
        {
            return ( p_object != null ) && ( p_object instanceof CAgentDefinition ) && ( p_object.hashCode() == this.hashCode() );
        }

        /**
         * name of the agent
         *
         * @return name
         */
        @Nonnull
        public final String name()
        {
            return m_name;
        }

        /**
         * returns visiblity
         *
         * @return visibility
         */
        public final boolean getvisibility()
        {
            return m_visibility;
        }

        /**
         * returns asl source
         *
         * @return asl
         */
        @Nonnull
        public final String getasl()
        {
            return m_asl;
        }

        /**
         * set asl source
         *
         * @param p_asl asl
         * @return self reference
         */
        @Nonnull
        public final CAgentDefinition setasl( @Nonnull final String p_asl )
        {
            m_asl = p_asl;
            return this;
        }

        /**
         * returns activatable flag
         *
         * @return activatable
         */
        public final boolean isactivatable()
        {
            return m_isactivatable;
        }

        /**
         * returns active state
         *
         * @return active
         */
        public final boolean getactive()
        {
            return m_active;
        }

        /**
         * swap active
         *
         * @return self reference
         */
        @Nonnull
        public final CAgentDefinition swapactive()
        {
            m_active = !m_active;
            return this;
        }
    }
}
