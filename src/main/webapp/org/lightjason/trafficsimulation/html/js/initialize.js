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

var CURRENT_URL = window.location.href.split('#')[0].split('?')[0],
    $BODY = $('body'),
    $MENU_TOGGLE = $('#menu_toggle'),
    $SIDEBAR_MENU = $('#sidebar-menu'),
    $SIDEBAR_FOOTER = $('.sidebar-footer'),
    $LEFT_COL = $('.left_col'),
    $RIGHT_COL = $('.right_col'),
    $NAV_MENU = $('.nav_menu'),
    $FOOTER = $('footer');

/**
 * Sidebar
 */
function init_sidebar() {
    // TODO: This is some kind of easy fix, maybe we can improve this
    var setContentHeight = function () {
        // reset height
        $RIGHT_COL.css('min-height', $(window).height());

        var bodyHeight = $BODY.outerHeight(),
            footerHeight = $BODY.hasClass('footer_fixed') ? -10 : $FOOTER.height(),
            leftColHeight = $LEFT_COL.eq(1).height() + $SIDEBAR_FOOTER.height(),
            contentHeight = bodyHeight < leftColHeight ? leftColHeight : bodyHeight;

        // normalize content
        contentHeight -= $NAV_MENU.height() + footerHeight;

        $RIGHT_COL.css('min-height', contentHeight);
    };

    $SIDEBAR_MENU.find('a').on('click', function(ev) {
        var $li = $(this).parent();

        if ($li.is('.active')) {
            $li.removeClass('active active-sm');
            $('ul:first', $li).slideUp(function() {
                setContentHeight();
            });
        } else {
            // prevent closing menu if we are on child menu
            if (!$li.parent().is('.child_menu')) {
                $SIDEBAR_MENU.find('li').removeClass('active active-sm');
                $SIDEBAR_MENU.find('li ul').slideUp();
            }else
            {
                if ( $BODY.is( ".nav-sm" ) )
                {
                    $SIDEBAR_MENU.find( "li" ).removeClass( "active active-sm" );
                    $SIDEBAR_MENU.find( "li ul" ).slideUp();
                }
            }
            $li.addClass('active');

            $('ul:first', $li).slideDown(function() {
                setContentHeight();
            });
        }
    });

    // toggle small or large menu
    $MENU_TOGGLE.on('click', function() {
        if ($BODY.hasClass('nav-md')) {
            $SIDEBAR_MENU.find('li.active ul').hide();
            $SIDEBAR_MENU.find('li.active').addClass('active-sm').removeClass('active');
        } else {
            $SIDEBAR_MENU.find('li.active-sm ul').show();
            $SIDEBAR_MENU.find('li.active-sm').addClass('active').removeClass('active-sm');
        }

        $BODY.toggleClass('nav-md nav-sm');

        setContentHeight();
    });

    // check active menu
    $SIDEBAR_MENU.find('a[href="' + CURRENT_URL + '"]').parent('li').addClass('current-page');

    $SIDEBAR_MENU.find('a').filter(function () {
        return this.href === CURRENT_URL;
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
};

var randNum = function() {
    return (Math.floor(Math.random() * (1 + 40 - 20))) + 20;
};


//hover and retain popover when on popover content
var originalLeave = $.fn.popover.Constructor.prototype.leave;
$.fn.popover.Constructor.prototype.leave = function(obj) {
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

$BODY.popover({
    selector: '[data-popover]',
    trigger: 'click hover',
    delay: {
        show: 50,
        hide: 400
    }
});

function gd(year, month, day) {
    return new Date(year, month - 1, day).getTime();
}

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
    var lo = jQuery( "#ui-editorheader" );
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
            var l_dom = jQuery( "#ui-agents" ).empty();

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

    var l_markdown = new showdown.Converter(),
        l_screen = jQuery("#simulation-screen"),
        l_editor = null,
        l_music = null,
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
    LightJason.websocket( "/message" )
              .onmessage = function ( i )
              {
                  var lo = JSON.parse( i.data );
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
        .success(function(i) { jQuery("#simulation-speed").val(i).trigger( "change" ); })
        .error(function(i) { notifymessage({ title: i.statusText, text: i.responseText, type: "error" }); });


    // get language labels for html content
    jQuery( ".ui-languagelabel" ).each(function(k, e) {
        var lo = jQuery(e);
        LightJason.ajax( "/api/simulation/language/label/" + lo.data( "languagelabel" ) )
        .success(function(t) { lo.html(t); })
        .error(function(i) { notifymessage({ title: i.statusText, text: i.responseText, type: "error" }); });
    });


    // get language labels of data attributes
    jQuery( ".ui-languagelabeldata" ).each(function(k, e) {
        var lo = jQuery(e);
        LightJason.ajax( "/api/simulation/language/label/" + lo.data( "languagelabel" ) )
            .success(function(t) { lo.attr( "data-" + lo.data( "languagelabelid" ), t ); })
            .error(function(i) { notifymessage({ title: i.statusText, text: i.responseText, type: "error" }); });
    });


    // get language document
    jQuery( ".ui-languagedocs" ).each(function(k, e) {
        var lo = jQuery(e);

        LightJason.ajax( "/api/simulation/language/current" )
                  .success(function(l) {
                      jQuery.get( "/docs/" + lo.data( "languagedoc" ) + "." + l + ".md" , "text" )
                          .done(function(d) { lo.html( l_markdown.makeHtml(d) ); })
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
            var l_mode = CodeMirrorGrammar.getMode( grammar );

            l_mode.supportCodeFolding = true;
            l_mode.supportCodeMatching = true;
            l_mode.supportAutoCompletion = true;
            l_mode.matcher.options = { maxHighlightLineLength: 1000 };
            l_mode.autocompleter.options = { prefixMatch: true, caseInsensitiveMatch: false, inContext:true, dynamic:true };

            CodeMirror.defineMode("agentspeak", l_mode);
            CodeMirror.registerHelper("fold", l_mode.foldType, l_mode.folder);


            // autocomplete
            CodeMirror.registerHelper("hint", "anyword", function(editor, options) {

                var l_spacer = options && options.spacer || /\s/gu,
                    l_current = editor.getCursor(),
                    l_line = editor.getLine( l_current.line ),
                    l_start = l_current.ch,
                    l_end = l_start;

                for( var i=l_start; (i < l_line.length) && ( !l_spacer.test( l_line.charAt( i ) ) ); i++ )
                    l_end = i;

                for( var i=l_start; (i >= 0) && ( !l_spacer.test( l_line.charAt( i ) ) ); i-- )
                    l_start = i;

                // check on seach if it starts with ? or ! for getting plans otherwise actions
                var l_search = l_line.slice( l_start, l_end + 1 );
                var l_return = [];
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
                    extraKeys: {"Ctrl-Enter": "autocomplete"}
                }
            );

            l_editor.on( "blur", function(i) { codemirrorsave( i.options.sourceid, i.getValue() ); } );
        });
    } );


    // set simulation speed
    jQuery("#simulation-speed").change(function(e) {
        if (e.value)
            LightJason.ajax( "/api/simulation/time/set/" + Math.round( e.value ) )
                      .success(function(i) { notifymessage({ title: "Simulation", text: i, type: "success" }); })
                      .error(function(i) { notifymessage({ title: i.statusText, text: i.responseText, type: "error" }); });
    });


    // save panelty image
    jQuery( "#ui-savepanelty" ).click(function() {
        jQuery( "#simulation-panelty" ).get(0).toBlob(function( po_blob ) {
            saveAs( po_blob, "panelty.png" );
        });
    });


    // set simulation execution
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


    // save simulation image
    jQuery( "#ui-savesimulation" ).click(function() {
        jQuery( "#simulation-screen" ).get(0).toBlob(function( po_blob ) {
            saveAs( po_blob, "simulation.png" );
        });
    });


    // set shutdown button
    jQuery( ".simulation-shutdown" ).click(function() {
        LightJason.ajax( "/api/simulation/shutdown" )
            .error(function(i) {
                if ( ( i.status === 503 ) || ( i.status === 0 ) )
                    return;
                notifymessage({ title: i.statusText, text: i.responseText, type: "error" });
            })
    });

    // slide view
    jQuery( ".slide-view" ).click(function() {
        var l_source = jQuery(this).data("slidesource");
        if ( l_source )
            window.open( "slide.htm?slides=" + encodeURIComponent( l_source ), l_source );
    });


    // set fullscreen structure
    jQuery( ".ui-fullscreen" ).fullscreen();



    // --- animation engine ------------------------------------------------------------------------------------------------------------------------------------

    /** element functions */
    l_visualizationfunctions = {

        environment: {
            create: function( p_data )
            {
                var l_height = p_data.lanes + 2,
                    l_width = p_data.length,
                    l_tiles = [];

                l_tiles = l_tiles.concat( Array( l_width ).fill( 2 ) );
                for( var i=0; i < l_height - 2; i++ )
                    l_tiles = l_tiles.concat( Array( l_width ).fill( i % 2 === 0  ? 4 : 3 ) );
                l_tiles = l_tiles.concat( Array( l_width ).fill( 2 ) );

                l_music = l_engine.add.audio( "music" );
                l_engine.scale.setGameSize( jQuery( "#simulation-dashboard" ).width(), l_height * 32 );

                l_engine.load.tilemap(
                    'street',
                    null,
                    {
                        version: 1,
                        orientation: "orthogonal",
                        tileheight: 32,
                        tilewidth: 32,
                        height: l_height,
                        width: l_width,

                        layers: [{
                            data: l_tiles,
                            height: l_height,
                            name: "Street",
                            opacity: 1,
                            type: "tilelayer",
                            visible: true,
                            width: l_width,
                            x: 0,
                            y:0
                        }],

                        tilesets: [
                            {
                                firstgid: 1,
                                image: "assets/streettiles.png",
                                imageheight: 32,
                                imagewidth: 128,
                                margin: 0,
                                name: "StreetTiles",
                                spacing: 0,
                                tileheight: 32,
                                tilewidth: 32
                            }]
                    },
                    Phaser.Tilemap.TILED_JSON
                );

                var l_map = l_engine.add.tilemap( "street" );
                l_map.addTilesetImage( "StreetTiles", "streettiles" );

                var l_layer = l_map.createLayer( "Street" );
                l_layer.resizeWorld();
                l_layer.wrap = true;


                if ( jQuery( "#simulation-music" ).is(":checked") )
                    l_music.play();
            },

            remove: function( p_data )
            {
                l_music.stop();
                // @todo check because destroing fails
                // Object.values( l_visualizationobjects ).forEach(function(i) { i.destroy(); });
                // l_engine.destroy();
                l_visualizationobjects = {};
            }
        },

        defaultvehicle: {
            create: function (p_data) {
                // create a default vehicle
                l_visualizationobjects[p_data.id] = l_engine.add.sprite( p_data.x * 32, p_data.y * 32 + 9, "defaultvehicle" );
            },

            execute: function (p_data) {
                // move the vehicles in the new positions
                l_engine.add.tween( l_visualizationobjects[p_data.id] ).to( {x: p_data.x * 32, y: p_data.y * 32 + 9}, jQuery("#simulation-speed").val() ).start();
            }
        },

        uservehicle: {
            create: function (p_data) {
                //create user vehicle
                l_visualizationobjects[p_data.id] = l_engine.add.sprite( p_data.x * 32, p_data.y * 32 + 9, "uservehicle" );
                // camera follows the user vehicle
                l_engine.camera.follow(l_visualizationobjects[p_data.id]);
            }
        }
    };

    // set function references
    l_visualizationfunctions.uservehicle.execute = l_visualizationfunctions.defaultvehicle.execute;




    // engine initialization
    l_engine = new Phaser.Game(
        l_screen.width(),
        l_screen.height(),
        Phaser.AUTO,
        "simulation-screen",
        {
            background: "#fff",
            preload: function(i) {
                i.load.image( "streettiles", "assets/streettiles.png" );
                i.load.image( "uservehicle", "assets/uservehicle.png" );
                i.load.image( "defaultvehicle", "assets/defaultvehicle.png" );

                i.load.audio( "music", "assets/axelf.ogg" );
            }
        }
    );

    // enging bind to communication websocket
    LightJason.websocket( "/animation" )
              .onmessage = function ( e ) {
                var l_data = JSON.parse( e.data );
                if ( ( l_visualizationfunctions[l_data.type] ) && ( typeof( l_visualizationfunctions[l_data.type][l_data.status] ) === "function" ) )
                    l_visualizationfunctions[l_data.type][l_data.status]( l_data );
              };

    jQuery( "#simulation-music" ).change(function() {
        if (!l_music)
            return;

        if (this.checked)
            l_music.play();
        else
            l_music.stop();
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
    var dataobject =
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
                  var l_data = JSON.parse( i.data );
                  dataobject[l_data.type](l_data.data);
              };

});


