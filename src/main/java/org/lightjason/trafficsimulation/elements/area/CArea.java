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
import com.google.common.collect.HashMultimap;
import com.google.common.collect.Multimap;
import com.google.common.collect.Multimaps;
import org.apache.commons.lang3.tuple.ImmutableTriple;
import org.apache.commons.lang3.tuple.Pair;
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
import org.lightjason.trafficsimulation.elements.environment.IEnvironment;
import org.lightjason.trafficsimulation.elements.vehicle.IVehicle;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.io.InputStream;
import java.text.MessageFormat;
import java.util.Collections;
import java.util.Map;
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
     * environment
     */
    private final IEnvironment m_environment;
    /**
     * set of objects inside
     */
    private final Multimap<IObject<?>, Pair<Number, Number>> m_elements = Multimaps.synchronizedMultimap( HashMultimap.create() );
    /**
     * allowed speed
     */
    private final Double m_allowedspeed;
    /**
     * current position (lane start, lane end, position on the lane start, position on the lane end)
     */
    private final DoubleMatrix1D m_position;

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
        @Nonnull final IEnvironment p_environment,
        @Nonnull final DoubleMatrix1D p_position,
        @Nonnull final Number p_allowedspeed
    )
    {
        super( p_configuration, FUNCTOR, p_id );
        m_environment = p_environment;
        m_allowedspeed = p_allowedspeed.doubleValue();
        m_position = p_position;
    }

    /*
    @Override
    public final IObject<?> push( @Nonnull final IObject<?> p_object )
    {
        if ( ( this.inside( p_object ) ) && ( m_elements.add( p_object ) ) )
        {
            this.trigger(
                CTrigger.from(
                    ITrigger.EType.ADDGOAL,
                    CLiteral.from( "element", CRawTerm.from( p_object ) )
                )
            );
        }
        return p_object;
    }
    */

    /**
     * checking if a position is inside the area
     *
     * @param p_position position
     * @return is-inside flag
     */
    private boolean inside( final DoubleMatrix1D p_position )
    {
        return ( m_position.get( 0 ) <= p_position.get( 0 ) ) && ( p_position.get( 0 ) <= m_position.get( 1 ) )
            && ( m_position.get( 2 ) <= p_position.get( 1 ) ) && ( p_position.get( 1 ) <= m_position.get( 3 ) );
    }

    @Nonnull
    @Override
    public final DoubleMatrix1D position()
    {
        return m_position;
    }

    @Nonnull
    @Override
    public final DoubleMatrix1D nextposition()
    {
        return this.position();
    }

    @Nonnull
    @Override
    public final IObject<IArea> release()
    {
        return this;
    }

    @Override
    public final Map<String, Object> map( @Nonnull final EStatus p_status )
    {
        return Collections.emptyMap();
    }

    @Override
    public final IVehicle push( @Nonnull final IVehicle p_object, @Nonnull final DoubleMatrix1D p_start,
                                @Nonnull final DoubleMatrix1D p_end, @Nonnull final Number p_speed )
    {
        // check if start or end position is inside

        //http://www.w3ii.com/de/computer_graphics/computer_graphics_quick_guide.html
        return p_object;
    }


    @Override
    protected final Stream<ILiteral> individualliteral( final IObject<?> p_object )
    {
        return Stream.of(
            CLiteral.from( "allowedspeed", CRawTerm.from( m_allowedspeed ) )
        );
    }

    @Override
    public final IArea call() throws Exception
    {
        /*
        m_elements.parallelStream()
                  .filter( i -> !this.inside( i ) )
                  .filter( m_elements::remove )
                  .forEach( i -> this.trigger(
                      CTrigger.from(
                        ITrigger.EType.DELETEGOAL,
                        CLiteral.from( "element", CRawTerm.from( i ) )
                      )
                  ) );
        */

        return super.call();
    }

    // --- agent actions ---------------------------------------------------------------------------------------------------------------------------------------

    /**
     * returns the current speed
     *
     * @param p_object any object
     * @return speed of the object
     */
    @IAgentActionFilter
    @IAgentActionName( name = "vehicle/speed" )
    private double speed( @Nonnull final IObject<?> p_object )
    {
        if ( !( p_object instanceof IVehicle ) )
            throw new RuntimeException( MessageFormat.format( "speed value can be read for vehicles only, but it is: {0}", p_object ) );

        return p_object.<IVehicle>raw().speed();
    }

    /**
     * set the panalize of the cars
     *
     * @param p_object object
     * @param p_value value
     */
    @IAgentActionFilter
    @IAgentActionName( name = "vehicle/penalty" )
    private void penalty( @Nonnull final IObject<?> p_object, @Nonnull final Number p_value )
    {
        if ( !( p_object instanceof IVehicle ) )
            throw new RuntimeException( MessageFormat.format( "penality value can be set for vehicles only, but it is set to: {0}", p_object ) );

        p_object.<IVehicle>raw().penalty( p_value );
    }

    /**
     * send penalty to environment
     *
     * @param p_value value
     */
    @IAgentActionFilter
    @IAgentActionName( name = "environment/send" )
    private void sendpenalty(  @Nonnull final Number p_value )
    {
        m_environment.trigger( CTrigger.from( ITrigger.EType.ADDGOAL, CLiteral.from( "penalty", CRawTerm.from( p_value ) ) ) );
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
            if ( ( p_data == null ) || ( p_data.length < 3 ) )
                throw new RuntimeException( "not enough parameter for area" );

            return new ImmutableTriple<>(
                new CArea(
                    m_configuration,
                    MessageFormat.format( "{0} {1}", FUNCTOR, COUNTER.getAndIncrement() ),
                    (IEnvironment) p_data[0],
                    (DoubleMatrix1D) p_data[1],
                    (Number) p_data[2]
                ),
                CConfiguration.INSTANCE.getOrDefault( false, "agent", "area", "visible" ),
                Stream.of( FUNCTOR )
            );
        }
    }
}
