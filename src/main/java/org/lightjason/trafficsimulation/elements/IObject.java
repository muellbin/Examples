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


import cern.colt.matrix.DoubleMatrix1D;
import org.lightjason.agentspeak.agent.IAgent;
import org.lightjason.agentspeak.generator.IAgentGenerator;
import org.lightjason.trafficsimulation.ui.IMap;

import javax.annotation.Nonnull;
import java.util.Locale;


/**
 * any object interface
 *
 * @tparam T domain specific type
 */
public interface IObject<T extends IAgent<?>> extends IMap<IObject.EStatus>, IPerceiveable, IAgent<T>
{

    /**
     * name of the object
     *
     * @return string name
     */
    @Nonnull
    String id();

    /**
     * position of the object
     * @return position
     */
    @Nonnull
    DoubleMatrix1D position();

    /**
     * returns the new position of the object
     *
     * @return new position
     */
    @Nonnull
    DoubleMatrix1D nextposition();

    /**
     * is called for object destroying
     *
     * @return self reference
     */
    @Nonnull
    IObject<T> release();



    /**
     * generator interface
     *
     * @tparam T element generator
     */
    interface IGenerator<T extends IObject<?>> extends IAgentGenerator<T>
    {
        /**
         * resets the internal counter
         *
         * @return self-reference
         */
        IGenerator<T> resetcount();
    }


    /**
     * status of the object
     */
    enum EStatus
    {
        INITIALIZE,
        EXECUTE,
        RELEASE;

        @Override
        public final String toString()
        {
            return super.toString().toLowerCase( Locale.ROOT );
        }
    }
}

