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
import org.apache.commons.lang3.tuple.ImmutableTriple;
import org.apache.commons.lang3.tuple.Triple;
import org.lightjason.agentspeak.action.binding.IAgentAction;
import org.lightjason.agentspeak.action.binding.IAgentActionFilter;
import org.lightjason.agentspeak.action.binding.IAgentActionName;
import org.lightjason.agentspeak.configuration.IAgentConfiguration;
import org.lightjason.agentspeak.language.ILiteral;
import org.lightjason.trafficsimulation.common.CConfiguration;
import org.lightjason.trafficsimulation.elements.IBaseObject;
import org.lightjason.trafficsimulation.elements.IObject;
import org.lightjason.trafficsimulation.elements.area.IArea;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.io.InputStream;
import java.util.Set;
import java.util.concurrent.CopyOnWriteArraySet;
import java.util.concurrent.atomic.AtomicBoolean;
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
    private final ObjectMatrix2D m_grid;

    /**
     * ctor
     *
     * @param p_configuration agent configuration
     * @param p_id name of the object
     * @param p_position size of the world
     */
    private CEnvironment( @Nonnull final IAgentConfiguration<IEnvironment> p_configuration,
                          @Nonnull final String p_id,
                          @Nonnull final DoubleMatrix1D p_position )
    {
        super( p_configuration, FUNCTOR, p_id, p_position );
        m_grid = new SparseObjectMatrix2D( (int) m_position.get( 0 ), (int) m_position.get( 1 ) );
    }

    @Override
    public final boolean shutdown()
    {
        return m_shutdown.get();
    }

    @Override
    public final IObject<?> set( final IObject<?> p_object, final DoubleMatrix1D p_position )
    {
        return p_object;
    }

    @Override
    protected final Stream<ILiteral> individualliteral( final Stream<IObject<?>> p_object )
    {
        return Stream.empty();
    }

    @IAgentActionFilter
    @IAgentActionName( name = "simulation/shutdown" )
    private void actionshutdown()
    {
        m_shutdown.set( true );
    }

    @IAgentActionFilter
    @IAgentActionName( name = "area/create" )
    private void createarea( final Number p_xlowerbound, final Number p_ylowerbound, final Number p_xupperbound, final Number p_yupperbound )
    {
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
         * @param p_stream stream
         * @throws Exception on any error
         */
        public CGenerator( @Nonnull final InputStream p_stream ) throws Exception
        {
            super( p_stream, CEnvironment.class );
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
                    FUNCTOR,
                    new DenseDoubleMatrix1D( new double[]{
                        CConfiguration.INSTANCE.<Number>getOrDefault( 0, "main", "dimension", "width" ).doubleValue(),
                        CConfiguration.INSTANCE.<Number>getOrDefault( 0, "main", "dimension", "height" ).doubleValue()
                    } )
                ),
                CConfiguration.INSTANCE.getOrDefault( false, "agent", "environment", "visible" ),
                Stream.of( FUNCTOR )
            );
        }
    }

}
