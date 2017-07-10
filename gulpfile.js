"use strict";

// https://www.smashingmagazine.com/2014/06/building-with-gulp/
// https://github.com/gulpjs/gulp/blob/master/docs/recipes/running-task-steps-per-folder.md

var l_gulp = require( "gulp" ),
    l_concat = require("gulp-dir-concat"),
    l_minify = require("gulp-uglify"),
    l_rename= require('gulp-rename'),
    l_clean = require('gulp-clean'),

    l_packagedir = "org/lightjason/trafficsimulation/html/",
    l_sourcedir = "src/main/webapp/" + l_packagedir,
    l_outputdir = "target/classes/" + l_packagedir,

    l_config = {

        assets : {

            "html-index" : {
                source: l_gulp.src( l_sourcedir + "index.htm" ),
                output: l_outputdir
            }

        },


        minify : {

            "js-main" : {
                source: l_gulp.src( l_sourcedir + "js/*.js" ),
                output: "script.min.js"
            },

            "js-gentelella" : {
                source: l_gulp.src([
                    "node_modules/gentelella/vendors/jquery/dist/jquery.js",
                    "node_modules/gentelella/vendors/bootstrap/dist/js/bootstrap.js",
                    "node_modules/gentelella/build/js/custom.min.js"
                ]),
                output: "gentelella.min.js"
            }

//            maincss: {
//                source: l_gulp.src( l_sourcedir + "css/*.css" ),
//                output: "layout.min.css"
//            }

        }

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


// minify tasks
for( var k in l_config.minify )
{
    l_gulp.task( k, function () {
        return l_config.minify[k].source
                    .pipe( l_concat() )
                    .pipe( l_minify() )
                    .pipe( l_rename( l_config.minify[k].output ) )
                    .pipe( l_gulp.dest( l_outputdir ) );
    });
}


// assets tasks
for( var k in l_config.assets )
{
    l_gulp.task( k, function () {
        return l_config.assets[k].source
                    .pipe( l_gulp.dest( l_config.assets[k].output ) );
    });
}


// clean task
l_gulp.task( "clean", function() {
    return l_gulp.src( l_outputdir )
        .pipe( l_clean({force: true}) );
});


// build
l_gulp.task( "default", [].concat( Object.keys(l_config.minify) ) );
