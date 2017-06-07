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

import org.lightjason.agentspeak.common.CCommon;
import org.lightjason.agentspeak.generator.IBaseAgentGenerator;

import java.io.InputStream;
import java.text.MessageFormat;
import java.util.concurrent.atomic.AtomicLong;
import java.util.stream.Collectors;
import java.util.stream.Stream;


/**
 * agent generator to create communication agents
 */
final class MyAgentGenerator extends IBaseAgentGenerator<MyCommunicationAgent>
{
    /**
     * store a reference to the send action for adding / removing agents
     */
    private final CSend m_send;

    /**
     * thread-safe counter for the agent name, because the
     * generator method can be called in parallel
     */
    private final AtomicLong m_counter = new AtomicLong();

    /**
     * constructor of the generator
     *
     * @param p_send send action
     * @param p_stream asl stream
     * @throws Exception on any error
     */
    MyAgentGenerator( final CSend p_send, final InputStream p_stream ) throws Exception
    {
        super(
            // input ASL stream
            p_stream,

            // a set with all possible actions for the agent
            Stream.concat(
                // we use all build-in actions of LightJason
                CCommon.actionsFromPackage(),
                // add VotingAgent related external action
                Stream.of( p_send )

                // build the set with a collector
            ).collect( Collectors.toSet() ),

            // variable builder for fixed variables within each plan
            new CVariableBuilder()
        );

        m_send = p_send;
    }

    /**
     * generator method of the agent
     *
     * @param p_data any data which can be put from outside to the generator method
     * @return returns an agent
     */
    @Override
    public final MyCommunicationAgent generatesingle( final Object... p_data )
    {
        // register a new agent object at the send action and the register
        // method retruns the object reference
        return m_send.register(
            new MyCommunicationAgent(

                // create a string with the agent name "agent <number>"
                // get the value of the counter first and increment, build the agent
                // name with message format (see Java documentation)
                MessageFormat.format( "agent {0}", m_counter.getAndIncrement() ),

                // add the agent configuration
                m_configuration
            )
        );
    }

    /**
     * unregister an agent
     *
     * @param p_agent agent object
     */
    final void unregister( final MyCommunicationAgent p_agent )
    {
        m_send.unregister( p_agent );
    }

}
