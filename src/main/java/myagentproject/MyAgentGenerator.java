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
import org.lightjason.agentspeak.language.score.IAggregation;

import java.io.InputStream;
import java.util.stream.Collectors;
import java.util.stream.Stream;


/**
 * agent generator, which push the environment reference to the agent
 */
final class MyAgentGenerator extends IBaseAgentGenerator<MyAgent>
{

    /**
     * environment reference
     */
    private final CEnvironment m_environment;


    /**
     * constructor of the generator
     *
     * @param p_stream asl stream
     * @param p_environment environment reference
     * @throws Exception on any error
     */
    MyAgentGenerator( final InputStream p_stream, final CEnvironment p_environment ) throws Exception
    {
        super(
            // input ASL stream
            p_stream,

            Stream.concat(

                // we use all build-in actions of LightJason
                CCommon.actionsFromPackage(),

                // read object actions
                CCommon.actionsFromAgentClass( MyAgent.class )

            ).collect( Collectors.toSet() ),

            // aggregation function for the optimization function, here
            // we use an empty function
            IAggregation.EMPTY
        );

        m_environment = p_environment;
    }

    /**
     * generator method of the agent
     *
     * @param p_data any data which can be put from outside to the generator method
     * @return returns an agent
     */
    @Override
    public final MyAgent generatesingle( final Object... p_data )
    {
        // create agent
        final MyAgent l_agent = new MyAgent( m_configuration, m_environment );

        // set agent into environment by random
        int l_position = (int) ( Math.random() * m_environment.size() );
        while ( m_environment.set( l_agent, l_position ) )
        {
            l_position = (int) ( Math.random() * m_environment.size() );
        }

        return l_agent;
    }

}
