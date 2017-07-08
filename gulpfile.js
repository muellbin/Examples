"use strict";

// https://www.smashingmagazine.com/2014/06/building-with-gulp/

var l_gulp = require( "gulp" ),
    taskMethods = require( "gulpfile-ninecms"),
    l_everytimetask = ["clean"],
    packagedir = "org/lightjason/trafficsimulation/html/",
    sourcedir = "src/main/webapp/" + packagedir,
    outputdir = "target/classes/" + packagedir,

    paths = {

        build: outputdir,

        assets: [
            sourcedir + "index.htm",
            sourcedir + "*.js",

            "node_modules/quintus/lib/*.js",

            "node_modules/gentelella/build/js/custom.min.js",

            "node_modules/gentelella/vendors/bootstrap/dist/js/bootstrap.min.js",
            "node_modules/gentelella/vendors/jquery/dist/jquery.min.js"

        ],

        css: [
            "node_modules/gentelella/build/css/custom.min.css",

            "node_modules/gentelella/vendors/bootstrap/dist/css/bootstrap.css",
            "node_modules/gentelella/vendors/bootstrap-progressbar/css/bootstrap-progressbar-3.3.4.min.css",
            "node_modules/gentelella/vendors/nprogress/nprogress.css",
            "node_modules/gentelella/vendors/iCheck/skins/flat/green.css",
            "node_modules/gentelella/vendors/pnotify/dist/pnotify.css",
            "node_modules/gentelella/vendors/pnotify/dist/pnotify.buttons.css",
            "node_modules/gentelella/vendors/pnotify/dist/pnotify.nonblock.css"
        ]

    },

    l_tasks = {
        clean: function () { return taskMethods.clean(paths); },
        assets: function () { return taskMethods.assets(paths); },
        css: function () { return taskMethods.css(paths); },
    };



l_gulp.task("clean", l_tasks.clean);

l_gulp.task("assets", l_everytimetask, l_tasks.assets);
l_gulp.task("css", l_everytimetask, l_tasks.css);



l_gulp.task("build", [ "assets", "css" ]);

l_gulp.task("default", ["build"]);
