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

// http://www.flaticon.com/packs/infographics

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
                output: "",
                source: l_gulp.src( l_sourcedir + "index.htm" )
            },

            "images" : {
                output: "images",
                source: l_gulp.src( l_sourcedir + "images/*.*" )
            },

            "data" : {
                output: "data",
                source: l_gulp.src( l_sourcedir + "data/*.json" )
            },

            "fonts-bootstrap" : {
                output: "fonts",
                source: l_gulp.src( "node_modules/gentelella/vendors/bootstrap/dist/fonts/*.*" )
            }

        },


        // minify javascript
        minifyjs : {

            "js-main ": {
                output: "js/script.min.js",
                source: l_gulp.src(l_sourcedir + "js/*.js")
            },

            "js-jquery" : {
                output: "js/jquery.min.js",
                source: l_gulp.src( "node_modules/gentelella/vendors/jquery/dist/jquery.js" )
            },

            "js-bootstrap" : {
                output: "js/bootstrap.min.js",
                source: l_gulp.src( "node_modules/gentelella/vendors/bootstrap/dist/js/bootstrap.js" )
            },

            "js-gentelella" : {
                output: "js/gentelella.min.js",
                source: l_gulp.src([
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

            "css-bootstrap" : {
                output: "css/bootstrap.min.css",
                source: l_gulp.src( "node_modules/gentelella/vendors/bootstrap/dist/css/bootstrap.css" )
            },

            "css-gentelella" : {
                output: "css/gentelella.min.css",
                source: l_gulp.src([
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
                       .pipe( l_gulp.dest( l_outputdir + l_config.assets[assets].output ) );
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
