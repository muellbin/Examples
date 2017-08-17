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
import javax.annotation.Nullable;
import java.util.Objects;
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
    public static Number distance( final DoubleMatrix1D p_first, final DoubleMatrix1D p_second )
    {
        return Math.sqrt( ALGEBRA.norm2(
            new DenseDoubleMatrix1D( p_second.toArray() )
                .assign( p_first, Functions.minus )
        ) );
    }

    /**
     * returns a stream all coordinates
     * within an arc
     *
     * @param p_radius radius
     * @param p_from from-angle in degree
     * @param p_to to-angle in degree
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
                                         .map( x -> new DenseDoubleMatrix1D( new double[]{y, x} ) )
                                         .filter( i ->
                                         {
                                             final double l_angle = Math.toDegrees( Math.atan2( i.getQuick( 0 ), i.getQuick( 1 ) ) );
                                             return ( !Double.isNaN( l_angle ) ) && ( p_from.doubleValue() <= l_angle  ) && ( l_angle <= p_to.doubleValue() );
                                         } )

                 );
    }

    /**
     * line clipping
     *
     * @param p_leftupper left-upper corner of the rectangle
     * @param p_rightbottom right-bottom corner of the rectangle
     * @param p_start line start point
     * @param p_end line end point
     * @return empty vector or clipped line
     * @see https://en.wikipedia.org/wiki/Liang%E2%80%93Barsky_algorithm
     * @see http://www.thecrazyprogrammer.com/2017/02/liang-barsky-line-clipping-algorithm.html
     */
    @Nonnull
    public static DoubleMatrix1D lineclipping( @Nonnull final DoubleMatrix1D p_leftupper, @Nonnull final DoubleMatrix1D p_rightbottom,
                                     @Nonnull final DoubleMatrix1D p_start, @Nonnull final DoubleMatrix1D p_end )
    {
        final double[] l_pval = Stream.of(
            -( p_end.get( 1 ) - p_start.getQuick( 1 ) ),
            p_end.get( 1 ) - p_start.getQuick( 1 ),
            -( p_end.get( 0 ) - p_start.getQuick( 0 ) ),
            p_end.get( 0 ) - p_start.getQuick( 0 )
        ).mapToDouble( i -> i ).toArray();

        final double[] l_qval = Stream.of(
            p_start.get( 1 ) - p_leftupper.getQuick( 1 ),
            p_rightbottom.getQuick( 1 ) - p_start.getQuick( 1 ),
            p_start.get( 0 ) - p_leftupper.getQuick( 0 ),
            p_rightbottom.getQuick( 1 ) - p_start.getQuick( 0 )
        ).mapToDouble( i -> i ).toArray();


        final DoubleMatrix1D l_vector = lineinsideoutside( p_leftupper, p_rightbottom, p_start, p_end, l_pval, l_qval );
        return l_vector != null
               ? l_vector
               : linebounderies( p_start, p_end, l_pval, l_qval );
    }

    /**
     * check line inside rectangle
     *
     * @param p_leftupper left-upper corner of the rectangle
     * @param p_rightbottom right-bottom corner of the rectangle
     * @param p_start line start point
     * @param p_end line end point
     * @param p_pval p-values
     * @param p_qval q-values
     * @return null or clipped-line
     */
    @Nullable
    private static DoubleMatrix1D lineinsideoutside( @Nonnull final DoubleMatrix1D p_leftupper, @Nonnull final DoubleMatrix1D p_rightbottom,
                                                     @Nonnull final DoubleMatrix1D p_start, @Nonnull final DoubleMatrix1D p_end,
                                                     final double[] p_pval, final double[] p_qval )
    {
        return IntStream.range( 0, 4 )
                        .boxed()
                        .filter( i -> ( p_pval[i] == 0 ) && ( p_qval[i] >= 0 ) )
                        .map( i ->
                        {
                            if ( i < 2 )
                            {
                                return new DenseDoubleMatrix1D( new double[]{
                                    p_start.getQuick( 0 ) < p_leftupper.getQuick( 0 )
                                        ? p_leftupper.getQuick( 0 )
                                        : p_start.getQuick( 0 ),

                                    p_start.getQuick( 1 ),

                                    p_end.getQuick( 0 ) > p_rightbottom.getQuick( 0 )
                                        ? p_rightbottom.getQuick( 0 )
                                        : p_end.getQuick( 0 ),

                                    p_end.getQuick( 1 )
                                } );
                            }

                            if ( i > 1 )
                            {
                                return new DenseDoubleMatrix1D( new double[]{
                                    p_start.getQuick( 0 ),

                                    p_start.getQuick( 1 ) < p_leftupper.getQuick( 1 )
                                        ? p_leftupper.getQuick( 1 )
                                        : p_start.getQuick( 1 ),

                                    p_end.getQuick( 0 ),

                                    p_end.getQuick( 1 ) > p_rightbottom.getQuick( 1 )
                                        ? p_rightbottom.getQuick( 1 )
                                        : p_end.getQuick( 1 )
                                } );
                            }

                            return null;
                        } )
                        .filter( Objects::nonNull )
                        .findFirst()
                        .orElse( null );
    }

    /**
     * clip-line to bounderiers
     *
     * @param p_start line start point
     * @param p_end line end point
     * @param p_pval p-values
     * @param p_qval q-values
     * @return clipped-line or empty vector
     */
    @Nonnull
    private static DoubleMatrix1D linebounderies( @Nonnull final DoubleMatrix1D p_start, @Nonnull final DoubleMatrix1D p_end, final double[] p_pval, final double[] p_qval )
    {
        double l_t1 = 0;
        double l_t2 = 1;

        for ( int i = 0; i < 4; i++ )
        {
            final double l_temp = p_qval[i] / p_pval[i];
            if ( p_pval[i] < 0 )
                l_t1 = l_t1 <= l_temp ? l_temp : l_t1;
            else
                l_t2 = l_t2 > l_temp ? l_temp : l_t2;
        }

        return l_t1 < l_t2
               ? new DenseDoubleMatrix1D( new double[]{
                    p_start.getQuick( 0 ) + l_t1 * p_pval[3],
                    p_start.getQuick( 1 ) + l_t1 * p_pval[1],
                    p_start.getQuick( 0 ) + l_t2 * p_pval[3],
                    p_start.getQuick( 1 ) + l_t2 * p_pval[1],
               } )
               : new DenseDoubleMatrix1D( 0 );
    }
}
