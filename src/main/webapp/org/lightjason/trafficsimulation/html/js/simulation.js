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

    /**
     * Quintus object (Simulation UI)
     * @see http://www.html5quintus.com/
     */
    var l_quintus = Quintus()
        .include("Audio, Sprites, Scenes, 2D")
        .setup("simulation-screen", {
            scaleToFit: true,
            audioSupported: [ "mp3" ]
        })
        .enableSound();

    // initialize objects
    l_quintus.stageScene("street", 0);
    l_quintus.stageScene("vehicles", 1);
    l_quintus.Sprite.extend("uservehicle", { init: function (p) { this._super(p, { sheet: "uservehicle" }); }});
    l_quintus.Sprite.extend("defaultvehicle", { init: function (p) { this._super(p, { sheet: "defaultvehicle" }); } });

    // loading quintus elements
    l_quintus.load("axelf.mp3, sprites.png, sprites.json, streettiles.png", function() {
        l_quintus.sheet("streettiles", "streettiles.png", {tilew: 32, tileh: 32});
        l_quintus.compileSheets("sprites.png", "sprites.json");
        l_quintus.stageScene("street");
    });



    /** element functions */
    var objectfunctions = {

        environment: {
            create: function( p_data )
            {
                /**
                 * generate the matrix of street tiles
                 * @param lanes the number of lanes
                 * @param length the length of the street in cells
                 * @return the matrix of the street tiles
                 */
                var streettiles = function( lanes, length )
                {
                    var l_matrix = [];
                    var l_footway = new Array( length ).fill( 1 );
                    var l_rightlane = new Array( length ).fill( 2 );
                    var l_leftlane = new Array( length ).fill( 3 );

                    l_matrix.push( l_footway );
                    for ( var i = 1; i <= lanes; i++ )
                        l_matrix.push( i % 2 === 0  ? l_rightlane : l_leftlane );
                    l_matrix.push( l_footway );

                    return l_matrix;
                };


                // a tilelayer in quintus can have the maximum size of 32768. (1024 cell * 32 pixcel)
                // therefore we define multi-tilelayers and put them together.
                var maxcellinlayer = 1000;
                var layercount = p_data.length / maxcellinlayer;
                for (var i = 0; i < layercount; i++) {
                    var layerlength = ( i === parseInt( layercount, 10 ) ) ? ( p_data.length - i * maxcellinlayer ) : maxcellinlayer;
                    var l_tilelayer = new l_quintus.TileLayer ( {
                        tileW: 32,
                        tileH: 32,
                        blockTileW: layerlength,
                        blockTileH: p_data.lanes + 2,
                        type: l_quintus.SPRITE_NONE,
                        sheet: "streettiles"
                    } );
                    l_tilelayer.p.tiles = streettiles( p_data.lanes, layerlength );
                    l_quintus.stages[0].insert( l_tilelayer );
                    if (i > 0)
                        l_quintus.stages[0].items[l_quintus.stages[0].items.length - 1].p.x = i * maxcellinlayer  * 16;
                }

                if ( jQuery( "#simulation-music" ).is(":checked") )
                    l_quintus.audio.play( "axelf.mp3", { loop: true } );
            },

            remove: function( p_data )
            {
                l_quintus.audio.stop();
                //ToDo: after shut down, all vehicles should be deleted
            }
        },


        defaultvehicle: {
            create: function (p_data) {
                // create a default vehicle
                var l_defaultvehicle = vehicles[p_data.id] = new l_quintus.defaultvehicle({x: p_data.x * 32, y: p_data.y * 32 + 16});
                // @todo opposite vehicle
                //if(opposite vehicle) l_defaultvehicle.p.angel = 180;
                l_quintus.stages[1].insert(l_defaultvehicle);
            },

            // @todo refactor
            execute: function (p_data) {
                // move the vehicles in the new positions
                vehicles[p_data.id].p.x = p_data.x * 32;
                vehicles[p_data.id].p.y = p_data.y * 32 + 16;
            }
        },


        uservehicle: {
            create: function (p_data) {
                vehicles[p_data.id] = new l_quintus.uservehicle({x: p_data.x * 32, y: p_data.y * 32 + 16});
                l_quintus.stages[1].insert(vehicles[p_data.id]);

                // the camera follows the user vehicle
                l_quintus.stages[0].add("viewport").follow(vehicles[p_data.id]);
                l_quintus.stages[1].add("viewport").follow(vehicles[p_data.id]);
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
            if ( ( objectfunctions[l_data.type] ) && ( typeof( objectfunctions[l_data.type][l_data.status] ) === "function" ) )
                objectfunctions[l_data.type][l_data.status]( l_data );
        };
});
