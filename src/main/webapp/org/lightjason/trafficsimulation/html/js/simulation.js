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
 * Resize function without multiple trigger
 *
 * Usage:
 * $(window).smartresize(function(){
 *     // code here
 * });
 */
(function($,sr){
    // debouncing function from John Hann
    // http://unscriptable.com/index.php/2009/03/20/debouncing-javascript-methods/
    var debounce = function (func, threshold, execAsap) {
        var timeout;

        return function debounced () {
            var obj = this, args = arguments;
            function delayed () {
                if (!execAsap)
                    func.apply(obj, args);
                timeout = null;
            }

            if (timeout)
                clearTimeout(timeout);
            else if (execAsap)
                func.apply(obj, args);

            timeout = setTimeout(delayed, threshold || 100);
        };
    };

    // smartresize
    jQuery.fn[sr] = function(fn){  return fn ? this.bind('resize', debounce(fn)) : this.trigger(sr); };

})(jQuery,'smartresize');



// --- gentelella functions ------------------------------------------------------------------------------------------------------------------------------------

/**
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

const CURRENTURL = window.location.href.split('#')[0].split('?')[0],
      BODY = jQuery( "body" ),
      MENUTOGGLE = jQuery( "#menu_toggle") ,
      SIDEBARMENU = jQuery( "#sidebar-menu" ),
      SIDEBARFOOTER = jQuery( ".sidebar-footer" ),
      LEFTCOLUM = jQuery( ".left_col" ),
      RIGHTCOLUM = jQuery( ".right_col" ),
      NAVIGATIONMENU = jQuery( ".nav_menu" ),
      FOOTER = jQuery( "footer" );

/**
 * Sidebar
 */
function init_sidebar() {
    // TODO: This is some kind of easy fix, maybe we can improve this
    var setContentHeight = function () {
        // reset height
        RIGHTCOLUM.css('min-height', $(window).height());

        var bodyHeight = BODY.outerHeight(),
            footerHeight = BODY.hasClass('footer_fixed') ? -10 : FOOTER.height(),
            leftColHeight = LEFTCOLUM.eq(1).height() + SIDEBARFOOTER.height(),
            contentHeight = bodyHeight < leftColHeight ? leftColHeight : bodyHeight;

        // normalize content
        contentHeight -= NAVIGATIONMENU.height() + footerHeight;

        RIGHTCOLUM.css('min-height', contentHeight);
    };

    SIDEBARMENU.find('a').on('click', function(ev) {
        var $li = $(this).parent();

        if ($li.is('.active')) {
            $li.removeClass('active active-sm');
            $('ul:first', $li).slideUp(function() {
                setContentHeight();
            });
        } else {
            // prevent closing menu if we are on child menu
            if (!$li.parent().is('.child_menu')) {
                SIDEBARMENU.find('li').removeClass('active active-sm');
                SIDEBARMENU.find('li ul').slideUp();
            }else
            {
                if ( BODY.is( ".nav-sm" ) )
                {
                    SIDEBARMENU.find( "li" ).removeClass( "active active-sm" );
                    SIDEBARMENU.find( "li ul" ).slideUp();
                }
            }
            $li.addClass('active');

            $('ul:first', $li).slideDown(function() {
                setContentHeight();
            });
        }
    });

    // toggle small or large menu
    MENUTOGGLE.on('click', function() {
        if (BODY.hasClass('nav-md')) {
            SIDEBARMENU.find('li.active ul').hide();
            SIDEBARMENU.find('li.active').addClass('active-sm').removeClass('active');
        } else {
            SIDEBARMENU.find('li.active-sm ul').show();
            SIDEBARMENU.find('li.active-sm').addClass('active').removeClass('active-sm');
        }

        BODY.toggleClass('nav-md nav-sm');

        setContentHeight();
    });

    // check active menu
    SIDEBARMENU.find('a[href="' + CURRENTURL + '"]').parent('li').addClass('current-page');

    SIDEBARMENU.find('a').filter(function () {
        return this.href === CURRENTURL;
    }).parent('li').addClass('current-page').parents('ul').slideDown(function() {
        setContentHeight();
    }).parent().addClass('active');

    // recompute content when resizing
    $(window).smartresize(function(){
        setContentHeight();
    });

    setContentHeight();

    // fixed sidebar
    if ($.fn.mCustomScrollbar) {
        $('.menu_fixed').mCustomScrollbar({
            autoHideScrollbar: true,
            theme: 'minimal',
            mouseWheel:{ preventDefault: true }
        });
    }
}


//hover and retain popover when on popover content
var originalLeave = jQuery.fn.popover.Constructor.prototype.leave;
jQuery.fn.popover.Constructor.prototype.leave = function(obj) {
    var self = obj instanceof this.constructor ?
        obj : $(obj.currentTarget)[this.type](this.getDelegateOptions()).data('bs.' + this.type);
    var container, timeout;

    originalLeave.call(this, obj);

    if (obj.currentTarget) {
        container = $(obj.currentTarget).siblings('.popover');
        timeout = self.timeout;
        container.one('mouseenter', function() {
            //We entered the actual popover – call off the dogs
            clearTimeout(timeout);
            //Let's monitor popover content instead
            container.one('mouseleave', function() {
                $.fn.popover.Constructor.prototype.leave.call(self, self);
            });
        });
    }
};

BODY.popover({
    selector: '[data-popover]',
    trigger: 'click hover',
    delay: {
        show: 50,
        hide: 400
    }
});


/**
 * AUTOSIZE
 */
function init_autosize() {
    if(typeof $.fn.autosize !== 'undefined'){
        autosize($('.resizable_textarea'));
    }
}

/**
 * PNotify
 */
function init_pnotify() {

    if( typeof (PNotify) === 'undefined'){ return; }

    PNotify.prototype.options.styling = "bootstrap3";
    PNotify.prototype.options.styling = "fontawesome";

}

/**
 * KNOB
 */
function init_knob() {
    if( typeof ($.fn.knob) === 'undefined'){ return; }

    $(".knob").knob({
        change: function(i) { this.$.trigger({ type: "change", value: i }); },

        draw: function() {

            // "tron" case
            if (this.$.data('skin') === 'tron') {

                this.cursorExt = 0.3;

                var a = this.arc(this.cv) // Arc
                    ,
                    pa // Previous arc
                    , r = 1;

                this.g.lineWidth = this.lineWidth;

                if (this.o.displayPrevious) {
                    pa = this.arc(this.v);
                    this.g.beginPath();
                    this.g.strokeStyle = this.pColor;
                    this.g.arc(this.xy, this.xy, this.radius - this.lineWidth, pa.s, pa.e, pa.d);
                    this.g.stroke();
                }

                this.g.beginPath();
                this.g.strokeStyle = r ? this.o.fgColor : this.fgColor;
                this.g.arc(this.xy, this.xy, this.radius - this.lineWidth, a.s, a.e, a.d);
                this.g.stroke();

                this.g.lineWidth = 2;
                this.g.beginPath();
                this.g.strokeStyle = this.o.fgColor;
                this.g.arc(this.xy, this.xy, this.radius - this.lineWidth + 1 + this.lineWidth * 2 / 3, 0, 2 * Math.PI, false);
                this.g.stroke();

                return false;
            }
        }

    });

    // Example of infinite knob, iPod click wheel
    var v, up = 0,
        down = 0,
        i = 0,
        $idir = $("div.idir"),
        $ival = $("div.ival"),
        incr = function() {
            i++;
            $idir.show().html("+").fadeOut();
            $ival.html(i);
        },
        decr = function() {
            i--;
            $idir.show().html("-").fadeOut();
            $ival.html(i);
        };
    $("input.infinite").knob({
        min: 0,
        max: 20,
        stopper: false,
        change: function() {
            if (v > this.cv) {
                if (up) {
                    decr();
                    up = 0;
                } else {
                    up = 1;
                    down = 0;
                }
            } else {
                if (v < this.cv) {
                    if (down) {
                        incr();
                        down = 0;
                    } else {
                        down = 1;
                        up = 0;
                    }
                }
            }
            v = this.cv;
        }
    });

}



// --- function definition -------------------------------------------------------------------------------------------------------------------------------------

/**
 * creates a notify-message
 *
 * @param px_options object, text, title and type must be set
 */
function notifymessage( px_options )
{
    new PNotify( jQuery.extend( true, { delay: 2500, animate_speed: "fast" }, px_options ) );
}


/**
 * codemirror save function
 *
 * @param pc_id id
 * @param pc_source source code
 */
function codemirrorsave( pc_id, pc_source )
{
    if (!pc_id)
        return;

    LightJason.ajax({
        url: "/api/simulation/asl/set/" + pc_id,
        data: pc_source,
        dataType: "text",
        method: "POST",
        headers: { "Content-Type": "text/plain" }
    })
    .success(function(i) { if (i) notifymessage({ title: "Agent", text: i, type: "success" }); })
    .error(function(i) { notifymessage({ title: i.statusText, text: i.responseText, type: "error" }); });
}

/**
 * sets the asl name into ui
 *
 * @param pc_value value
 */
function aslname( pc_value )
{
    const lo = jQuery( "#ui-editorheader" );
    lo.html( lo.html().split("<")[0] + " <small>" + pc_value + "</small>" );
}

/**
 * loads an asl script into the editor
 *
 * @param pc_id agent id
 * @param po_editor editor instance
 */
function loadagent( pc_id, po_editor )
{
    LightJason.ajax( "/api/simulation/asl/get/" + pc_id )
        .success(function(i) {
            po_editor.setValue( i );
            po_editor.options.sourceid = pc_id;
            aslname( pc_id );

        })
        .error(function(i) { notifymessage({ title: i.statusText, text: i.responseText, type: "error" }); });
}

/**
 * builds the agent menu list
 */
function agentlist()
{
    LightJason.ajax( "/api/simulation/agents" )
        .success(function(o) {
            const l_dom = jQuery( "#ui-agents" ).empty();

            o.forEach(function(i) {
                l_dom.append(
                    jQuery("<li>").append(
                        jQuery("<a>").attr( "href", "#" )
                            .attr("data-sourceid", i)
                            .addClass("ui-agent-source")
                            .text(i)
                    )
                );
            });
        });
}


CodeMirror.commands.save = function(i) { codemirrorsave( i.options.sourceid, i.getValue() ); };

MathJax.Hub.Config({
    tex2jax:{
        inlineMath: [["$","$"]],
        displayMath: [["$$","$$"]],
        processEscapes: !0,
        processEnvironments: !1,
        skipTags: ["script","noscript","style","textarea","pre","code"],
        TeX: {
            equationNumbers: {
                autoNumber:"AMS"
            },
            extensions: ["autoload-all.js"]
        }
    }
});

// -------------------------------------------------------------------------------------------------------------------------------------------------------------

/**
 * document-ready execution
 */
jQuery(function() {

    // --- main initialize -------------------------------------------------------------------------------------------------------------------------------------

    init_sidebar();
    init_pnotify();
    init_knob();
    init_autosize();
    agentlist();

    const MARKDOWN = new showdown.Converter(),
          SIMULATIONSCREEN = jQuery("#simulation-screen"),
          SIMULATIONSPEED = jQuery("#simulation-speed"),
          SIMULATIONMUSIC = jQuery( "#simulation-music" ),
          WSANIMATION = LightJason.websocket( "/animation" ),
          WSMESSAGES = LightJason.websocket( "/message" ),
          TILESIZE = 32,
          VEHICLEXSIZE = 32,
          VEHICLEYSIZE = 16,
          PIXELCENTER = 9,
          GAUGE = new RadialGauge({
                            renderTo: 'simulation-speedview',
                            width: 200,
                            height: 130,
                            units: "km/h",
                            minValue: 0,
                            maxValue: 220,
                            majorTicks: [ "0", "20", "40", "60", "80", "100", "120", "140", "160", "180", "200", "220" ],
                            highlights: [{
                                "from": 160,
                                "to": 220,
                                "color": "rgba(200, 50, 50, .75)"
                            }],
                            minorTicks: 2,
                            strokeTicks: true,
                            colorPlate: "#fff",
                            borderShadowWidth: 0,
                            borders: false,
                            needleType: "arrow",
                            needleWidth: 2,
                            needleCircleSize: 7,
                            needleCircleOuter: true,
                            needleCircleInner: false,
                            animationDuration: SIMULATIONSPEED.val(),
                            animationRule: "linear"
                  }).draw();

    var l_editor = null,
        l_engine = null,
        l_visualizationobjects = {},
        l_visualizationfunctions = {};




    // --- initialize main components --------------------------------------------------------------------------------------------------------------------------

    // panel toolbox
    jQuery('.close-link').click(function () { jQuery(this).closest('.x_panel').remove(); });
    jQuery('.collapse-link').on('click', function() {
        var $BOX_PANEL = $(this).closest('.x_panel'),
            $ICON = $(this).find('i'),
            $BOX_CONTENT = $BOX_PANEL.find('.x_content');

        // fix for some div with hardcoded fix class
        if ($BOX_PANEL.attr('style')) {
            $BOX_CONTENT.slideToggle(200, function(){
                $BOX_PANEL.removeAttr('style');
            });
        } else {
            $BOX_CONTENT.slideToggle(200);
            $BOX_PANEL.css('height', 'auto');
        }

        $ICON.toggleClass('fa-chevron-up fa-chevron-down');
    });


    // tooltip
    jQuery('[data-toggle="tooltip"]').tooltip({ container: 'body' });


    // switchery
    jQuery(".js-switch").each(function(i,e) { var s = new Switchery( e, { color: "#26B99A" } ); });



    // --- initialize ui function & execution  -----------------------------------------------------------------------------------------------------------------

    // notify messages
    WSMESSAGES.onmessage = function ( i )
               {
                   const lo = JSON.parse( i.data );
                   new PNotify({
                       title: lo.title,
                       text: lo.text,
                       type: lo.type,
                       delay: lo.delay,
                       animate_speed: "fast"
                   })
               };


    // initialize simulation speed
    LightJason.ajax( "/api/simulation/time/get" )
        .success(function(i) { SIMULATIONSPEED.val(i).trigger( "change" ); })
        .error(function(i) { notifymessage({ title: i.statusText, text: i.responseText, type: "error" }); });

    // initialize music
    LightJason.ajax( "/api/simulation/music" )
              .success(function(i) { if ( i !== "true" ) SIMULATIONMUSIC.trigger("click"); });


    // get language labels for html content
    jQuery( ".ui-languagelabel" ).each(function(k, e) {
        const lo = jQuery(e);
        LightJason.ajax( "/api/simulation/language/label/" + lo.data( "languagelabel" ) )
        .success(function(t) { lo.html(t); })
        .error(function(i) { notifymessage({ title: i.statusText, text: i.responseText, type: "error" }); });
    });


    // get language labels of data attributes
    jQuery( ".ui-languagelabeldata" ).each(function(k, e) {
        const lo = jQuery(e);
        LightJason.ajax( "/api/simulation/language/label/" + lo.data( "languagelabel" ) )
            .success(function(t) { lo.attr( "data-" + lo.data( "languagelabelid" ), t ); })
            .error(function(i) { notifymessage({ title: i.statusText, text: i.responseText, type: "error" }); });
    });


    // get language document
    jQuery( ".ui-languagedocs" ).each(function(k, e) {
        const lo = jQuery(e);
        LightJason.ajax( "/api/simulation/language/current" )
                  .success(function(l) {
                      jQuery.get( "/docs/" + lo.data( "languagedoc" ) + "." + l + ".md" , "text" )
                          .done(function(d) { lo.html( MARKDOWN.makeHtml(d) ); })
                          .fail(function(i) { notifymessage({ title: i.statusText, text: i.responseText, type: "error" }); });
                  })
                  .error(function(i) { notifymessage({ title: i.statusText, text: i.responseText, type: "error" }); });
    });


    // read codemirror grammer and build autocompletion & syntax highlight
    // @see http://foo123.github.io/examples/codemirror-grammar/
    jQuery.getJSON( "/assets/agentspeak.json", function(grammar) {
        jQuery.getJSON( "/assets/action.json", function(actions) {

            // syntax highlighting
            grammar.Lex.builtin.tokens = Object.keys(actions);
            const l_mode = CodeMirrorGrammar.getMode( grammar );

            l_mode.supportCodeFolding = true;
            l_mode.supportCodeMatching = true;
            l_mode.supportAutoCompletion = true;
            l_mode.matcher.options = { maxHighlightLineLength: 1000 };
            l_mode.autocompleter.options = { prefixMatch: true, caseInsensitiveMatch: false, inContext:true, dynamic:true };

            CodeMirror.defineMode("agentspeak", l_mode);
            CodeMirror.registerHelper("fold", l_mode.foldType, l_mode.folder);


            // autocomplete
            CodeMirror.registerHelper("hint", "anyword", function(editor, options) {

                const l_spacer = options && options.spacer || /\s/gu,
                      l_current = editor.getCursor(),
                      l_line = editor.getLine( l_current.line );

                var l_start = l_current.ch,
                    l_end = l_start;

                for( var i=l_start; (i < l_line.length) && ( !l_spacer.test( l_line.charAt( i ) ) ); i++ )
                    l_end = i;

                for( var i=l_start; (i >= 0) && ( !l_spacer.test( l_line.charAt( i ) ) ); i-- )
                    l_start = i;

                // check on seach if it starts with ? or ! for getting plans otherwise actions
                const l_search = l_line.slice( l_start, l_end + 1 );
                const l_return = [];
                if ( l_start !== l_end )
                {
                    if ((l_search.startsWith("!")) || (l_search.startsWith("?")))
                        editor.getValue().match( /\+!.*<-|$/gu )
                                         .map(function(i) { return i.replace("!", "").replace("+", "").replace("<-", "").trim(); })
                                         .filter(function(i) { return i; })
                                         .forEach(function(i) { l_return.push(i); });
                    else
                    {
                        grammar.Lex.builtin.tokens
                               .filter(function (i) { return i.startsWith(l_search); })
                               .forEach(function(i) { l_return.push(i); });
                        grammar.Lex.keyword.tokens
                               .filter(function (i) { return i.startsWith(l_search); })
                               .forEach(function(i) { l_return.push(i); });
                    }
                }

                return {
                    list: l_return.sort(),
                    from: CodeMirror.Pos( l_current.line, l_start ),
                    to: CodeMirror.Pos( l_current.line, l_end )
                };
            });


            // codemirror initialization
            l_editor = CodeMirror.fromTextArea(
                document.getElementById("ui-editor"),
                {
                    sourceid: null,
                    theme: "neat",
                    mode: "agentspeak",
                    autofocus: true,
                    lineNumbers: true,
                    matchBrackets: true,
                    indentUnit: 4,
                    indentWithTabs: false,
                    matching: true,
                    lint: false,
                    extraKeys: { "Ctrl-Enter": "autocomplete" }
                }
            );

            l_editor.on( "blur", function(i) { codemirrorsave( i.options.sourceid, i.getValue() ); } );
        });
    } );


    // set simulation speed
    SIMULATIONSPEED.change(function(e) {
        if (e.value)
            LightJason.ajax( "/api/simulation/time/set/" + Math.round( e.value ) )
                      .success(function(i) {
                          GAUGE.update({ animationDuration: e.value });
                          notifymessage({ title: "Simulation", text: i, type: "success" });
                      })
                      .error(function(i) { notifymessage({ title: i.statusText, text: i.responseText, type: "error" }); });
    });


    // run simulation
    jQuery( "#simulation-run" ).click(function() {
        LightJason.ajax( "/api/simulation/run" )
            .error(function(i) { notifymessage({ title: i.statusText, text: i.responseText, type: "error" }); });
    });


    // creates an agent
    jQuery( "#simulation-createagent" ).click(function() {
        var lc_id = jQuery( "#simulation-agentname" ).val();
        LightJason.ajax( "/api/simulation/asl/create/" + lc_id )
            .success(function(i) { notifymessage({ title: "Agent", text: i, type: "success" }); agentlist(); loadagent( lc_id, l_editor ); })
            .error(function(i) { notifymessage({ title: i.statusText, text: i.responseText, type: "error" }); });
    });


    // delete agent
    jQuery( "#ui-deleteagent" ).click(function() {
        LightJason.ajax( "/api/simulation/asl/remove/" + l_editor.options.sourceid )
            .success(function(i) {
                notifymessage({ title: "Agent", text: i, type: "success" });
                l_editor.setValue("");
                l_editor.options.sourceid = undefined;
                agentlist();
                aslname( "" );
            })
            .error(function(i) { notifymessage({ title: i.statusText, text: i.responseText, type: "error" }); });
    });

    // bind action to load asl source
    jQuery(document).on( "click", ".ui-agent-source", function() {
        loadagent( jQuery(this).data("sourceid"), l_editor );
    });

    // shutdown button
    jQuery( ".simulation-shutdown" ).click(function() {
        WSANIMATION.close();
        WSMESSAGES.close();

        LightJason.ajax( "/api/simulation/shutdown" )
            .error(function(i) {
                if ( ( i.status === 503 ) || ( i.status === 0 ) )
                    return;
                notifymessage({ title: i.statusText, text: i.responseText, type: "error" });
                window.close();
            })
    });

    // slide view
    jQuery( ".slide-view" ).click(function() {
        const l_source = jQuery(this).data("slidesource");
        if ( l_source )
            window.open( "slide.htm?slides=" + encodeURIComponent( l_source ), l_source );
    });


    // set fullscreen structure
    jQuery( ".ui-fullscreen" ).fullscreen();



    // --- animation engine ------------------------------------------------------------------------------------------------------------------------------------

    /** element functions */
    l_visualizationfunctions = {

        environment: {

            // initialize environment with tilemap
            initialize: function( p_data )
            {
                DataStorage.set( "environment", jQuery.extend( {}, p_data, { time : new Date().getTime() } ) );
                var l_tiles = [];
                const HEIGHT = p_data.laneslefttoright + p_data.lanesrighttoleft + 2,
                      WIDTH = p_data.length;

                // build tiles with footway
                for( var i = 0; i < WIDTH; i++ )
                {
                    l_tiles.push( Math.ceil( Math.random() * 8 + 2 ) );
                }
                for( var i=0; i < p_data.laneslefttoright; i++ )
                    l_tiles = l_tiles.concat( Array( WIDTH ).fill( i % 2 === 0  ? 1 : 2 ) );
                for( var i=0; i < p_data.lanesrighttoleft; i++ )
                    l_tiles = l_tiles.concat( Array( WIDTH ).fill( i % 2 === 0  ? 1 : 2 ) );
                for( var i = 0; i < WIDTH; i++ )
                {
                    l_tiles.push( Math.ceil( Math.random() * 8 + 2 ) );
                }

                if ( SIMULATIONMUSIC.is(":checked") )
                    l_engine.music.play();

                l_engine.scale.setGameSize( jQuery( "#simulation-dashboard" ).width(), HEIGHT * 32 );

                l_engine.load.tilemap(
                    'street',
                    null,
                    {
                        version: 1,
                        orientation: "orthogonal",
                        tileheight: TILESIZE,
                        tilewidth: TILESIZE,
                        height: HEIGHT,
                        width: WIDTH,

                        layers: [{
                            data: l_tiles,
                            height: HEIGHT,
                            name: "street",
                            opacity: 1,
                            type: "tilelayer",
                            visible: true,
                            width: WIDTH,
                            x: 0,
                            y:0
                        }],

                        tilesets: [{
                            firstgid: 1,
                            image: "assets/streettiles.png",
                            imageheight: TILESIZE,
                            imagewidth: 10 * TILESIZE,
                            margin: 0,
                            name: "streettiles",
                            spacing: 0,
                            tileheight: TILESIZE,
                            tilewidth: TILESIZE
                        }]
                    },
                    Phaser.Tilemap.TILED_JSON
                );

                const MAP = l_engine.add.tilemap( "street" );
                MAP.addTilesetImage( "streettiles", "streettiles" );

                const LAYER = MAP.createLayer( "street" );
                LAYER.resizeWorld();
                LAYER.wrap = true;
            },


            // remove environment content (sprites) but not the timemap
            release: function( p_data )
            {
                l_engine.music.stop();

                Promise.all(
                    Object.values( l_visualizationobjects )
                          .map(function(i) {
                              return new Promise( function(resolve) {
                                  l_engine.tweens.remove(i);
                                  i.destroy();
                                  resolve();
                              } );
                          })
                )
                .then(function() {
                     l_visualizationobjects = {};
                     DataStorage.remove( "environment" );
                });
            }
        },


        defaultvehicle: {

            // initialize a default vehicle
            initialize: function (p_data) {
                l_visualizationobjects[p_data.id] = l_engine.add.sprite( p_data.x * TILESIZE + VEHICLEXSIZE / 2, ( p_data.y + 1 ) * TILESIZE + VEHICLEYSIZE / 2 + PIXELCENTER, p_data.type );
                l_visualizationobjects[p_data.id].anchor.setTo( 0.5, 0.5 );
                if( p_data.goal === 0 )
                    l_visualizationobjects[p_data.id].angle = 180;

                WSANIMATION.send( JSON.stringify({ id: p_data.id }) );
            },


            // execute vehicle, create new tween animation (y-position must be increment based on footway)
            execute: function (p_data) {
                if ( !l_visualizationobjects[p_data.id] )
                    l_visualizationfunctions[p_data.type]["initialize"](p_data);

                const l_xpos = p_data.x * TILESIZE + VEHICLEXSIZE / 2,
                      l_ypos = ( p_data.y + 1 ) * TILESIZE + VEHICLEYSIZE / 2 + PIXELCENTER;

                // update storage
                //DataStorage.getandset( "environment", function(i){ i.time = new Date().getTime(); return i; } );

                // create tween
                const TWEEN = l_engine.add.tween( l_visualizationobjects[p_data.id] ).to({ x: l_xpos, y: l_ypos }, SIMULATIONSPEED.val() );
                TWEEN.onComplete.add(function(){ WSANIMATION.send( JSON.stringify({ id: p_data.id }) ); }, this);
                TWEEN.delay(0);
                TWEEN.start();
            },


            // release for removing vehicles
            release: function( p_data ) {
                if ( !l_visualizationobjects[p_data.id] )
                    return;

                l_engine.tweens.remove( l_visualizationobjects[p_data.id] );
                l_visualizationobjects[p_data.id].destroy();
                delete l_visualizationobjects[p_data.id];
            }
        },

        uservehicle: {
            // initialize a user vehicle
            initialize: function (p_data) {
                l_visualizationfunctions.defaultvehicle.initialize( p_data );
                l_engine.camera.follow(l_visualizationobjects[p_data.id]);

                var l_max = Math.ceil( p_data.maxspeed / 10 + 5) * 10;
                GAUGE.value = p_data.speed;
                GAUGE.update({
                    maxValue: l_max,
                    majorTicks: Array.from(Array( Math.ceil( l_max / 20 ) ).keys()).map(function(i) { return i*20; }).map(function(i) { return i.toString() }),
                    highlights: [{
                        "from": Math.floor( l_max * 0.75 / 20 ) * 20,
                        "to": l_max,
                        "color": "rgba(200, 50, 50, .75)"
                    }]
                });

                jQuery( "#ui-acceleration" ).text( Math.round( p_data.acceleration ) );
                jQuery( "#ui-deceleration" ).text( Math.round( p_data.deceleration ) );
            },

            execute: function (p_data) {
                GAUGE.value = p_data.speed;
                l_visualizationfunctions.defaultvehicle.execute( p_data );
            },

            release: function(p_data) { l_visualizationfunctions.defaultvehicle.release( p_data ) }
        }
    };



    // engine initialization
    l_engine = new Phaser.Game(
        SIMULATIONSCREEN.width(),
        SIMULATIONSCREEN.height(),
        Phaser.AUTO,
        "simulation-screen",
        {
            background: "#fff",
            preload: function(i) {
                i.load.image( "streettiles", "assets/streettiles.png" );
                i.load.image( "uservehicle", "assets/uservehicle.png" );
                i.load.image( "defaultvehicle", "assets/defaultvehicle.png" );

                i.load.audio( "music", "assets/axelf.ogg" );
            },

            create: function(g) {
                g.music = g.add.audio( "music" );
                g.music.allowMultiple = false;
                g.music.loop = true;

                // reinitialize content if the browser tab was closed
                const ENV = DataStorage.remove( "environment" );
                if ( ENV )
                    LightJason.ajax( "/api/simulation/cookie/expire" )
                              .success(function(i) {
                                  if ( ( new Date().getTime() - ENV.time ) / 1000 < parseInt(i) )
                                      LightJason.ajax( "/api/simulation/elements" )
                                                .success(function(j) {
                                                    l_visualizationfunctions[ENV.type][ENV.status]( ENV );
                                                    j.filter( function(o) { return o.type !== "environment"; } )
                                                     .forEach( function( o ) { l_visualizationfunctions[o.type][o.status]( o ); });
                                                })
                                                .error(function(i) { notifymessage({ title: i.statusText, text: i.responseText, type: "error" }); });
                                })
                                .error(function(i) { notifymessage({ title: i.statusText, text: i.responseText, type: "error" }); });
            }
        }
    );


    // engine bind to communication websocket
    WSANIMATION
              .onmessage = function ( e ) {
                const OBJECTDATA = JSON.parse( e.data );
                if ( ( l_visualizationfunctions[OBJECTDATA.type] ) && ( typeof( l_visualizationfunctions[OBJECTDATA.type][OBJECTDATA.status] ) === "function" ) )
                    l_visualizationfunctions[OBJECTDATA.type][OBJECTDATA.status](OBJECTDATA);
              };


    // music enable / disable
    SIMULATIONMUSIC.change(function() {
        if ( this.checked ) {
            if ( DataStorage.get("environment") )
                l_engine.music.play();
        }
        else
            l_engine.music.stop();
    });











    // ---- @test section --------------------------------------------------------------------------------------------------------------------------------------

    // https://canvasjs.com/docs/charts/basics-of-creating-html5-chart/updating-chart-options/
    var chart = new Chart( jQuery( "#simulation-panelty" ), {
        type: "line",
        data: {
            labels: [0],
            datasets: [{
                data: [0],
                fill: false,
                borderColor: "rgba(255,99,132,1)"
            }]
        },
        options: {
            legend: {
                display: false
            }
        }
    });

    var chartlabel = 1;
    const dataobject =
    {
        penalty: function( p_data )
        {
            chart.data.labels.push( chartlabel );
            chart.data.datasets.forEach(function(dataset) {
                dataset.data.push( p_data );
            });
            chart.update();
            chartlabel++;
        }
    };

    // notify messages
    LightJason.websocket( "/data" )
              .onmessage = function ( i )
              {
                  const l_data = JSON.parse( i.data );
                  dataobject[l_data.type](l_data.data);
              };

});


