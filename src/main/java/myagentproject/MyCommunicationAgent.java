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

import org.lightjason.agentspeak.agent.IBaseAgent;
import org.lightjason.agentspeak.configuration.IAgentConfiguration;


/**
 * agent, here in detail an communication agents with an individual name
 */
final class MyCommunicationAgent extends IBaseAgent<MyCommunicationAgent>
{
    /**
     * agent name
     */
    private final String m_name;

    /**
     * constructor of the agent
     *
     * @param p_name agent name
     * @param p_configuration agent configuration of the agent generator
     */
    MyCommunicationAgent( final String p_name, final IAgentConfiguration<MyCommunicationAgent> p_configuration )
    {
        super( p_configuration );
        m_name = p_name;
    }

    /**
     * returns the agent name
     *
     * @return agent name
     */
    final String name()
    {
        return m_name;
    }

}
