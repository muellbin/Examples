window.addEventListener("load",function() {

var Q = window.Q = Quintus()
        .include("Sprites, Scenes, Input, 2D, Anim, Touch, UI")
        .setup({ maximize: true })
        .controls()
        .touch()


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