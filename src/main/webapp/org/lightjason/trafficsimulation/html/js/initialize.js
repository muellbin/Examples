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

jQuery(function() {

    // set codemirror
    var l_editor = CodeMirror.fromTextArea( document.getElementById("ui-editor"), { lineNumbers: true });


    // set shutdown button
    jQuery( ".simulation-shutdown" ).click(function() {
        LightJason.ajax( "/api/simulation/shutdown" )
                  .error(function(i) {
                      if ( ( i.status === 503 ) || ( i.status === 0 ) )
                          return;

                          alert("error");
                  })
    });


    // get agent list
    LightJason.ajax( "/api/simulation/agents" )
        .done(function(o) {
            var l_dom = jQuery( "#ui-agents" );
            Object.keys(o).forEach(function(i) {
                l_dom.append(
                    jQuery("<li>").append(
                        jQuery("<a>").attr( "href", "#" )
                                     .addClass("ui-agent-source")
                                     .text(i)
                    )
                );
            });

            console.log(i);
        });



    // set simulation execution
    jQuery( "#simulation-run" ).click(function() {
        LightJason.ajax( "/api/simulation/run" ).error(function() { alert("error") });
    });


    // slide view
    jQuery( ".slide-view" ).click(function() {
        var l_source = jQuery(this).data("slidesource");
        if ( l_source )
            window.open( "slide.htm", l_source ).slide = l_source;
    });

    // set fullscreen structure
    jQuery( "#ui-fullscreen" ).click(function() {
        var el = document.documentElement,
            rfs = el.requestFullScreen || el.webkitRequestFullScreen || el.mozRequestFullScreen || el.msRequestFullScreen;

        if (typeof rfs !== "undefined" && rfs)
            rfs.call(el);
        else
            if (typeof window.ActiveXObject !== "undefined")
            {
                var wscript = new ActiveXObject("WScript.Shell");
                if (wscript !== null)
                    wscript.SendKeys("{F11}");
            }
    });


    // test chart
    new Chart( jQuery( "#simulation-panelty" ), {
        type: "line",
        data: {
            labels: [1,2,3,4,5,6,7,8,9,10,11,12,13,14,15,15,17,18,19,20],
            datasets: [{
                data: [12, 19, 3, 5, 2, 3, 7, 10, 98, 76, 54, 128, 55, 44, 33],
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

});
