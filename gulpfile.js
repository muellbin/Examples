"use strict";

var l_gulp = require( "gulp" ),
    taskMethods = require( "gulpfile-ninecms"),
    l_everytimetask = ["clean"],
    packagedir = "org/lightjason/trafficsimulation/html/",
    sourcedir = "src/main/webapp/" + packagedir,
    outputdir = "target/classes/" + packagedir,

    paths = {

        assets: [
            sourcedir + "*.js",
            sourcedir + "index.htm"
        ],

        sass: [],

        less: [],

        css: [
            "node_modules/bootstrap/dist/css/bootstrap*(|-theme).css"
        ],

        js: "",

        js_watch: [
            "node_modules/jquery/dist/jquery.js",
            "node_modules/bootstrap/dist/js/bootstrap.js"
        ],

        partials: [],

        js_lint: [],

        js_cover: [],

        mocha: [],

        fonts: "",

        build: outputdir,

        images: "",

        admin: {
            assets: [
                "node_modules/bootstrap/dist/*fonts/*",
                "node_modules/font-awesome/*fonts/*",
                "node_modules/gentelella/vendors/bootstrap/dist/*js/bootstrap.min.js",
                "node_modules/ng-gentelella/*gentelella/gentelella.jquery.js"
            ],
            sass: [
                "node_modules/ng-gentelella/gentelella/*.s?ss"
            ],
            css: [
                "node_modules/gentelella/vendors/bootstrap/dist/css/bootstrap.css",
                "node_modules/font-awesome/css/font-awesome.css",
                "node_modules/gentelella/vendors/nprogress/nprogress.css",
                "node_modules/gentelella/vendors/iCheck/skins/flat/green.css",
                "node_modules/gentelella/vendors/bootstrap-progressbar/css/bootstrap-progressbar-3.3.4.min.css",
                "node_modules/gentelella/vendors/pnotify/dist/pnotify.css",
                "node_modules/gentelella/vendors/pnotify/dist/pnotify.buttons.css",
                "node_modules/gentelella/vendors/pnotify/dist/pnotify.nonblock.css",
                "node_modules/gentelella/vendors/select2/dist/css/select2.min.css",
            ],
            js_watch: [
                "node_modules/jquery/dist/jquery.js",
                "node_modules/angular/angular.js",
                "node_modules/angular-route/angular-route.js",
                "node_modules/angular-resource/angular-resource.js",
                "node_modules/angular-animate/angular-animate.js",
                "node_modules/angular-sanitize/angular-sanitize.js",
                "node_modules/ng-file-upload/dist/ng-file-upload.js",
                "node_modules/gentelella/vendors/fastclick/lib/fastclick.js",
                "node_modules/gentelella/vendors/nprogress/nprogress.js",
                "node_modules/gentelella/vendors/Chart.js/dist/Chart.min.js",
                "node_modules/gentelella/vendors/gauge.js/dist/gauge.min.js",
                "node_modules/gentelella/vendors/bootstrap-progressbar/bootstrap-progressbar.min.js",
                "node_modules/gentelella/vendors/iCheck/icheck.min.js",
                "node_modules/gentelella/vendors/skycons/skycons.js",
                "node_modules/gentelella/vendors/Flot/jquery.flot.js",
                "node_modules/gentelella/vendors/Flot/jquery.flot.pie.js",
                "node_modules/gentelella/vendors/Flot/jquery.flot.time.js",
                "node_modules/gentelella/vendors/Flot/jquery.flot.stack.js",
                "node_modules/gentelella/vendors/Flot/jquery.flot.resize.js",
                "node_modules/gentelella/production/js/flot/jquery.flot.orderBars.js",
                "node_modules/gentelella/production/js/flot/date.js",
                "node_modules/gentelella/production/js/flot/jquery.flot.spline.js",
                "node_modules/gentelella/production/js/flot/curvedLines.js",
                "node_modules/gentelella/production/js/moment/moment.min.js",
                "node_modules/gentelella/production/js/datepicker/daterangepicker.js",
                "node_modules/gentelella/vendors/pnotify/dist/pnotify.js",
                "node_modules/gentelella/vendors/pnotify/dist/pnotify.buttons.js",
                "node_modules/gentelella/vendors/pnotify/dist/pnotify.nonblock.js",
                "node_modules/gentelella/vendors/select2/dist/js/select2.full.js",

                "node_modules/ng-gentelella/gentelella/*.module.js",
                "node_modules/ng-gentelella/gentelella/*.config.js",
                "node_modules/ng-gentelella/gentelella/**/*.module.js",
                "node_modules/ng-gentelella/gentelella/**/*.component.js",
                "node_modules/ng-gentelella/gentelella/**/*.service.js"
            ],
            partials: [
                "node_modules/*ng-gentelella/gentelella/**/*.html"
            ],
            build: outputdir + "js/admin/"
        }

    },

    tasks = {
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

        adminAssets: function () { return taskMethods.assets(paths.admin); },
        adminCss: function () { return taskMethods.css(paths.admin); },
        adminSass: function () { return taskMethods.sass(paths.admin); },
        adminConcatJs: function () { return taskMethods.concatJs(paths.admin); },
        adminPreloadNgHtml: function () { return taskMethods.preloadNgHtml(paths.admin); }
    };



l_gulp.task("clean", tasks.clean);
l_gulp.task("assets", l_everytimetask, tasks.assets);
l_gulp.task("css", l_everytimetask.concat(["less", "sass"]), tasks.css);
l_gulp.task("less", l_everytimetask, tasks.less);
l_gulp.task("sass", l_everytimetask, tasks.sass);
l_gulp.task("browserify", l_everytimetask, tasks.browserify);
l_gulp.task("lintjs", tasks.lintjs);
l_gulp.task("concatJs", l_everytimetask, tasks.concatJs);
l_gulp.task("images", l_everytimetask, tasks.images);
l_gulp.task("clean_image_opts", l_everytimetask, tasks.clean_image_opts);
l_gulp.task("fonts", l_everytimetask, tasks.fonts);
l_gulp.task("nsp", tasks.nsp);
l_gulp.task("preTest", tasks.preTest);
l_gulp.task("mocha", tasks.mocha);
l_gulp.task("karma", tasks.karma);
l_gulp.task("istanbul", ["preTest"], tasks.mocha);
l_gulp.task("adminAssets", l_everytimetask, tasks.adminAssets);
l_gulp.task("adminSass", l_everytimetask, tasks.adminSass);
l_gulp.task("adminCss", l_everytimetask.concat(["adminSass"]), tasks.adminCss);
l_gulp.task("preloadNgHtml", l_everytimetask, tasks.preloadNgHtml);
l_gulp.task("adminConcatJs", l_everytimetask.concat(["preloadNgHtml"]), tasks.adminConcatJs);

l_gulp.task("build", [
    "assets",
    "less",
    "sass",
    "css",
    "browserify",
    "concatJs",
    "images",
    "fonts",
    "adminAssets",
    "adminSass",
    "adminCss",
    "adminConcatJs"
]);

l_gulp.task("default", ["build"]);
