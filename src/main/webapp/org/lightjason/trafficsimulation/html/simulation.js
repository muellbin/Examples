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

  var Q = window.Q = Quintus()
          .include("Sprites, Scenes, Input, 2D, Anim, Touch, UI")
          .setup("screen")
          .controls()
          .touch();


  Q.Sprite.extend("UserCar",{
    init: function(p) {
      this._super(p, {
        sheet: "usercar",
        x: 410,
        y: 90
      });

      this.on("hit.sprite",function(collision) {
        if(collision.obj.isA("Car")) {
          //the Usercar hit another car!

        }
      });
    }
  });

  Q.Sprite.extend("Car",{
    init: function(p) {
      this._super(p, {
        sheet: "car",
        x: 510,
        y: 20
      });

      this.on("hit.sprite",function(collision) {
        if(collision.obj.isA("Car")) {
          //A car hit another car!

        }
      });
    }
  });

  Q.scene("street",function(stage) {

    //stage.insert(new Q.Repeater({ asset: "background-wall.png", speedX: 0.5, speedY: 0.5 }));

    stage.collisionLayer(new Q.TileLayer({
                               dataAsset: 'level.json',
                               sheet:     'tiles' }));


    var usercar = stage.insert(new Q.UserCar());

    stage.add("viewport").follow(usercar);

    stage.insert(new Q.Car({ x: 100, y: 0 }));
    stage.insert(new Q.Car({ x: 800, y: 0 }));

  });

  Q.load("sprites.png, sprites.json, level.json, tiles.png", function() {
    Q.sheet("tiles","tiles.png", { tilew: 32, tileh: 32 });

    // Or from a .json asset that defines sprite locations
    Q.compileSheets("sprites.png","sprites.json");

    // Finally, call stageScene to run the game
    Q.stageScene("street");
  });

});