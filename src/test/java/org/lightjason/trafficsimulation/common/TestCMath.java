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

package org.lightjason.trafficsimulation.common;

import cern.colt.matrix.impl.DenseDoubleMatrix1D;
import org.junit.Assert;
import org.junit.Test;
import org.lightjason.trafficsimulation.IBaseTest;

import java.util.Arrays;
import java.util.stream.Stream;


/**
 * test math class
 */
public final class TestCMath extends IBaseTest
{
    /**
     * line clipping test inner-line
     */
    @Test
    public final void lineclippinginner()
    {
        Assert.assertArrayEquals(
            Arrays.stream(
                CMath.lineclipping(
                    new DenseDoubleMatrix1D( new double[]{10, 10} ), new DenseDoubleMatrix1D( new double[]{50, 80} ),
                    new DenseDoubleMatrix1D( new double[]{20, 20} ), new DenseDoubleMatrix1D( new double[]{40, 40} )
                ).toArray()
            ).boxed().toArray(),

            Stream.of( 20.0, 20.0, 40.0, 40.0 ).toArray()
        );
    }

    /**
     * line clipping with half inside
     */
    @Test
    public final void lineclippinghalf()
    {
        Assert.assertArrayEquals(
            Arrays.stream(
                CMath.lineclipping(
                    new DenseDoubleMatrix1D( new double[]{10, 10} ), new DenseDoubleMatrix1D( new double[]{50, 80} ),
                    new DenseDoubleMatrix1D( new double[]{5, 5} ), new DenseDoubleMatrix1D( new double[]{15, 25} )
                ).toArray()
            ).boxed().toArray(),

            Stream.of( 10.0, 15.0, 15.0, 25.0 ).toArray()
        );

        Assert.assertArrayEquals(
            Arrays.stream(
                CMath.lineclipping(
                    new DenseDoubleMatrix1D( new double[]{10, 10} ), new DenseDoubleMatrix1D( new double[]{50, 80} ),
                    new DenseDoubleMatrix1D( new double[]{45, 55} ), new DenseDoubleMatrix1D( new double[]{65, 85} )
                ).toArray()
            ).mapToInt( i -> (int)i ).boxed().toArray(),

            Stream.of( 45, 55, 50, 62 ).toArray()
        );
    }

    /**
     * line clipping outside
     */
    @Test
    public final void lineclippingoutside()
    {
        Assert.assertEquals(
            CMath.lineclipping(
                new DenseDoubleMatrix1D( new double[]{10, 10} ), new DenseDoubleMatrix1D( new double[]{50, 80} ),
                new DenseDoubleMatrix1D( new double[]{105, 90} ), new DenseDoubleMatrix1D( new double[]{150, 150} )
            ).size(),
            0
        );

        Assert.assertEquals(
            CMath.lineclipping(
                new DenseDoubleMatrix1D( new double[]{10, 10} ), new DenseDoubleMatrix1D( new double[]{50, 80} ),
                new DenseDoubleMatrix1D( new double[]{8, 8} ), new DenseDoubleMatrix1D( new double[]{20, 8} )
            ).size(),
            0
        );

        Assert.assertEquals(
            CMath.lineclipping(
                new DenseDoubleMatrix1D( new double[]{10, 10} ), new DenseDoubleMatrix1D( new double[]{50, 80} ),
                new DenseDoubleMatrix1D( new double[]{8, 8} ), new DenseDoubleMatrix1D( new double[]{8, 20} )
            ).size(),
            0
        );

        Assert.assertEquals(
            CMath.lineclipping(
                new DenseDoubleMatrix1D( new double[]{10, 10} ), new DenseDoubleMatrix1D( new double[]{50, 80} ),
                new DenseDoubleMatrix1D( new double[]{60, 8} ), new DenseDoubleMatrix1D( new double[]{60, 20} )
            ).size(),
            0
        );
    }

    /**
     * test angle
     */
    @Test
    public final void angle()
    {
        Assert.assertEquals(
            CMath.angle( new DenseDoubleMatrix1D( new double[]{0, 1} ), new DenseDoubleMatrix1D( new double[]{1, 0} ) ),
            90.0
        );
    }

    /**
     * main
     *
     * @param p_args arguments
     */
    public static void main( final String[] p_args )
    {
        new TestCMath().invoketest();
    }

}
