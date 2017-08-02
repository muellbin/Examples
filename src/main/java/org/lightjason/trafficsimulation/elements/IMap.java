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

package org.lightjason.trafficsimulation.elements;

import javax.annotation.Nonnull;
import java.util.Locale;
import java.util.Map;


/**
 * interface to define the object as key-value pair
 */
public interface IMap
{

    /**
     * creates for the object a map / yaml definition
     * @return map representation with status
     */
    Map<String, Object> map( @Nonnull final EStatus p_status );


    /**
     * status of the object
     */
    enum EStatus
    {
        INITIALIZE,
        EXECUTE,
        SHUTDOWN;

        @Override
        public final String toString()
        {
            return super.toString().toLowerCase( Locale.ROOT );
        }
    }
}
