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

var Q = window.Q = Quintus()
        .include("Sprites, Scenes, Input, 2D, Anim, Touch, UI")
        .setup({ maximize: true })
        .controls()
        .touch()

Q.Sprite.extend("Player",{
  init: function(p) {
    this._super(p, {
      sheet: "player"
    });

    this.on("hit.sprite",function(collision) {
      if(collision.obj.isA("Car")) {
        //the player hit a car!

      }
    });
  }
});

Q.Sprite.extend("Car",{
  init: function(p) {
    this._super(p, {
      sheet: "car"
    });

    this.on("hit.sprite",function(collision) {
      if(collision.obj.isA("Car")) {
        //A car hit another car!

      }
    });
  }
});

Q.scene("street",function(stage) {

  stage.insert(new Q.TileLayer({
                             dataAsset: 'street.json',
                             sheet:     'streettiles' }));

  var player = stage.insert(new Q.Player({ x: 20, y: 114 }));

  stage.add("viewport").follow(player);

  stage.insert(new Q.Car({ x: 300, y: 114 }));
  stage.insert(new Q.Car({ x: 200, y: 144 }));
  stage.insert(new Q.Car({ x: 500, y: 144 }));

  stage.insert(new Q.Car({ x: 700, y: 80, angle: 180 }));
  stage.insert(new Q.Car({ x: 800, y: 50, angle: 180 }));

});

Q.load("sprites.png, sprites.json, street.json, streettiles.png", function() {
  Q.sheet("streettiles","streettiles.png", { tilew: 32, tileh: 32 });

  Q.compileSheets("sprites.png","sprites.json");

  Q.stageScene("street");
});