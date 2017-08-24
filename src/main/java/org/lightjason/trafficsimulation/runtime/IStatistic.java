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

import org.lightjason.trafficsimulation.ui.IMap;

import javax.annotation.Nonnull;
import java.util.Locale;
import java.util.function.Consumer;


/**
 * interface of the statistic
 */
public interface IStatistic extends IMap<IStatistic.EValue>, Consumer<Number>
{
    /**
     * returns if values exists
     *
     * @return existing flag
     */
    boolean valuesexist();

    /**
     * returns maimum value
     *
     * @return value
     */
    @Nonnull
    Number max();

    /**
     * value type
     */
    enum EValue
    {
        PENALTY;

        @Override
        public final String toString()
        {
            return super.toString().toLowerCase( Locale.ROOT );
        }
    }
}
