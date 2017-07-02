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

import org.apache.commons.io.IOUtils;
import org.lightjason.trafficsimulation.common.CCommon;
import org.lightjason.trafficsimulation.common.CConfiguration;

import javax.annotation.Nonnull;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Collections;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import java.util.concurrent.atomic.AtomicReference;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;


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
     * map with asl codes
     */
    private final Map<String, String> m_asl = Collections.synchronizedMap( new TreeMap<>( String.CASE_INSENSITIVE_ORDER ) );


    /**
     * ctor
     */
    private CRuntime()
    {
        // read asl codes
        final Set<String> l_items = Stream.of( "area", "communication", "environment", "vehicle" )
                                          .filter( i -> !read( m_asl, CConfiguration.INSTANCE.getOrDefault( "", "agent", i, "asl" ) ) )
                                          .collect( Collectors.toSet() );
        if ( !l_items.isEmpty() )
            throw new RuntimeException( CCommon.languagestring( this, "agentnotfound", l_items ) );
    }

    /**
     * read agetn data
     *
     * @param p_code code map
     * @param p_key key / file name
     * @return data can be read
     */
    private boolean read( @Nonnull final Map<String, String> p_code, @Nonnull final String p_key )
    {
        if ( ( p_key.isEmpty() ) || ( p_code.containsKey( p_key ) ) )
            return false;

        try
        (
            final InputStream l_stream = new FileInputStream( CCommon.searchpath( p_key ) )
        )
        {
            m_asl.put( p_key.substring( 0, p_key.lastIndexOf( '.' ) ), IOUtils.toString( l_stream, "UTF-8" ) );
            return true;
        }
        catch ( final IOException l_exception )
        {
            return false;
        }
    }



    @Override
    public final void run()
    {
        try
        {
            m_task.updateAndGet( ( i ) -> i.running() ? i : m_supplier.get().apply( m_asl ) ).call();
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
