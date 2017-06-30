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

package org.lightjason.trafficsimulation.elements.area;

import cern.colt.matrix.DoubleMatrix1D;
import org.apache.commons.lang3.tuple.ImmutableTriple;
import org.apache.commons.lang3.tuple.Triple;
import org.lightjason.agentspeak.action.binding.IAgentAction;
import org.lightjason.agentspeak.action.binding.IAgentActionFilter;
import org.lightjason.agentspeak.action.binding.IAgentActionName;
import org.lightjason.agentspeak.configuration.IAgentConfiguration;
import org.lightjason.agentspeak.language.CLiteral;
import org.lightjason.agentspeak.language.CRawTerm;
import org.lightjason.agentspeak.language.ILiteral;
import org.lightjason.agentspeak.language.instantiable.plan.trigger.CTrigger;
import org.lightjason.agentspeak.language.instantiable.plan.trigger.ITrigger;
import org.lightjason.trafficsimulation.common.CConfiguration;
import org.lightjason.trafficsimulation.elements.IBaseObject;
import org.lightjason.trafficsimulation.elements.IObject;
import org.lightjason.trafficsimulation.elements.vehicle.IVehicle;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.io.InputStream;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.concurrent.CopyOnWriteArraySet;
import java.util.concurrent.atomic.AtomicLong;
import java.util.stream.Stream;


/**
 * area agent
 */
@IAgentAction
public final class CArea extends IBaseObject<IArea> implements IArea
{
    /**
     * serial id
     */
    private static final long serialVersionUID = -5224921591628374230L;
    /**
     * functor
     */
    private static final String FUNCTOR = "area";
    /**
     * set of vehicles
     */
    private final Set<IVehicle> m_elements = new CopyOnWriteArraySet<>();
    /**
     * allowed speed
     */
    private final Double m_allowedspeed;

    /**
     * ctor
     *
     * @param p_configuration agent configuration
     * @param p_id name of the object
     * @param p_position initial position
     */
    private CArea(
        @Nonnull final IAgentConfiguration<IArea> p_configuration,
        @Nonnull final String p_id,
        @Nonnull final DoubleMatrix1D p_position,
        @Nonnull final Number p_allowedspeed
    )
    {
        super( p_configuration, FUNCTOR, p_id, p_position );
        m_allowedspeed = p_allowedspeed.doubleValue();
    }

    @Override
    public final IVehicle push( @Nonnull final IVehicle p_vehicle )
    {
        // @todo checking inside
        if ( m_elements.add( p_vehicle ) )
        {
            this.trigger(
                CTrigger.from(
                    ITrigger.EType.ADDGOAL,
                    CLiteral.from( "vehicle", CRawTerm.from( p_vehicle ) )
                )
            );
        }
        return p_vehicle;
    }

    @Override
    protected final Stream<ILiteral> individualliteral( final Stream<IObject<?>> p_object
    )
    {
        return Stream.of(
            CLiteral.from( "allowedspeed", CRawTerm.from( m_allowedspeed ) )
        );
    }

    @Override
    public final IArea call() throws Exception
    {
        m_elements.parallelStream()
                  // @todo can be removed
                  .filter( i -> true )
                  .forEach( i -> this.trigger(
                      CTrigger.from(
                        ITrigger.EType.DELETEGOAL,
                        CLiteral.from( "vehicle", CRawTerm.from( i ) )
                      )
                  ) );

        return super.call();
    }

    /**
     * returns a list of all vehicles inside
     *
     * @return list
     */
    @IAgentActionFilter
    @IAgentActionName( name = "list" )
    private List<IVehicle> listvehicle()
    {
        return new ArrayList<>( m_elements );
    }

    /**
     * set the panalize of the cars
     *
     * @param p_vehicle car
     * @param p_value value
     */
    @IAgentActionFilter
    @IAgentActionName( name = "penalize" )
    private void penalize( final IVehicle p_vehicle, final Number p_value )
    {

    }

    // ---------------------------------------------------------------------------------------------------------------------------------------------------------

    /**
     * generator
     */
    public static final class CGenerator extends IBaseGenerator<IArea>
    {
        /**
         * counter
         */
        private static final AtomicLong COUNTER = new AtomicLong();

        /**
         * ctor
         *
         * @param p_stream stream
         * @throws Exception on any error
         */
        public CGenerator( @Nonnull final InputStream p_stream ) throws Exception
        {
            super( p_stream, CArea.class );
        }

        @Override
        public final IGenerator<IArea> resetcount()
        {
            COUNTER.set( 0 );
            return this;
        }

        @Nullable
        @Override
        protected final Triple<IArea, Boolean, Stream<String>> generate( @Nullable final Object... p_data )
        {
            return new ImmutableTriple<>(
                new CArea(
                    m_configuration,
                    MessageFormat.format( "{0} {1}", FUNCTOR, COUNTER.getAndIncrement() ),
                    null,
                    null
                ),
                CConfiguration.INSTANCE.getOrDefault( false, "agent", "area", "visible" ),
                Stream.of( FUNCTOR )
            );
        }
    }
}
