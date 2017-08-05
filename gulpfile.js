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


/**
 * explantion of components
 *
 * asserts - contains all components which are copied only
 * minifyjs - contains all components wich are based on JavaScript and will be minifed
 * minifycss - contains all components which ar ebased on CSS and will be minifed
 *
 */

const MINIFY = true;

const l_gulp = require( "gulp" ),
      l_clean = require( "gulp-clean" ),
      l_empty = require( "gulp-empty-pipe" ),
      l_vinyl = require( "vinyl-source-stream" ),
      l_browserify = require( "browserify" ),
      l_pump = require( "pump" ),

      l_concatjs = require( "gulp-concat"),
      l_concatcss = require( "gulp-concat-css"),

      l_minifyhtml = require("gulp-htmlmin"),
      l_minifyjs = require( "gulp-uglify/composer")( require('uglify-es'), console ),
      l_minifycss = require( "gulp-uglifycss" ),

      l_packagedir = "org/lightjason/trafficsimulation/html/",
      l_sourcedir = "src/main/webapp/" + l_packagedir,
      l_outputdir = "target/classes/" + l_packagedir,

      l_config = {

          // assets
          assets : {

              "fonts" : {
                  output: "fonts",
                  source: l_gulp.src([
                      "node_modules/bootstrap/dist/fonts/*.*",
                      "node_modules/font-awesome/fonts/*.*",
                      "node_modules/canvas-gauges/fonts/*.*",
                      "!node_modules/canvas-gauges/fonts/*.css"
                  ])
              },

              "assets" : {
                  output: "assets",
                  source: l_gulp.src( l_sourcedir + "assets/*.*" )
              },

              "css-fontawesome" : {
                  output: "css",
                  source: l_gulp.src( "node_modules/font-awesome/css/font-awesome.min.css" )
              },

              "markdown-slides" : {
                  output: "slides",
                  source: l_gulp.src( l_sourcedir + "slides/*.md" )
              },

              "markdown-documents" : {
                  output: "docs",
                  source: l_gulp.src( l_sourcedir + "docs/*.md" )
              },

              "mathjax-jax" : {
                output: "jax",
                source: l_gulp.src( "node_modules/mathjax/unpacked/jax/**/*.*" )
              },

              "mathjax-extensions" : {
                  output: "extensions",
                  source: l_gulp.src( "node_modules/mathjax/unpacked/extensions/**/*.*" )
              }

          },


          // html
          minifyhtml : {

              "html-main" : {
                  output: "",
                  source: l_gulp.src( l_sourcedir + "*.htm" )
              }

          },


          // javascript
          minifyjs : {

              "js-main ": {
                  output: "js/index.min.js",
                  source: l_gulp.src([
                      l_sourcedir + "js/global.js",
                      l_sourcedir + "js/lightjason.js",
                      l_sourcedir + "js/simulation.js"
                  ])
              },

              "js-library" : {
                  output: "js/library.min.js",
                  source: l_gulp.src([

                      "node_modules/jquery/dist/jquery.js",
                      "node_modules/bootstrap/dist/js/bootstrap.js",

                      "node_modules/jquery.full.screen/jquery.full.screen.js",
                      "node_modules/jquery-knob/js/jquery.knob.js",
                      "node_modules/pnotify/dist/pnotify.js",
                      "node_modules/switchery/dist/switchery.js",

                      "node_modules/file-saver/FileSaver.min.js",
                      "node_modules/remark/out/remark.js",
                      "node_modules/showdown/dist/showdown.js",
                      "node_modules/chart.js/dist/Chart.bundle.js",
                      "node_modules/canvas-gauges/gauge.min.js",

                      "node_modules/mathjax/unpacked/MathJax.js",
                      "node_modules/mathjax/unpacked/config/TeX-AMS-MML_HTMLorMML.js",

                      "node_modules/codemirror/lib/codemirror.js",
                      "node_modules/codemirror/addon/hint/show-hint.js",
                      "node_modules/codemirror/addon/hint/anyword-hint.js",
                      l_sourcedir + "js/codemirror_grammar.js",

                      "node_modules/phaser/build/pixi.js",
                      "node_modules/phaser/build/phaser.js"

                      /*
                      "node_modules/prismjs/prism.js",
                      "node_modules/prismjs/components/prism-clike.js",
                      "node_modules/prismjs/components/prism-java.js",
                      "node_modules/prismjs/components/prism-prolog.js",
                      l_sourcedir + "js/prism-agentspeak.js",
                      */
                  ])
              }

          },


          // browserify
          browserify : {
              /*
              "browserify-any" : {
                  dependcy: <any other task dependency>
                  bundle: "<bundle name>",
                  output: "<output file>",
                  source: "<application source>"
              }
              */
          },


          // css
          minifycss : {

              "css-library" : {
                  output: "css/library.min.css",
                  source: l_gulp.src([
                      "node_modules/bootstrap/dist/css/bootstrap.css",
                      "node_modules/gentelella/build/css/custom.css",

                      "node_modules/pnotify/dist/*.css",
                      "node_modules/switchery/dist/switchery.css",
                      "node_modules/canvas-gauges/assets/styles/*.css",
                      "node_modules/canvas-gauges/fonts/fonts.css",
                      //"node_modules/prismjs/themes/prism-coy.css",

                      "node_modules/codemirror/lib/codemirror.css",
                      "node_modules/codemirror/addon/hint/show-hint.css",
                      "node_modules/codemirror/theme/neat.css"
                  ])
              },

              "css-index" : {
                  output: "css/index.min.css",
                  source: l_gulp.src( l_sourcedir + "css/index.css" )
              },

              "css-slide" : {
                  output: "css/slide.min.css",
                  source: l_gulp.src( l_sourcedir + "css/slide.css" )
              }

          }

      };


// --- task definition ------------------------------------------------------------------------------------

// minify js tasks
for( const js in l_config.minifyjs )
{
    l_gulp.task( js,
        l_config.minifyjs[js].dependency ? [].concat( l_config.minifyjs[js].dependency ) : [],
        function (i) {
            l_pump([
                l_config.minifyjs[js].source,
                l_concatjs( l_config.minifyjs[js].output ),
                MINIFY ? l_minifyjs() : l_empty(),
                l_gulp.dest( l_outputdir )
                ], i
            );
    });
}


// browserify
for( const br in l_config.browserify )
{
    l_gulp.task( br,
        l_config.browserify[br].dependency ? [].concat( l_config.browserify[br].dependency ) : [],
        function () {
            return l_browserify( l_config.browserify[br].source, { standalone: l_config.browserify[br].bundle })
                   .bundle()
                   .pipe( l_vinyl( l_config.browserify[br].output ) )
                   .pipe( l_gulp.dest( l_outputdir ) );
    });
}


// minify css tasks
for( const css in l_config.minifycss )
{
    l_gulp.task( css,
        l_config.minifycss[css].dependency ? [].concat( l_config.minifycss[css].dependency ) : [],
        function () {
            return l_config.minifycss[css].source
                           .pipe( l_concatcss( l_config.minifycss[css].output ) )
                           .pipe( MINIFY ? l_minifycss() : l_empty() )
                           .pipe( l_gulp.dest( l_outputdir ) );
    });
}


// minify html tasks
for( const html in l_config.minifyhtml )
{
    l_gulp.task( html,
        l_config.minifyhtml[html].dependency ? [].concat( l_config.minifyhtml[html].dependency ) : [],
        function () {
            return l_config.minifyhtml[html].source
                .pipe( MINIFY ? l_minifyhtml({
                    caseSensitive: true,
                    collapseBooleanAttributes: true,
                    collapseWhitespace: true,
                    decodeEntities: true,
                    html5: true,
                    minifyCSS: true,
                    minifyJS: true,
                    removeComments: true,
                    removeRedundantAttributes: true,
                    removeScriptTypeAttributes: true,
                    removeStyleLinkTypeAttributes: true,
                    trimCustomFragments: true,
                    useShortDoctype: true
                }) : l_empty() )
                .pipe( l_gulp.dest( l_outputdir ) );
    });
}


// assets tasks
for( const assets in l_config.assets )
{
    l_gulp.task( assets,
        l_config.assets[assets].dependency ? [].concat( l_config.assets[assets].dependency ) : [],
        function () {
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
    Object.keys( l_config.minifyhtml ),
    Object.keys( l_config.assets ),
    Object.keys( l_config.browserify )
) );
