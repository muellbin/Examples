"use strict";

// https://www.smashingmagazine.com/2014/06/building-with-gulp/
// https://github.com/gulpjs/gulp/blob/master/docs/recipes/running-task-steps-per-folder.md

var l_gulp = require( "gulp" ),
    l_concat = require("gulp-dir-concat"),
    l_minifyjs = require("gulp-uglify"),
    l_minifycss = require('gulp-uglifycss'),
    l_rename= require('gulp-rename'),
    l_clean = require('gulp-clean'),

    l_packagedir = "org/lightjason/trafficsimulation/html/",
    l_sourcedir = "src/main/webapp/" + l_packagedir,
    l_outputdir = "target/classes/" + l_packagedir,

    l_config = {

        // assets are copied only
        assets : {

            "html-index" : {
                output: l_outputdir,
                source: l_gulp.src( l_sourcedir + "index.htm" )
            },

            "images" : {
                output: l_outputdir + "images",
                source: l_gulp.src( l_sourcedir + "images/*.*" )
            },

            "data" : {
                output: l_outputdir + "data",
                source: l_gulp.src( l_sourcedir + "data/*.json" )
            }

        },


        // minify javascript
        minifyjs : {

            "js-main ": {
                output: "script.min.js",
                source: l_gulp.src(l_sourcedir + "js/*.js")
            },

            "js-jquery" : {
                output: "jquery.min.js",
                source: l_gulp.src( "node_modules/gentelella/vendors/jquery/dist/jquery.js" )
            },

            "js-gentelella" : {
                output: "gentelella.min.js",
                source: l_gulp.src([
                    "node_modules/gentelella/vendors/bootstrap/dist/js/bootstrap.js",
                    //"node_modules/gentelella/vendors/pnotify/dist/*.js",
                    //"node_modules/gentelella/vendors/iCheck/icheck.js",
                    "node_modules/gentelella/build/js/custom.js"
                ])
            }

        },


        // minify css
        minifycss : {

//            maincss: {
//                output: "layout.min.css",
//                source: l_gulp.src( l_sourcedir + "css/*.css" )
//            },

            "css-gentelella" : {
                output : "gentelella.min.css",
                source: l_gulp.src([
                    "node_modules/gentelella/vendors/bootstrap/dist/css/bootstrap.css",
                    "node_modules/gentelella/vendors/pnotify/dist/*.css",
                    "node_modules/gentelella/build/css/custom.css"
                ])
            }

        }

    };


/*
    css: [
        "node_modules/gentelella/vendors/bootstrap-progressbar/css/bootstrap-progressbar-3.3.4.min.css",
        "node_modules/gentelella/vendors/nprogress/nprogress.css",
        "node_modules/gentelella/vendors/iCheck/skins/flat/green.css",
        "node_modules/gentelella/vendors/pnotify/dist/pnotify.css",
        "node_modules/gentelella/vendors/pnotify/dist/pnotify.buttons.css",
        "node_modules/gentelella/vendors/pnotify/dist/pnotify.nonblock.css"
    ]
*/

// --- task definition ------------------------------------------------------------------------------------

// minify js tasks
for( const js in l_config.minifyjs )
{
    l_gulp.task( js, function () {
        return l_config.minifyjs[js].source
                    .pipe( l_concat() )
                    .pipe( l_minifyjs() )
                    .pipe( l_rename( l_config.minifyjs[js].output ) )
                    .pipe( l_gulp.dest( l_outputdir ) );
    });
}


// minify css tasks
for( const css in l_config.minifycss )
{
    l_gulp.task( css, function () {
        return l_config.minifycss[css].source
                       .pipe( l_concat() )
                       .pipe( l_minifycss() )
                       .pipe( l_rename( l_config.minifycss[css].output ) )
                       .pipe( l_gulp.dest( l_outputdir ) );
    });
}


// assets tasks
for( const assets in l_config.assets )
{
    l_gulp.task( assets, function () {
        return l_config.assets[assets].source
                       .pipe( l_gulp.dest( l_config.assets[assets].output ) );
    });
}


// clean task
l_gulp.task( "clean", function() {
    return l_gulp.src( l_outputdir )
                 .pipe( l_clean({force: true}) );
});

// --------------------------------------------------------------------------------------------------------

// build
l_gulp.task( "default", [].concat(
    Object.keys( l_config.minifyjs ),
    Object.keys( l_config.minifycss ),
    Object.keys( l_config.assets )
) );
