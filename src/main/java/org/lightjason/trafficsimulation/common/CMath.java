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

import cern.colt.matrix.DoubleMatrix1D;
import cern.colt.matrix.DoubleMatrix2D;
import cern.colt.matrix.doublealgo.Formatter;
import cern.colt.matrix.impl.DenseDoubleMatrix1D;
import cern.colt.matrix.impl.DenseDoubleMatrix2D;
import cern.colt.matrix.linalg.Algebra;
import cern.jet.math.Functions;
import org.apache.commons.lang3.tuple.ImmutablePair;
import org.apache.commons.lang3.tuple.Pair;
import org.apache.commons.math3.random.MersenneTwister;
import org.apache.commons.math3.random.RandomGenerator;
import org.apache.commons.math3.random.SynchronizedRandomGenerator;

import javax.annotation.Nonnull;
import java.util.function.Consumer;
import java.util.stream.IntStream;
import java.util.stream.Stream;


/**
 * class for global math algorithm
 */
public final class CMath
{
    /**
     * reference to global algebra instance
     */
    public static final Algebra ALGEBRA = Algebra.DEFAULT;
    /**
     * synchronized random generator
     */
    public static final RandomGenerator RANDOMGENERATOR = new SynchronizedRandomGenerator( new MersenneTwister() );
    /**
     * matrix formatter
     */
    public static final Formatter MATRIXFORMAT = new Formatter();

    static
    {
        MATRIXFORMAT.setRowSeparator( "; " );
        MATRIXFORMAT.setColumnSeparator( "," );
        MATRIXFORMAT.setPrintShape( false );
    }

    /**
     * pvate ctor
     */
    private CMath()
    {
    }

    /**
     * consums a stream of matrix objects
     *
     * @param p_stream stream
     * @param p_consumer consumer function
     * @return stream
     */
    public static Stream<DoubleMatrix1D> matrixconsumer( final Stream<DoubleMatrix1D> p_stream, final Consumer<String> p_consumer )
    {
        return p_stream.peek( i -> p_consumer.accept( MATRIXFORMAT.toString( i ) + " " ) );
    }

    /**
     * creates a rotation matrix
     *
     * @param p_alpha angel in radians
     * @return matrix
     *
     * @see https://en.wikipedia.org/wiki/Rotation_matrix
     */
    public static DoubleMatrix2D rotationmatrix( final double p_alpha )
    {
        final double l_sin = Math.sin( p_alpha );
        final double l_cos = Math.cos( p_alpha );
        return new DenseDoubleMatrix2D( new double[][]{{l_cos, l_sin}, {-l_sin, l_cos}} );
    }

    /**
     * returns the angel
     *
     * @param p_first first vector
     * @param p_second second vector
     * @return pair of angel in radians and boolean for calulation correctness
     */
    public static Pair<Double, Boolean> angle( final DoubleMatrix1D p_first, final DoubleMatrix1D p_second )
    {
        final double l_first = ALGEBRA.norm2( p_first );
        final double l_second = ALGEBRA.norm2( p_second );

        return ( l_first == 0 ) || ( l_second == 0 )
               ? new ImmutablePair<>( 0.0, false )
               : new ImmutablePair<>( Math.acos( ALGEBRA.mult( p_first, p_second ) / ( Math.sqrt( l_first ) * Math.sqrt( l_second ) ) ), true );
    }


    /**
     * returns the distance between to points
     *
     * @param p_first vector
     * @param p_second vector
     * @return distance
     */
    public static double distance( final DoubleMatrix1D p_first, final DoubleMatrix1D p_second )
    {
        return ALGEBRA.norm2(
            new DenseDoubleMatrix1D( p_second.toArray() )
                .assign( p_first, Functions.minus )
        );
    }

    /**
     * returns a stream of realtive positions
     *
     * @param p_radius radius
     * @param p_from from-angle
     * @param p_to to-angle
     * @return stream with relative position
     */
    @Nonnull
    public static Stream<DoubleMatrix1D> cellangle( @Nonnull final Number p_radius, @Nonnull final Number p_from, @Nonnull final Number p_to )
    {
        return IntStream.rangeClosed( -p_radius.intValue(), p_radius.intValue() )
                 .parallel()
                 .boxed()
                 .flatMap( y -> IntStream.rangeClosed( -p_radius.intValue(), p_radius.intValue() )
                                         .boxed()
                                         .filter( x -> positioninsideangle( y, x, p_from.doubleValue(), p_to.doubleValue() ) )
                                         .map( x -> new DenseDoubleMatrix1D( new double[]{y, x} ) )
                 );
    }

    /**
     * checks if a point is within the angel of the visual range
     *
     * @param p_ypos y-position of center
     * @param p_xpos x-position of center
     * @param p_from start angle
     * @param p_to end angle
     * @return boolean if the position is inside
     */
    private static boolean positioninsideangle( final double p_ypos, final double p_xpos, final double p_from, final double p_to )
    {
        double l_angle = Math.toDegrees( Math.atan2( p_ypos, p_xpos ) );
        l_angle = l_angle < 0 ? 360 + l_angle : l_angle;
        return !Double.isNaN( l_angle ) && ( p_from <= l_angle ) && ( l_angle <= p_to );
    }

}
