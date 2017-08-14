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


var helpdescriptions = {
    "widget-simulation" : "widget-simulation description...",
    "widget-speed" : "widget-speed description...",
    "widget-penalty" : "widget-penalty description...",
    "widget-editor" : "widget-editor description...",
    "information" : "information description...",
    "agents" : "agents description...",
    "footer-buttons" : "run simulation, setting,..."
};

function showanno( id , backdrop, position, deletebg )
{
    jQuery("#simulation-help").hide();
    jQuery(".modal-backdrop").hide();

    // examples: http://iamdanfox.github.io/anno.js/
    var anno = new Anno( {
        target : '#' + id,
        position: position,
        content: helpdescriptions[id],
        buttons: [
        {
            text: 'Done',
            click: function(anno, evt){
                anno.hide();
                jQuery(".modal-backdrop").show()
                jQuery("#simulation-help").show();
            }
        } ]
    } ).show();

    if ( !backdrop ) jQuery( ".anno-overlay" ).css( "display", "none" );
    if ( deletebg ) jQuery( "#" + id ).css( "background", "none" );
}