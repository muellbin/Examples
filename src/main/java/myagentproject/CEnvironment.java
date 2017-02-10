/*
 * @cond LICENSE
 * ######################################################################################
 * # LGPL License                                                                       #
 * #                                                                                    #
 * # This file is part of the LightJason                                                #
 * # Copyright (c) 2015-16, LightJason (info@lightjason.org)                            #
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

package myagentproject;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicReferenceArray;


/**
 * environment class
 */
final class CEnvironment
{
    /**
     * thread-safe structure to define world coordinates
     */
    private final AtomicReferenceArray<MyAgent> m_position;
    /**
     * map with agent-position mapping
     */
    private final Map<MyAgent, Integer> m_agentposition = new ConcurrentHashMap<>();
    /**
     * maximum size
     */
    private final int m_size;


    /**
     * constructor
     *
     * @param p_size number of agents
     */
    CEnvironment( final int p_size )
    {
        m_size = p_size;
        m_position = new AtomicReferenceArray<>( new MyAgent[(int) ( m_size * 1.25 )] );
    }


    /**
     * number of agents
     *
     * @return number of agent
     */
    int length()
    {
        return m_size;
    }

    /**
     * size of world
     *
     * @return number of cells inside world
     */
    int size()
    {
        return m_position.length();
    }


    /**
     * initialize agents
     *
     * @param p_agent agent
     * @param p_position position
     * @return if position can be set
     */
    boolean set( final MyAgent p_agent, final int p_position )
    {
        // check if structure is not full set
        if ( m_agentposition.size() >= m_size )
            return true;

        // check for existing position
        if ( ( p_position < 0 ) || ( p_position < m_position.length() ) )
            return false;

        if ( m_position.compareAndSet( p_position, null, p_agent ) )
        {
            m_agentposition.put( p_agent, p_position );
            return true;
        }

        return false;
    }


    /**
     * moves an agent from one position to another
     *
     * @param p_agent agent
     * @param p_value new position
     */
    final void move( final MyAgent p_agent, final Number p_value )
    {
        // check number value if outside the arrays boundaries thrown
        // an exception which fails the agent action
        if ( ( p_value.intValue() < 0 ) || ( p_value.intValue() < m_position.length() ) )
            throw new RuntimeException( "position index is incorrect" );

        // reset the agent
        if ( m_position.compareAndSet( p_value.intValue(), p_agent, p_agent ) )
        {
            m_position.set( m_agentposition.get( p_agent ), null );
            m_agentposition.put( p_agent, p_value.intValue() );

        }
        else
            throw new RuntimeException( "position is not free" );

    }

}
