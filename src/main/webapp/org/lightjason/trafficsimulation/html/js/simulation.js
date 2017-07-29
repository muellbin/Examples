/*
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
 */
"use strict";


jQuery(function() {

    /** array of vehicles */
    var vehicles = {};

    /** phaser engine */
    var lo_dom = jQuery("#simulation-screen");
    var game = new Phaser.Game(
        lo_dom.width(),
        lo_dom.height(),
        Phaser.AUTO, 'simulation-screen',
        {
            preload: function(i) {
                i.load.image('streettiles', 'assets/streettiles.png');
                i.load.image('uservehicle', 'assets/uservehicle.png');
                i.load.image('defaultvehicle', 'assets/defaultvehicle.png');
            },
            background: "#fff"
        }
    );


    /** element functions */
    var objectfunctions = {

        environment: {
            create: function( p_data )
            {
                var height = p_data.lanes + 2;
                var width = p_data.length;

                var streettiles = function( width, height )
                {
                    var l_matrix = [];
                    var i = 1;
                    for ( var j = 1; j <= width; j++ )
                        l_matrix.push( 2 );
                    while( i <= height - 2 )
                    {
                        for ( var j = 1; j <= width; j++ )
                            l_matrix.push( i % 2 === 0  ? 3 : 4 );
                        i++;
                    }
                    for ( var j = 1; j <= width; j++ )
                            l_matrix.push( 2 );
                    return l_matrix;
                };

                var tiles =
                {
                    "height":height,
                    "layers":[
                        {
                         "data": streettiles(width, height),
                         "height":height,
                         "name":"Street",
                         "opacity":1,
                         "type":"tilelayer",
                         "visible":true,
                         "width":width,
                         "x":0,
                         "y":0
                        }],
                    "orientation":"orthogonal",
                    "tileheight":32,
                    "tilesets":[
                        {
                         "firstgid":1,
                         "image":"images/streettiles.png",
                         "imageheight":32,
                         "imagewidth":128,
                         "margin":0,
                         "name":"StreetTiles",
                         "spacing":0,
                         "tileheight":32,
                         "tilewidth":32
                        }],
                    "tilewidth":32,
                    "version":1,
                    "width":width
                }
                game.load.tilemap('street', null, tiles, Phaser.Tilemap.TILED_JSON);
                game.scale.setGameSize( jQuery("#simulation-dashboard").width(), height * 32 )
                var map = game.add.tilemap('street');
                map.addTilesetImage('StreetTiles', 'streettiles');
                var layer = map.createLayer('Street');
                layer.resizeWorld();
                layer.wrap = true;
            },

            remove: function( p_data )
            {
                //ToDo: after shut down, all vehicles should be deleted
            }
        },

        defaultvehicle: {
            create: function (p_data) {
                // create a default vehicle
                vehicles[p_data.id] = game.add.sprite(p_data.x * 32, p_data.y * 32 + 9, 'defaultvehicle');
            },

            execute: function (p_data) {
                // move the vehicles in the new positions
                // @todo replace 250 with simulation speed
                game.add.tween( vehicles[p_data.id] ).to( {x: p_data.x * 32, y: p_data.y * 32 + 9}, 250 ).start();
            }
        },

        uservehicle: {
            create: function (p_data) {
                //create user vehicle
                vehicles[p_data.id] = game.add.sprite(p_data.x * 32, p_data.y * 32 + 9, 'uservehicle');
                // camera follows the user vehicle
                game.camera.follow(vehicles[p_data.id]);
            }
        }
    };

    // set function references
    objectfunctions.uservehicle.execute = objectfunctions.defaultvehicle.execute;

    /** define the animation websocket */
    LightJason.websocket( "/animation" )
        .onmessage = function ( e )
        {
            var l_data = JSON.parse( e.data );
            //console.log(l_data);
            if ( ( objectfunctions[l_data.type] ) && ( typeof( objectfunctions[l_data.type][l_data.status] ) === "function" ) )
                objectfunctions[l_data.type][l_data.status]( l_data );
        };
});
