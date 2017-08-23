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

import org.apache.commons.lang3.tuple.Triple;
import org.lightjason.agentspeak.agent.IAgent;
import org.lightjason.agentspeak.agent.IBaseAgent;
import org.lightjason.agentspeak.beliefbase.CBeliefbase;
import org.lightjason.agentspeak.beliefbase.storage.CMultiStorage;
import org.lightjason.agentspeak.beliefbase.storage.CSingleStorage;
import org.lightjason.agentspeak.beliefbase.view.IView;
import org.lightjason.agentspeak.common.CCommon;
import org.lightjason.agentspeak.configuration.CDefaultAgentConfiguration;
import org.lightjason.agentspeak.configuration.IAgentConfiguration;
import org.lightjason.agentspeak.generator.IBaseAgentGenerator;
import org.lightjason.agentspeak.language.CLiteral;
import org.lightjason.agentspeak.language.CRawTerm;
import org.lightjason.agentspeak.language.ILiteral;
import org.lightjason.agentspeak.language.ITerm;
import org.lightjason.agentspeak.language.execution.IVariableBuilder;
import org.lightjason.agentspeak.language.fuzzy.operator.IFuzzyBundle;
import org.lightjason.agentspeak.language.instantiable.IInstantiable;
import org.lightjason.agentspeak.language.instantiable.plan.IPlan;
import org.lightjason.agentspeak.language.instantiable.rule.IRule;
import org.lightjason.agentspeak.language.unify.IUnifier;
import org.lightjason.agentspeak.language.variable.CConstant;
import org.lightjason.agentspeak.language.variable.IVariable;
import org.lightjason.trafficsimulation.common.CConfiguration;
import org.lightjason.trafficsimulation.ui.EHTTPServer;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.io.InputStream;
import java.util.Collection;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;


/**
 * base agent object
 *
 * @tparam T agent type
 */
public abstract class IBaseObject<T extends IObject<?>> extends IBaseAgent<T> implements IObject<T>
{
    /**
     * serial id
     */
    private static final long serialVersionUID = 6278806527768825298L;
    /**
     * functor definition
     */
    private final String m_functor;
    /**
     * id of the object
     */
    private final String m_id;
    /**
     * reference to external beliefbase
     */
    private final IView m_external;



    /**
     * ctor
     *
     * @param p_configuration agent configuration
     * @param p_functor functor of the object literal
     * @param p_id name of the object
     */
    protected IBaseObject( @Nonnull final IAgentConfiguration<T> p_configuration, @Nonnull final String p_functor, @Nonnull final String p_id )
    {
        super( p_configuration );

        m_id = p_id;
        m_functor = p_functor;
        m_external = m_beliefbase.beliefbase().view( "dynamic" );
    }

    @Override
    @Nonnull
    public final String id()
    {
        return m_id;
    }

    @Override
    @Nonnull
    public final ILiteral literal( @Nonnull final IObject<?> p_object )
    {
        return CLiteral.from(
            m_functor,

            Stream.of(
                CLiteral.from( "id", CRawTerm.from( m_id ) ),
                CLiteral.from( "object", CRawTerm.from( this ) ),
                CLiteral.from(
                    "data",
                    CLiteral.from( "dynamic", m_external.stream().map( i -> i.shallowcopysuffix() ) ),
                    CLiteral.from( "static", this.staticliteral( p_object ).sorted().sequential() )
                )
            )
        );
    }

    /**
     * define object literal addons
     *
     * @param p_object calling object
     * @return literal stream
     */
    protected abstract Stream<ITerm> staticliteral( final IObject<?> p_object );

    @Override
    public final int hashCode()
    {
        return m_id.hashCode();
    }

    @Override
    public final boolean equals( final Object p_object )
    {
        return ( p_object != null ) && ( p_object instanceof IObject<?> ) && ( this.hashCode() == p_object.hashCode() );
    }


    // ---------------------------------------------------------------------------------------------------------------------------------------------------------

    /**
     * base agent generator
     *
     * @tparam T agent type
     */
    protected abstract static class IBaseGenerator<T extends IObject<?>> extends IBaseAgentGenerator<T> implements IGenerator<T>
    {
        /**
         * @param p_stream asl stream
         * @param p_agentclass agent class
         * @throws Exception thrown on any error
         */
        protected IBaseGenerator( @Nonnull final InputStream p_stream, @Nonnull final Class<? extends T> p_agentclass ) throws Exception
        {
            super(
                p_stream,
                Stream.concat( CConfiguration.INSTANCE.actions(), CCommon.actionsFromAgentClass( p_agentclass ) ).collect( Collectors.toSet() )
            );
        }

        /**
         * ctor
         * @param p_stream asl stream
         * @param p_agentclass agent class
         * @param p_variablebuilder variable builder
         * @throws Exception thrown on any error
         */
        protected IBaseGenerator( @Nonnull final InputStream p_stream, @Nonnull final Class<? extends T> p_agentclass,
                                  @Nonnull final IVariableBuilder p_variablebuilder ) throws Exception
        {
            super(
                p_stream,
                Stream.concat( CConfiguration.INSTANCE.actions(), CCommon.actionsFromAgentClass( p_agentclass ) ).collect( Collectors.toSet() ),
                p_variablebuilder
            );
        }

        @Override
        public final T generatesingle( @Nullable final Object... p_data )
        {
            final Triple<T, Boolean, Stream<String>> l_data = this.generate( p_data );
            return l_data == null ? null : EHTTPServer.register( l_data );
        }

        /**
         * generates the agent
         *
         * @param p_data creating arguments
         * @return agent object, visibility and group names
         */
        @Nullable
        protected abstract Triple<T, Boolean, Stream<String>> generate( @Nullable final Object... p_data );

        @Override
        protected IAgentConfiguration<T> configuration( @Nonnull final IFuzzyBundle<Boolean> p_fuzzy, @Nonnull final Collection<ILiteral> p_initalbeliefs,
                                                        @Nonnull final Set<IPlan> p_plans, @Nonnull final Set<IRule> p_rules,
                                                        @Nullable final ILiteral p_initialgoal,
                                                        @Nonnull final IUnifier p_unifier, @Nonnull final IVariableBuilder p_variablebuilder
        )
        {
            return new CAgentConfiguration( p_fuzzy, p_initalbeliefs, p_plans, p_rules, p_initialgoal, p_unifier, p_variablebuilder );
        }


        /**
         * agent configuration
         */
        private final class CAgentConfiguration extends CDefaultAgentConfiguration<T>
        {
            /**
             * ctor
             *
             * @param p_fuzzy fuzzy bundle
             * @param p_initalbeliefs initial beliefs
             * @param p_plans plans
             * @param p_rules rules
             * @param p_initialgoal initial goal
             * @param p_unifier unifier
             * @param p_variablebuilder variable builder
             */
            CAgentConfiguration( @Nonnull final IFuzzyBundle<Boolean> p_fuzzy, @Nonnull final Collection<ILiteral> p_initalbeliefs,
                            @Nonnull final Set<IPlan> p_plans, @Nonnull final Set<IRule> p_rules, @Nullable final ILiteral p_initialgoal,
                            @Nonnull final IUnifier p_unifier, @Nonnull final IVariableBuilder p_variablebuilder
            )
            {
                super( p_fuzzy, p_initalbeliefs, p_plans, p_rules, p_initialgoal, p_unifier, p_variablebuilder );
            }

            @Nonnull
            @Override
            public final IView beliefbase()
            {
                final IView l_view = new CBeliefbase( new CMultiStorage<>() ).create( BELIEFBASEROOTNAME );
                l_view.add( new CBeliefbase( new CSingleStorage<>() ).create( "extern", l_view ) );

                // add initial beliefs and clear initial beliefbase trigger
                m_initialbeliefs.parallelStream().forEach( i -> l_view.add( i.shallowcopy() ) );
                l_view.trigger();

                return l_view;
            }
        }
    }


    /**
     * variable builder of any object
     */
    protected abstract static class IBaseVariableBuilder implements IVariableBuilder
    {

        @Override
        public Stream<IVariable<?>> apply( final IAgent<?> p_agent, final IInstantiable p_instantiable )
        {
            return Stream.of(
                new CConstant<>( "ID", p_agent.<IObject<?>>raw().id() )
            );
        }
    }

}
