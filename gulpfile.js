"use strict";

// https://www.smashingmagazine.com/2014/06/building-with-gulp/
// https://github.com/gulpjs/gulp/blob/master/docs/recipes/running-task-steps-per-folder.md

var l_gulp = require( "gulp" ),
    l_concat = require("gulp-dir-concat"),
    l_minify = require("gulp-uglify"),
    l_rename= require('gulp-rename'),
    l_clean = require('gulp-clean'),

    l_packagedir = "org/lightjason/trafficsimulation/html/",
    l_sourcedir = "src/main/webappx/" + l_packagedir,
    l_outputdir = "target/classes/" + l_packagedir,

    l_config = {

        mainjs: {
            source: "**/*.js",
            output: "script.min.js"
        }

//        maincss: {
//            source: "**/*.css",
//            output: "layout.min.css"
//        }

    };


    /*
    paths = {

        build: outputdir,

        assets: [
            sourcedir + "index.htm",
            sourcedir + "*.js",

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
        ],

    },

    l_tasks = {
        clean: function () { return taskMethods.clean(paths); },
        assets: function () { return taskMethods.assets(paths); },
        css: function () { return taskMethods.css(paths); },
    };
    */

for( var k in l_config )
    l_gulp.task( k, function() {
        return l_gulp.src( l_sourcedir + l_config[k].source )
            .pipe( l_concat() )
            .pipe( l_minify() )
            .pipe( l_rename( l_config[k].output ) )
            .pipe( l_gulp.dest( l_outputdir ) );
    });

l_gulp.task( "clean", function() {
    return l_gulp.src( l_outputdir )
        .pipe( l_clean({force: true}) );
});




l_gulp.task( "default", Object.keys(l_config) );


