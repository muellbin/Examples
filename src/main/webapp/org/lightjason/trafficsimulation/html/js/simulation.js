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

    var ws = new WebSocket( "ws://localhost:12345/animation" );

    ws.onopen = function()
    {
        console.log( "Websocket opened!" );
        ws.send( JSON.stringify( { foo : "Hello Server!" } ) );
    };

    ws.onmessage = function ( evt )
    {
        console.log("Message from server: " + evt.data);
        var l_data = JSON.parse( evt.data );
        switch( l_data.operation )
        {
            case ( "initializegrid" ):
                initialize( l_data.width, l_data.height, l_data.cellsize );
                break;
        }
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

function initialize( width, height, cellsize )
{
    var l_quintus = window.m_quintus = Quintus()
        .include("Sprites, Scenes, Input, 2D, Anim, Touch, UI")
        .setup("screen", {
            scaleToFit: true
        })
        .controls()
        .touch();

    l_quintus.Sprite.extend("Player", {
        init: function (p) {
            this._super(p, {
                sheet: "player"
            });

            this.on("hit.sprite", function (collision) {
                if (collision.obj.isA("Car")) {
                    //the player hit a car!

                }
            });
        }
    });

    l_quintus.Sprite.extend("Car", {
        init: function (p) {
            this._super(p, {
                sheet: "car"
            });

            this.on("hit.sprite", function (collision) {
                if (collision.obj.isA("Car")) {
                    //A car hit another car!

                }
            });
        }
    });

    l_quintus.scene("street", function (stage) {

        stage.insert(new l_quintus.TileLayer({
            dataAsset: 'street.json',
            sheet: 'streettiles'
        }));

        var player = stage.insert(new l_quintus.Player({x: 20, y: 114}));

        //stage.add("viewport").follow(player);

        stage.insert(new l_quintus.Car({x: 300, y: 114}));
        stage.insert(new l_quintus.Car({x: 200, y: 144}));
        stage.insert(new l_quintus.Car({x: 500, y: 144}));

        stage.insert(new l_quintus.Car({x: 700, y: 80, angle: 180}));
        stage.insert(new l_quintus.Car({x: 800, y: 50, angle: 180}));

    });

    l_quintus.load("sprites.png, sprites.json, street.json, streettiles.png", function () {
        l_quintus.sheet("streettiles", "streettiles.png", {tilew: cellsize, tileh: cellsize});

        l_quintus.compileSheets("sprites.png", "sprites.json");

        l_quintus.stageScene("street");
    });
}