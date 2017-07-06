"use strict";

// https://www.smashingmagazine.com/2014/06/building-with-gulp/

var l_gulp = require( "gulp" ),
    taskMethods = require( "gulpfile-ninecms"),
    l_everytimetask = ["clean"],
    packagedir = "org/lightjason/trafficsimulation/html/",
    sourcedir = "src/main/webapp/" + packagedir,
    outputdir = "target/classes/" + packagedir,

    paths = {

        assets: [
            sourcedir + "index.htm",
            sourcedir + "*.js",

            "node_modules/bootstrap/dist/*fonts/*",
            "node_modules/font-awesome/*fonts/*",
            "node_modules/gentelella/vendors/bootstrap/dist/*js/bootstrap.min.js",
            "node_modules/ng-gentelella/*gentelella/gentelella.jquery.js"
        ],

        sass: [
            "node_modules/ng-gentelella/gentelella/*.s?ss"
        ],

        less: [],

        css: [
            "node_modules/bootstrap/dist/css/bootstrap*(|-theme).css",

            "node_modules/gentelella/vendors/bootstrap/dist/css/bootstrap.css",
            "node_modules/font-awesome/css/font-awesome.css",
            "node_modules/gentelella/vendors/nprogress/nprogress.css",
            "node_modules/gentelella/vendors/iCheck/skins/flat/green.css",
            "node_modules/gentelella/vendors/bootstrap-progressbar/css/bootstrap-progressbar-3.3.4.min.css",
            "node_modules/gentelella/vendors/pnotify/dist/pnotify.css",
            "node_modules/gentelella/vendors/pnotify/dist/pnotify.buttons.css",
            "node_modules/gentelella/vendors/pnotify/dist/pnotify.nonblock.css",
            "node_modules/gentelella/vendors/select2/dist/css/select2.min.css"
        ],

        js: "",

        partials: [
            "node_modules/*ng-gentelella/gentelella/**/*.html"
        ],

        js_lint: [],

        js_cover: [],

        js_watch: [],

        mocha: [],

        fonts: "",

        build: outputdir,

        images: "",

    },

    l_tasks = {
        clean: function () { return taskMethods.clean(paths); },
        assets: function () { return taskMethods.assets(paths); },
        css: function () { return taskMethods.css(paths); },
        less: function () { return taskMethods.less(paths); },
        sass: function () { return taskMethods.sass(paths); },
        browserify: function () { return taskMethods.browserify(paths); },
        lintJs: function () { return taskMethods.lintJs(paths); },
        concatJs: function () { return taskMethods.concatJs(paths); },
        preloadNgHtml: function () { return taskMethods.preloadNgHtml(paths); },
        images: function () { return taskMethods.images(paths, []); },
        clean_image_opts: function () { return taskMethods.clean_image_opts() },
        fonts: function () { return taskMethods.fonts(paths); },
        nsp: function () { return taskMethods.nsp(); },
    };



l_gulp.task("clean", l_tasks.clean);
l_gulp.task("assets", l_everytimetask, l_tasks.assets);
l_gulp.task("css", l_everytimetask.concat(["less", "sass"]), l_tasks.css);
l_gulp.task("less", l_everytimetask, l_tasks.less);
l_gulp.task("sass", l_everytimetask, l_tasks.sass);
l_gulp.task("browserify", l_everytimetask, l_tasks.browserify);
l_gulp.task("lintjs", l_tasks.lintjs);
l_gulp.task("concatJs", l_everytimetask, l_tasks.concatJs);
l_gulp.task("images", l_everytimetask, l_tasks.images);
l_gulp.task("clean_image_opts", l_everytimetask, l_tasks.clean_image_opts);
l_gulp.task("fonts", l_everytimetask, l_tasks.fonts);
l_gulp.task("nsp", l_tasks.nsp);
l_gulp.task("preTest", l_tasks.preTest);
l_gulp.task("mocha", l_tasks.mocha);
l_gulp.task("karma", l_tasks.karma);
l_gulp.task("istanbul", ["preTest"], l_tasks.mocha);
l_gulp.task("preloadNgHtml", l_everytimetask, l_tasks.preloadNgHtml);


l_gulp.task("build", [
    "assets",
    "less",
    "sass",
    "css",
    "browserify",
    "concatJs",
    "images",
    "fonts"
]);

l_gulp.task("default", ["build"]);
