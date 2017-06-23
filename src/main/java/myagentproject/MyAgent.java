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

import org.lightjason.agentspeak.action.binding.IAgentAction;
import org.lightjason.agentspeak.action.binding.IAgentActionFilter;
import org.lightjason.agentspeak.action.binding.IAgentActionName;
import org.lightjason.agentspeak.agent.IBaseAgent;
import org.lightjason.agentspeak.configuration.IAgentConfiguration;

import javax.annotation.Nonnull;


/**
 * agent with internal actions, on default
 * all methods are blacklisted, so a method
 * which should be an action must be annotated
 */
@IAgentAction
final class MyAgent extends IBaseAgent<MyAgent>
{
    /**
     * serial id
     */
    private static final long serialVersionUID = -5105593330267677627L;
    /**
     * environment reference
     */
    private final CEnvironment m_environment;


    /**
     * constructor of the agent
     *
     * @param p_configuration agent configuration of the agent generator
     * @param p_environment environment reference
     */
    MyAgent( @Nonnull final IAgentConfiguration<MyAgent> p_configuration, @Nonnull final CEnvironment p_environment )
    {
        super( p_configuration );
        m_environment = p_environment;
    }

    /**
     * internal object action, that calls
     * the environment method
     *
     * @param p_value new index position
     */
    @IAgentActionFilter
    @IAgentActionName( name = "env/move" )
    private void envmove( @Nonnull final Number p_value )
    {
        m_environment.move( this, p_value );
    }

    /**
     * returns the current agent position
     *
     * @return agent position
     */
    final int position()
    {
        return m_environment.position( this );
    }

}
