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
import org.lightjason.agentspeak.agent.IAgent;
import org.lightjason.agentspeak.configuration.IAgentConfiguration;
import org.lightjason.agentspeak.language.CLiteral;
import org.lightjason.agentspeak.language.CRawTerm;
import org.lightjason.agentspeak.language.ILiteral;
import org.lightjason.agentspeak.language.execution.IVariableBuilder;
import org.lightjason.agentspeak.language.instantiable.IInstantiable;
import org.lightjason.agentspeak.language.instantiable.plan.trigger.CTrigger;
import org.lightjason.agentspeak.language.instantiable.plan.trigger.ITrigger;
import org.lightjason.agentspeak.language.variable.CConstant;
import org.lightjason.agentspeak.language.variable.IVariable;
import org.lightjason.trafficsimulation.common.CConfiguration;
import org.lightjason.trafficsimulation.common.CMath;
import org.lightjason.trafficsimulation.elements.EUnit;
import org.lightjason.trafficsimulation.elements.IBaseObject;
import org.lightjason.trafficsimulation.elements.IObject;
import org.lightjason.trafficsimulation.elements.environment.IEnvironment;
import org.lightjason.trafficsimulation.elements.vehicle.IVehicle;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.io.InputStream;
import java.text.MessageFormat;
import java.util.Collections;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.atomic.AtomicLong;
import java.util.stream.Collectors;
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
    private final Set<IObject<?>> m_elements = Collections.synchronizedSet( new HashSet<>() );
    /**
     * allowed speed
     */
    private final Double m_allowedspeed;
    /**
     * current position (lane start, lane end, position on the lane start, position on the lane end)
     */
    private final DoubleMatrix1D m_position;
    /**
     * length of the area
     */
    private final double m_length;


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

        if ( m_allowedspeed < 10 )
            throw new RuntimeException( "maximumspeed is to low" );
        if ( m_position.getQuick( 0 ) > m_position.getQuick( 1 ) )
            throw new RuntimeException( "lane index must be from lower to upper" );
        if ( m_position.getQuick( 2 ) > m_position.getQuick( 3 ) )
            throw new RuntimeException( "position index must be from lower to upper" );

        // calculate km in cell position
        m_length = m_position.getQuick( 3 ) - m_position.getQuick( 2 );
        m_position.setQuick( 2, EUnit.INSTANCE.kilometertocell( m_position.getQuick( 2 ) ).doubleValue() );
        m_position.setQuick( 3, EUnit.INSTANCE.kilometertocell( m_position.getQuick( 3 ) ).doubleValue() );

    }

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
        final DoubleMatrix1D l_line = CMath.lineclipping(
                                        m_position.viewPart( 0, 2 ),
                                        m_position.viewPart( 2, 2 ),
                                        p_start,
                                        p_end
        );

        if ( l_line.size() != 0 )
            this.executetrigger(
                "vehicle/move",
                p_object,
                p_speed,
                EUnit.INSTANCE.celltokilometer(
                    CMath.distance(
                        l_line.viewPart( 0, 2 ),
                        l_line.viewPart( 2, 2 )
                    )
                )
            );

        return p_object;
    }

    /**
     * executes a trigger
     *
     * @param p_functor functor
     * @param p_object object
     * @param p_speed speed
     * @param p_distance distance
     */
    private void executetrigger( @Nonnull final String p_functor, @Nonnull final IObject<?> p_object,
                                 @Nonnull final Number p_speed, @Nonnull final Number p_distance )
    {
        if ( p_distance.doubleValue() == 0 )
            return;

        m_elements.add( p_object );
        this.trigger(
            CTrigger.from(
                ITrigger.EType.ADDGOAL,
                CLiteral.from(
                    p_functor,
                    CLiteral.from( "vehicle", CRawTerm.from( p_object ) ),
                    CLiteral.from( "speed", CRawTerm.from( p_speed ) ),
                    CLiteral.from( "distance", CRawTerm.from( p_distance ) )
                )
            )
        );
    }

    @Override
    public final double allowedspeed()
    {
        return m_allowedspeed;
    }

    @Override
    public final double length()
    {
        return m_length;
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
        super.call();

        m_elements.removeAll(
            m_elements.parallelStream()
                      .filter( i -> !this.inside( i.position() ) )
                      .peek( i -> CTrigger.from(
                          ITrigger.EType.ADDGOAL,
                          CLiteral.from(
                              "vehicle/leave",
                              CRawTerm.from( i )
                          )
                      ) )
                      .collect( Collectors.toSet() )
        );

        return this;
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
    private double speed( final IObject<?> p_object )
    {
        if ( !( p_object instanceof IVehicle ) )
            throw new RuntimeException( MessageFormat.format( "speed value can be read for vehicles only, but it is: {0}", p_object ) );

        return p_object.<IVehicle>raw().speed();
    }

    /**
     * reutns the maximum speed of a vehicle
     *
     * @param p_object object
     * @return maximum speed of the object
     */
    @IAgentActionFilter
    @IAgentActionName( name = "vehicle/maximumspeed" )
    private double maximumspeed( final IObject<?> p_object )
    {
        if ( !( p_object instanceof IVehicle ) )
            throw new RuntimeException( MessageFormat.format( "speed value can be read for vehicles only, but it is: {0}", p_object ) );

        return p_object.<IVehicle>raw().maximumspeed();
    }

    /**
     * set the panalize of the cars
     *
     * @param p_object object
     * @param p_value value
     */
    @IAgentActionFilter
    @IAgentActionName( name = "vehicle/penalty" )
    private void penalty( final IObject<?> p_object, final Number p_value )
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
            super( p_stream, CArea.class, new CVariableBuilder() );
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

    /**
     * variable builder of vehicle
     */
    private static class CVariableBuilder implements IVariableBuilder
    {

        @Override
        public final Stream<IVariable<?>> apply( final IAgent<?> p_agent, final IInstantiable p_instance )
        {
            final IArea l_area = p_agent.<IArea>raw();

            return Stream.of(
                new CConstant<>( "Length", l_area.length() ),
                new CConstant<>( "AllowedSpeed", l_area.allowedspeed() )
            );
        }
    }
}
