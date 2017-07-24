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
    var vehicles = {};
    document.getElementById('simulation-screen').width = jQuery("#simulation-dashboard").width();
    document.getElementById('simulation-screen').height = jQuery("#simulation-dashboard").height();
    var l_quintus = Quintus()
        .include("Sprites, Scenes, Input, 2D, Anim, Touch, UI, Audio")
        .setup("simulation-screen", {
            scaleToFit: true,
            audioSupported: [ "mp3" ]
        })
        .enableSound()
        .controls()
        .touch();

    l_quintus.Sprite.extend("UserVehicle", {
        init: function (p) {
            this._super(p, {
                sheet: "uservehicle"
            });
            this.on("hit.sprite", function (collision) {
                if (collision.obj.isA("DefaultVehicle")) {
                    //the user vehicle hit a default vehicle!
                }
            });
        }
    });

    l_quintus.Sprite.extend("DefaultVehicle", {
        init: function (p) {
            this._super(p, {
                sheet: "defaultvehicle"
            });
            this.on("hit.sprite", function (collision) {
                if (collision.obj.isA("DefaultVehicle")) {
                    //A default vehicle hit another default vehicle!
                }
            });
        }
    });

    l_quintus.stageScene("street", 0);
    l_quintus.stageScene("vehicles", 1);

    l_quintus.load("axelf.mp3, sprites.png, sprites.json, streettiles.png", function () {
        l_quintus.sheet("streettiles", "streettiles.png", {tilew: 32, tileh: 32});
        l_quintus.compileSheets("sprites.png", "sprites.json");
        l_quintus.stageScene("street");
    });

    var streettiles = function( lanes, length )
    {
        var l_matrix = [];
        var l_footway = Array.apply( null, Array( length ) ).map( function() {return 1} );
        var l_rightlane = Array.apply( null, Array( length ) ).map( function() {return 2} );
        var l_leftlane = Array.apply( null, Array( length ) ).map( function() {return 3} );
        l_matrix.push( l_footway );
        for ( var i = 1; i <= lanes; i++ )
        {
            if ( i % 2 == 0 )
                l_matrix.push( l_rightlane );
            else
                l_matrix.push( l_leftlane );
        }
        l_matrix.push( l_footway );
        return l_matrix;
    }

    var objects =
    {
        vehicle:
        {
            create: function( p_data )
            {
                if( p_data.userdefinied )
                {
                    var l_uservehicle = vehicles[p_data.id] = new l_quintus.UserVehicle( {x: p_data.x * 32, y: p_data.y * 32 + 16} );
                    l_quintus.stages[1].insert( l_uservehicle );
                    l_quintus.stages[0].add("viewport").follow( l_uservehicle );
                    l_quintus.stages[1].add("viewport").follow( l_uservehicle );
                }
                else
                {
                    var l_defaultvehicle = vehicles[p_data.id] = new l_quintus.DefaultVehicle( {x: p_data.x * 32, y: p_data.y * 32 + 16} );
                    //TODO: opposite vehicle
                    //if(opposite vehicle) l_defaultvehicle.p.angel = 180;
                    l_quintus.stages[1].insert( l_defaultvehicle );
                }
            },
            execute: function( p_data )
            {
                //console.log( p_data );

                vehicles[p_data.id].p.x = p_data.x * 32;
                vehicles[p_data.id].p.y = p_data.y * 32 + 16;
            },
            remove: function( p_data )
            {
            }
        },
        environment:
        {
            create: function( p_data )
            {
                var lanes = p_data.lanes;
                var length = p_data.length;
                var maxcellinlayer = 1000;
                var layercount = length / maxcellinlayer;
                for (var i = 0; i < layercount; i++) {
                    var layerlength = ( i == parseInt( layercount, 10 ) ) ? ( length - i * maxcellinlayer ) : maxcellinlayer;
                    var l_tilelayer = new l_quintus.TileLayer ( {
                        tileW: 32,
                        tileH: 32,
                        blockTileW: layerlength,
                        blockTileH: lanes + 2,
                        type: l_quintus.SPRITE_NONE,
                        sheet: "streettiles"
                    } );
                    l_tilelayer.p.tiles = streettiles( lanes, layerlength );
                    l_quintus.stages[0].insert( l_tilelayer );
                	if(i > 0)
                	{
                        l_quintus.stages[0].items[l_quintus.stages[0].items.length - 1].p.x = i * maxcellinlayer  * 16;
                	}
                }

                l_quintus.audio.play( "axelf.mp3", { loop: true } );
            },
            execute: function( p_data )
            {
            },
            remove: function( p_data )
            {
                l_quintus.audio.stop();
            }
        }
    }

    var ws = LightJason.websocket( "/animation" );

    ws.onopen = function()
    {
        console.log( "Websocket opened!" );
        ws.send( JSON.stringify( { foo : "Hello Server!" } ) );
    };

    ws.onmessage = function ( evt )
    {
        //console.log("Message from server: " + evt.data);
        var l_data = JSON.parse( evt.data );
        objects[l_data.type][l_data.status]( l_data );
    };

    ws.onclose = function()
    {
        console.log( "Websocket closed!" );
    };

    ws.onerror = function( err )
    {
        console.log( "Websocket Error: " + err );
    };
});
