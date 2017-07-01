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

import org.lightjason.trafficsimulation.common.CCommon;
import org.lightjason.trafficsimulation.common.CConfiguration;
import org.lightjason.trafficsimulation.elements.IObject;
import org.lightjason.trafficsimulation.elements.environment.CEnvironment;
import org.lightjason.trafficsimulation.elements.environment.IEnvironment;
import org.lightjason.trafficsimulation.ui.CHTTPServer;

import java.io.FileInputStream;
import java.io.InputStream;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;
import java.util.logging.Logger;


/**
 * runtime task instance
 */
public class CTask implements ITask
{
    /**
     * logger
     */
    private static final Logger LOGGER = CCommon.logger( ITask.class );
    /**
     * environement
     */
    private Set<IObject<?>> m_elements = Collections.synchronizedSet( new HashSet<>() );

    @Override
    public final void run()
    {
        new Thread( () ->
        {
            final IEnvironment l_environment;

            try
                (
                    final InputStream l_stream = new FileInputStream(
                        CCommon.searchpath( CConfiguration.INSTANCE.get( "agent", "environment", "asl" ) )
                    )
                )
            {
                l_environment = new CEnvironment.CGenerator( l_stream ).generatesingle();
            }
            catch ( final Exception l_exception )
            {
                System.out.println( l_exception );
                LOGGER.warning( l_exception.getLocalizedMessage() );
                return;
            }

            if ( l_environment != null )
                while ( !l_environment.shutdown() )
                    try
                    {
                        l_environment.call();
                    }
                    catch ( final Exception l_exception )
                    {
                        LOGGER.warning( l_exception.getLocalizedMessage() );
                        break;
                    }

            CHTTPServer.shutdown();
        } ).start();
    }
}
