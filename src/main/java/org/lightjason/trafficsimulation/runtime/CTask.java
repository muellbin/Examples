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

import org.apache.commons.io.IOUtils;
import org.lightjason.trafficsimulation.common.CCommon;
import org.lightjason.trafficsimulation.elements.IObject;
import org.lightjason.trafficsimulation.elements.area.CArea;
import org.lightjason.trafficsimulation.elements.area.IArea;
import org.lightjason.trafficsimulation.elements.environment.CEnvironment;
import org.lightjason.trafficsimulation.elements.environment.IEnvironment;
import org.lightjason.trafficsimulation.elements.vehicle.CVehicle;
import org.lightjason.trafficsimulation.elements.vehicle.IVehicle;
import org.lightjason.trafficsimulation.ui.api.CMessage;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.Map;
import java.util.concurrent.Callable;
import java.util.logging.Logger;


/**
 * runtime task instance
 */
public class CTask implements ITask
{
    /**
     * logger
     */
    private static final Logger LOGGER = CCommon.logger( ITask.class );
    /**
     * simulation objects
     */
    private final Map<String, IObject<?>> m_elements;
    /**
     * environment reference
     */
    private final IEnvironment m_environment;


    /**
     * ctor
     *
     * @param p_agentdefinition asl map
     * @param p_elements element map
     * @todo exception handling for evnrionment agent generator
     */
    public CTask( @Nonnull final Map<String, ERuntime.CAgentDefinition> p_agentdefinition, @Nonnull final Map<String, IObject<?>> p_elements )
    {
        m_elements = p_elements;

        // --- initialize generators ---
        final IEnvironment.IGenerator<IEnvironment> l_environmentgenerator = this.generatorenvironment( p_agentdefinition );

        if ( l_environmentgenerator == null )
            m_environment = null;
        else
            m_environment = l_environmentgenerator.generatesingle(
                p_elements,
                this.generatorvehicle( p_agentdefinition, "defaultvehicle", IVehicle.ETYpe.DEFAULTVEHICLE ),
                this.generatorvehicle( p_agentdefinition,
                                       p_agentdefinition.entrySet()
                                                        .stream()
                                                        .filter( i -> i.getValue().getactive() )
                                                        .findFirst()
                                                        .map( Map.Entry::getKey )
                                                        .orElseThrow( () -> new RuntimeException( CCommon.languagestring( this, "notactivefound" ) ) ),
                                       IVehicle.ETYpe.USERVEHICLE
                ),
                this.generatorarea( p_agentdefinition )
            );
    }


    /**
     * gets a generator of a vehicle
     *
     * @param p_agents agent map
     * @param p_agent agent id
     * @param p_type vehicle type
     * @return null or generator
     */
    @Nullable
    private IVehicle.IGenerator<IVehicle> generatorvehicle( @Nonnull final Map<String, ERuntime.CAgentDefinition> p_agents,
                                                            @Nonnull final String p_agent, @Nonnull final IVehicle.ETYpe p_type )
    {
        final ERuntime.CAgentDefinition l_agentdefinition = p_agents.get( p_agent );
        try
        {
            return new CVehicle.CGenerator(
                IOUtils.toInputStream( l_agentdefinition.getasl(), "UTF-8" ),
                l_agentdefinition.getvisibility(),
                p_type
            ).resetcount();
        }
        catch ( final Exception l_exception )
        {
            CMessage.CInstance.INSTANCE.write(
                CMessage.EType.ERROR,
                CCommon.languagestring( this, "initialize", p_agent ),
                l_exception.getLocalizedMessage()
            );
            return null;
        }
    }


    /**
     * area generator
     *
     * @param p_agents agent map
     * @return null or generator
     */
    @Nullable
    private IArea.IGenerator<IArea> generatorarea( @Nonnull final Map<String, ERuntime.CAgentDefinition> p_agents )
    {
        try
        {
            return new CArea.CGenerator( IOUtils.toInputStream( p_agents.get( "area" ).getasl(), "UTF-8" ) )
                .resetcount();
        }
        catch ( final Exception l_exception )
        {
            CMessage.CInstance.INSTANCE.write(
                CMessage.EType.ERROR,
                CCommon.languagestring( this, "initialize", "area" ),
                l_exception.getLocalizedMessage()
            );
            return null;
        }
    }


    /**
     * environment generator
     *
     * @param p_agents agent map
     * @return null or generator
     */
    @Nullable
    private IEnvironment.IGenerator<IEnvironment> generatorenvironment( @Nonnull final Map<String, ERuntime.CAgentDefinition> p_agents )
    {
        try
        {
            return new CEnvironment.CGenerator( p_agents.get( "environment" ).getasl() ).resetcount();
        }
        catch ( final Exception l_exception )
        {
            CMessage.CInstance.INSTANCE.write(
                CMessage.EType.ERROR,
                CCommon.languagestring( this, "initialize", "environment" ),
                l_exception.getLocalizedMessage()
            );
            return null;
        }
    }



    /**
     * execute any object
     *
     * @param p_object callable
     */
    private static void execute( @Nonnull final Callable<?> p_object )
    {
        try
        {
            p_object.call();
        }
        catch ( final Exception l_execution )
        {
            LOGGER.warning( l_execution.getLocalizedMessage() );
        }
    }


    @Override
    public final ITask call() throws Exception
    {
        if ( m_environment == null )
            return this;

        // --- execute objects ---
        CMessage.CInstance.INSTANCE.write(
            CMessage.EType.SUCCESS,
            CCommon.languagestring( this, "initialize", "" ),
            CCommon.languagestring( this, "simulationstart" )
        );

        while ( true )
        {
            m_elements.values().parallelStream().forEach( CTask::execute );
            try
            {
                Thread.sleep( ERuntime.INSTANCE.time().get() );
            }
            catch ( final InterruptedException l_exception )
            {
                break;
            }
        }

        CMessage.CInstance.INSTANCE.write(
            CMessage.EType.SUCCESS,
            CCommon.languagestring( this, "shutdown" ),
            CCommon.languagestring( this, "simulationstop" )
        );

        return this;
    }

}
