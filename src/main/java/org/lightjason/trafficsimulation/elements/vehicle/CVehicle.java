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

import cern.colt.matrix.DoubleMatrix1D;
import com.google.common.util.concurrent.AtomicDouble;
import org.apache.commons.lang3.tuple.ImmutableTriple;
import org.apache.commons.lang3.tuple.Triple;
import org.lightjason.agentspeak.action.binding.IAgentAction;
import org.lightjason.agentspeak.action.binding.IAgentActionFilter;
import org.lightjason.agentspeak.action.binding.IAgentActionName;
import org.lightjason.agentspeak.configuration.IAgentConfiguration;
import org.lightjason.agentspeak.language.ILiteral;
import org.lightjason.trafficsimulation.elements.IBaseObject;
import org.lightjason.trafficsimulation.elements.IObject;

import javax.annotation.Nonnegative;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.io.InputStream;
import java.text.MessageFormat;
import java.util.concurrent.atomic.AtomicLong;
import java.util.stream.Stream;


/**
 * vehicle agent
 */
@IAgentAction
public final class CVehicle extends IBaseObject<IVehicle> implements IVehicle
{
    /**
     * serial id
     */
    private static final long serialVersionUID = 3822143462033345857L;
    /**
     * literal functor
     */
    private static final String FUNCTOR = "vehicle";
    /**
     * current speed
     */
    private final AtomicDouble m_speed = new AtomicDouble();
    /**
     * accelerate speed
     */
    private final double m_accelerate;
    /**
     * decelerate speed
     */
    private final double m_decelerate;
    /**
     * panelize value
     */
    private final AtomicDouble m_panelize = new AtomicDouble();

    /**
     * ctor
     *
     * @param p_configuration agent configuration
     * @param p_id name of the object
     * @param p_position initial position
     * @param p_accelerate accelerate speed
     * @param p_decelerate decelerate speed
     */
    private CVehicle( @Nonnull final IAgentConfiguration<IVehicle> p_configuration,
                      @Nonnull final String p_id,
                      @Nonnull final DoubleMatrix1D p_position,
                      @Nonnegative final double p_accelerate, @Nonnegative final double p_decelerate
    )
    {
        super( p_configuration, FUNCTOR, p_id, p_position );
        m_accelerate = p_accelerate;
        m_decelerate = p_decelerate;
    }

    @Override
    protected final Stream<ILiteral> individualliteral( final Stream<IObject<?>> p_object )
    {
        return Stream.empty();
    }

    /**
     * accelerate
     */
    @IAgentActionFilter
    @IAgentActionName( name = "accelerate" )
    private void accelerate()
    {

    }

    /**
     * decelerate
     */
    @IAgentActionFilter
    @IAgentActionName( name = "decelerate" )
    private void decelerate()
    {

    }

    /**
     * swing-out
     */
    @IAgentActionFilter
    @IAgentActionName( name = "swingout" )
    private void swingout()
    {

    }

    /**
     * go back into lane
     */
    @IAgentActionFilter
    @IAgentActionName( name = "goback" )
    private void goback()
    {

    }

    @Override
    public final double speed()
    {
        return m_speed.get();
    }

    @Override
    public final IVehicle penalize( @Nonnull final Number p_value )
    {
        m_panelize.addAndGet( p_value.doubleValue() );
        return this;
    }

    @Override
    public final double penalize()
    {
        return m_panelize.get();
    }

    // ---------------------------------------------------------------------------------------------------------------------------------------------------------

    /**
     * generator
     */
    public static final class CGenerator extends IBaseGenerator<IVehicle>
    {
        /**
         * counter
         */
        private static final AtomicLong COUNTER = new AtomicLong();
        /**
         * visibility within the UI
         */
        private final boolean m_visible;

        /**
         * @param p_stream stream
         * @throws Exception on any error
         */
        protected CGenerator( @Nonnull final InputStream p_stream, final boolean p_visible ) throws Exception
        {
            super( p_stream, CVehicle.class );
            m_visible = p_visible;
        }

        @Override
        public final IGenerator<IVehicle> resetcount()
        {
            COUNTER.set( 0 );
            return this;
        }

        @Nullable
        @Override
        protected final Triple<IVehicle, Boolean, Stream<String>> generate( @Nullable final Object... p_data )
        {
            return new ImmutableTriple<>(
                new CVehicle(
                        m_configuration,
                        MessageFormat.format( "{0} {1}", FUNCTOR, COUNTER.getAndIncrement() ),
                        null,
                        1,
                        1
                ),
                m_visible,
                Stream.of( "vehicle" )
            );
        }
    }
}
