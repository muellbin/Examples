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

import java.text.MessageFormat;


/**
 * agent class with annotation to define inner-action
 */
@IAgentAction
final class MyAgent extends IBaseAgent<MyAgent>
{

    /**
     * constructor of the agent
     *
     * @param p_configuration agent configuration of the agent generator
     */
    MyAgent( final IAgentConfiguration<MyAgent> p_configuration )
    {
        super( p_configuration );
    }


    /**
     * inner-action definition with annotation for naming and filtering
     *
     * @param p_value numeric value, just use Number type because LightJason
     * can deal with double and long values and number is more flexible
     */
    @IAgentActionFilter
    @IAgentActionName( name = "my/inner-action" )
    private void action( final Number p_value )
    {
        System.out.println( MessageFormat.format( "agent {0} called inner action with value {1}", this.hashCode(), p_value ) );
    }

}
