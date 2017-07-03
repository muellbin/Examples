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

package org.lightjason.trafficsimulation.elements.environment;

import cern.colt.matrix.DoubleMatrix1D;
import cern.colt.matrix.ObjectMatrix2D;
import cern.colt.matrix.impl.DenseDoubleMatrix1D;
import cern.colt.matrix.impl.SparseObjectMatrix2D;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.tuple.ImmutableTriple;
import org.apache.commons.lang3.tuple.Triple;
import org.apache.commons.math3.distribution.AbstractRealDistribution;
import org.lightjason.agentspeak.action.binding.IAgentAction;
import org.lightjason.agentspeak.action.binding.IAgentActionFilter;
import org.lightjason.agentspeak.action.binding.IAgentActionName;
import org.lightjason.agentspeak.configuration.IAgentConfiguration;
import org.lightjason.agentspeak.language.ILiteral;
import org.lightjason.trafficsimulation.common.CConfiguration;
import org.lightjason.trafficsimulation.elements.IBaseObject;
import org.lightjason.trafficsimulation.elements.IObject;
import org.lightjason.trafficsimulation.elements.area.IArea;
import org.lightjason.trafficsimulation.elements.vehicle.IVehicle;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.io.InputStream;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.CopyOnWriteArraySet;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.Stream;


/**
 * environment agent
 */
@IAgentAction
public final class CEnvironment extends IBaseObject<IEnvironment> implements IEnvironment
{
    /**
     * serial id
     */
    private static final long serialVersionUID = -4598520582091540701L;
    /**
     * literal functor
     */
    private static final String FUNCTOR = "environment";
    /**
     * shutdown flag
     */
    private final AtomicBoolean m_shutdown = new AtomicBoolean();
    /**
     * hastset with areas
     */
    private final Set<IArea> m_areas = new CopyOnWriteArraySet<>();
    /**
     * grid
     */
    private final AtomicReference<ObjectMatrix2D> m_grid = new AtomicReference<>( new SparseObjectMatrix2D( 0, 0) );

    /**
     * ctor
     *
     * @param p_configuration agent configuration
     * @param p_id name of the object
     */
    private CEnvironment( @Nonnull final IAgentConfiguration<IEnvironment> p_configuration, @Nonnull final String p_id )
    {
        super( p_configuration, FUNCTOR, p_id );
    }

    @Override
    public final DoubleMatrix1D position()
    {
        return new DenseDoubleMatrix1D( new double[]{m_grid.get().columns(), m_grid.get().rows()} );
    }

    @Override
    public final boolean shutdown()
    {
        return m_shutdown.get();
    }

    @Nonnull
    @Override
    public final IObject<?> set( @Nonnull final IObject<?> p_object, @Nonnull final DoubleMatrix1D p_position )
    {
        return p_object;
    }

    @Override
    protected final Stream<ILiteral> individualliteral( final Stream<IObject<?>> p_object )
    {
        return Stream.empty();
    }

    @IAgentActionFilter
    @IAgentActionName( name = "simulation/initialize" )
    private void simulationinitialize( final Number p_width, final Number p_height )
    {
        if ( m_grid.get() != null )
            throw new RuntimeException( "world is initialized" );

        m_grid.set( new SparseObjectMatrix2D( p_width.intValue(), p_height.intValue() ) );
        m_areas.clear();
    }

    /**
     * shuts down the current execution
     */
    @IAgentActionFilter
    @IAgentActionName( name = "simulation/shutdown" )
    private void simulationshutdown()
    {
        m_shutdown.set( true );
    }

    /**
     * creates an area
     * @param p_xlowerbound x-value left-lower bound
     * @param p_ylowerbound y-value left-lower bound
     * @param p_xupperbound x-value right-upper bound
     * @param p_yupperbound y-value right-upper bound
     */
    @IAgentActionFilter
    @IAgentActionName( name = "area/create" )
    private void areacreate( final Number p_xlowerbound, final Number p_ylowerbound, final Number p_xupperbound, final Number p_yupperbound )
    {
    }

    /**
     * creates a vehicle generator
     *
     * @param p_distribution distribution
     */
    @IAgentActionFilter
    @IAgentActionName( name = "vehicle/create" )
    private void createvehiclegenerator( @Nonnull final AbstractRealDistribution p_distribution )
    {

    }

    /**
     * checks if an object element is a vehicle
     *
     * @param p_object any object
     * @return is vehicle
     */
    @IAgentActionFilter
    @IAgentActionName( name = "element/isvehicle" )
    private boolean isvehicle( @Nonnull final IObject<?> p_object )
    {
        return p_object instanceof IVehicle;
    }

    // ---------------------------------------------------------------------------------------------------------------------------------------------------------

    /**
     * environment generator
     */
    public static final class CGenerator extends IBaseGenerator<IEnvironment>
    {

        /**
         * ctor
         *
         * @param p_asl map with asl codes
         * @throws Exception on any error
         */
        public CGenerator( @Nonnull final Map<String, String> p_asl ) throws Exception
        {
            super( IOUtils.toInputStream( p_asl.get( "environment" ), "UTF-8" ), CEnvironment.class );
        }

        @Override
        public final IGenerator<IEnvironment> resetcount()
        {
            return this;
        }

        @Override
        protected final Triple<IEnvironment, Boolean, Stream<String>> generate( @Nullable final Object... p_data )
        {
            return new ImmutableTriple<>(
                new CEnvironment(
                    m_configuration,
                    FUNCTOR

                ),
                CConfiguration.INSTANCE.getOrDefault( false, "agent", "environment", "visible" ),
                Stream.of( FUNCTOR )
            );
        }
    }

}
