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

import org.lightjason.agentspeak.language.CLiteral;
import org.lightjason.agentspeak.language.CRawTerm;
import org.lightjason.agentspeak.language.instantiable.plan.trigger.CTrigger;
import org.lightjason.agentspeak.language.instantiable.plan.trigger.ITrigger;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicReferenceArray;


/**
 * environment class
 * @note we use here two thread-safe data structure
 * for resolving position-to-agent and agent-to-position
 * on a general point of view it can be also done in a
 * single data structure, but for this example we split it
 * into two parts
 */
final class CEnvironment
{
    /**
     * thread-safe structure to position-to-agent mapping
     */
    private final AtomicReferenceArray<MyAgent> m_position;
    /**
     * map with agent-to-position mapping
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
        m_position = new AtomicReferenceArray<>( new MyAgent[(int) ( m_size * 1.5 )] );
    }


    /**
     * number of agents
     *
     * @return number of agent
     */
    final int length()
    {
        return m_size;
    }

    /**
     * size of world
     *
     * @return number of cells inside world
     */
    final int size()
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
    final boolean set( final MyAgent p_agent, final int p_position )
    {
        // check if structure is not full set
        if ( m_agentposition.size() >= m_size )
            return true;

        // check for existing position
        if ( ( p_position < 0 ) || ( p_position >= m_position.length() ) )
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
        if ( ( p_value.intValue() < 0 ) || ( p_value.intValue() >= m_position.length() ) )
            throw new RuntimeException( "position index is incorrect" );

        // set the new position to the agent
        if ( m_position.compareAndSet( p_value.intValue(), null, p_agent ) )
        {
            final int l_oldposition = m_agentposition.get( p_agent );

            // change the agent position
            m_position.set( l_oldposition, null );
            m_agentposition.put( p_agent, p_value.intValue() );

            // create an goal-trigger once and use it later for all agents
            final ITrigger l_trigger = CTrigger.from(
                ITrigger.EType.ADDGOAL,
                CLiteral.from(
                    "other/agent-position/changed",
                    CLiteral.from( "from", CRawTerm.from( l_oldposition ) ),
                    CLiteral.from( "to", CRawTerm.from( p_value.intValue() ) )
                )
            );

            // trigger all other agents and tells the new position to the agents with the trigger
            m_agentposition
                .keySet()
                .parallelStream()
                // ignore agent which changes the position
                .filter( i -> !i.equals( p_agent ) )
                .forEach( i -> i.trigger( l_trigger ) );

        }
        else
            throw new RuntimeException( "position is not free" );
    }


    /**
     * returns agent position
     *
     * @param p_agent agent
     * @return position
     */
    final int position( final MyAgent p_agent )
    {
        return m_agentposition.getOrDefault( p_agent, 0 );
    }

}
