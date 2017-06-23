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

import java.io.FileInputStream;
import java.util.Collections;
import java.util.Set;
import java.util.logging.LogManager;
import java.util.stream.Collectors;
import java.util.stream.IntStream;


/**
 * main application with runtime
 */
final class App
{

    static
    {
        // disable logger
        LogManager.getLogManager().reset();
    }

    /**
     * private constructor to avoid any instantiation
     */
    private App()
    {
    }


    /**
     * main method
     *
     * @param p_args command-line arguments
     */
    public static void main( final String[] p_args )
    {
        if ( p_args.length < 2 )
            throw new RuntimeException( "arguments are not set: ASL script, number of agents" );

        // parameter of the command-line arguments:
        // 1. ASL file
        // 2. number of agents
        // 3. number of iterations (if not set maximum integer)
        final Set<MyAgent> l_agents;
        try
            (
                final FileInputStream l_stream = new FileInputStream( p_args[0] );
            )
        {
            // agent factory with generating an unmodifyable set
            l_agents = Collections.unmodifiableSet(
                new MyAgentGenerator( l_stream )
                    .generatemultiple( Integer.parseInt( p_args[1] ) )
                    .collect( Collectors.toSet() )
            );
        }
        catch ( final Exception l_exception )
        {
            l_exception.printStackTrace();
            return;
        }

        // runtime call (with parallel execution)
        IntStream
            .range(
                0,
                p_args.length < 3
                ? Integer.MAX_VALUE
                : Integer.parseInt( p_args[2] )
            )
            .forEach( j -> l_agents.parallelStream()
                                   .forEach( i ->
                                   {
                                       try
                                       {
                                           i.call();
                                       }
                                       catch ( final Exception l_exception )
                                       {
                                           l_exception.printStackTrace();
                                           throw new RuntimeException();
                                       }
                                   } ) );
    }
}
