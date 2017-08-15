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

package org.lightjason.trafficsimulation.elements.vehicle;

import org.lightjason.trafficsimulation.elements.IObject;

import javax.annotation.Nonnegative;
import javax.annotation.Nonnull;
import java.util.Locale;


/**
 * vehicle interface
 */
public interface IVehicle extends IObject<IVehicle>
{

    /**
     * get current speed in km/h
     *
     * @return current speed
     */
    @Nonnegative
    double speed();

    /**
     * set penalty value
     *
     * @param p_value value
     * @return self reference
     */
    @Nonnull
    IVehicle penalty( @Nonnull final Number p_value );

    /**
     * get penalty value
     *
     * @return value
     */
    @Nonnegative
    double penalty();

    /**
     * returns acceleration in m/sec^2
     *
     * @return acceleration
     */
    @Nonnegative
    double acceleration();

    /**
     * returns deceleration in m/sec^2
     *
     * @return deceleration
     */
    @Nonnegative
    double deceleration();

    /**
     * returns the maximum speed of the vehicle
     *
     * @return maximum speed
     */
    @Nonnegative
    double maximumspeed();



    /**
     * vehicle type
     *
     * @return vehicle type
     */
    ETYpe type();

    /**
     * type definition
     */
    enum ETYpe
    {
        USERVEHICLE,
        DEFAULTVEHICLE;

        @Override
        public final String toString()
        {
            return super.toString().toLowerCase( Locale.ROOT );
        }
    }

}
