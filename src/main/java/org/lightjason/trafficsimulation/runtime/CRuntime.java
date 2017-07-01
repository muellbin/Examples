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

import javax.annotation.Nonnull;
import java.util.concurrent.atomic.AtomicReference;
import java.util.function.Supplier;


/**
 * runtime singletone instance
 */
public final class CRuntime implements IRuntime
{
    /**
     * instance
     */
    public static final CRuntime INSTANCE = new CRuntime();
    /**
     * execution task
     */
    private final AtomicReference<ITask> m_task = new AtomicReference<>();
    /**
     * supplier of tasks
     */
    private Supplier<ITask> m_tasksupply = () -> ITask.EMPTY;


    /**
     * ctor
     */
    private CRuntime()
    {
    }

    @Override
    public final void run()
    {
        m_task.compareAndSet( null, m_tasksupply.get() );
        m_task.get().run();
        m_task.compareAndSet( m_task.get(), null );
    }


    @Override
    public IRuntime supplier( @Nonnull final Supplier<ITask> p_supplier )
    {
        m_tasksupply = p_supplier;
        return this;
    }

    @Override
    public final boolean running()
    {
        return m_task.get() != null;
    }
}
