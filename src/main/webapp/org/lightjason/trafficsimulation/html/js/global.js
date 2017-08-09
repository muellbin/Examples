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
 * prototype overload - add startwidth to string
 * @param pc_prefix prefix
 * @return boolean existance
 **/
String.prototype.startsWith = String.prototype.startsWith || function( pc_prefix ) {
        return this.indexOf(pc_prefix) === 0;
    };


/**
 * prototype overload - add endwidth to string
 * @param pc_suffix
 * @return boolean existance
 **/
String.prototype.endsWith = String.prototype.endsWith || function( pc_suffix ) {
        return this.match( pc_suffix+"$" ) === pc_suffix;
    };


/**
 * clear null bytes from the string
 * @return cleared null bytes
 **/
String.prototype.clearnull = String.prototype.clearnull || function() {
        return this.replace(/\0/g, "");
    };


/**
 * parse the string to a JSON object
 **/
String.prototype.toJSON = String.prototype.toJSON || function() {
        return jQuery.parseJSON(this.clearnull());
    };


/**
 * Fowler–Noll–Vo hash function
 * @see https://en.wikipedia.org/wiki/Fowler%E2%80%93Noll%E2%80%93Vo_hash_function#FNV-1a_hash
 * @see http://stackoverflow.com/questions/7616461/generate-a-hash-from-string-in-javascript-jquery
 *
 * @return hash value
 **/
String.prototype.hash = String.prototype.hash || function() {
        var hval = 0x811c9dc5;

        // Strips unicode bits, only the lower 8 bits of the values are used
        for (var i = 0; i < this.length; i++) {
            hval = hval ^ (this.charCodeAt(i) & 0xFF);
            hval += (hval << 1) + (hval << 4) + (hval << 7) + (hval << 8) + (hval << 24);
        }

        var val = hval >>> 0;
        return ("0000000" + (val >>> 0).toString(16)).substr(-8);
    };


/**
 * split a string into lines and remove empty lines
 * @param pc_separator separator
 * @return array of lines
 **/
String.prototype.splitLines = String.prototype.splitLines || function( pc_separator ) {
        var la_out = [];
        var lc_separator = "\n";
        if (pc_separator)
            lc_separator = pc_separator;

        jQuery.each(this.split(lc_separator), function (pn, pc) {
            if (pc)
                la_out.push(pc);
        });

        return la_out;
    };


/**
 * replaces all linebreaks to HTML br tag
 * @return modified string
 **/
String.prototype.nl2br = String.prototype.nl2br || function() {
        return this.replace(/(\r\n|\n\r|\r|\n)/g, "<br/>");
    };


/**
 * removes an element of an array
 * @param px_value value
 **/
Array.prototype.remove = Array.prototype.remove || function( px_value ) {
        delete this[ Array.prototype.indexOf.call(this, px_value) ];
    };


/**
 * elementwise convert to build a new array
 * @param px_value modifier closure
 * @returns a new array
 **/
Array.prototype.convert = Array.prototype.convert || function( px_value ) {
        var la_result = [];
        this.forEach( function( px_item ) { la_result.push( px_value(px_item) ); } );
        return la_result;
    };


/**
 * uniquify the array
 **/
Array.prototype.unique = Array.prototype.unique || function( px_value ) {
        return function(){ return this.filter( px_value) }
    }(function( a, b, c ) { return c.indexOf(a,b+1) < 0 });

/**
 * flatts an nested array
 */
Array.prototype.flatten = Array.prototype.flatten || function() {
    var la_result = [];
    this.forEach(function(i) {
        if (Array.isArray(i))
            la_result = la_result.concat( i.flatten() );
        else
            la_result.push(i);
    });
    return la_result;
};


/**
 * global function to get the object-type of a variable
 *
 * @param px_value value type
 * @param pc_type class type
 * @return class type
 **/
function classof( px_value, pc_type ) {
    return ({}).toString.call( px_value ).match(/\s([a-z|A-Z]+)/)[1].trim().toLowerCase() === pc_type.trim().toLowerCase();
}


/**
 * local storage access
 */
var DataStorage = (function (px_modul) {

    /**
     * local storage set value
     *
     * @param pc_key
     * @param px_value
     * @return value
     */
    px_modul.set = function( pc_key, px_value) {
        localStorage.setItem( pc_key, JSON.stringify( px_value ) );
        return px_value;
    };

    /**
     * local storag get value
     *
     * @param pc_key key
     * @return object
     */
    px_modul.get = function( pc_key ) {
        const lx = localStorage.getItem( pc_key );
        return lx ? JSON.parse( lx ) : null;
    };


    /**
     * local strorage get and set call
     *
     * @param pc_key key
     * @param px_function function
     */
    px_modul.getandset = function( pc_key, px_function )
    {
        const lx = localStorage.getItem( pc_key );
        localStorage.setItem( pc_key, JSON.stringify( px_function( lx ? JSON.parse( lx ) : null ) ) );
    };

    /**
     * local storage get value and remove it
     *
     * @param pc_key key
     * @return object
     */
    px_modul.remove = function( pc_key ) {
        const lx = localStorage.getItem( pc_key );
        localStorage.removeItem( pc_key );
        return lx ? JSON.parse( lx ) : null;
    };

    return px_modul;

}(DataStorage || {}));


/**
 * add an empty trigger to the empty function
 *
 * @Overload
 **/
if ('undefined' !== typeof window.jQuery) {
    jQuery.fn.raw_empty = jQuery.fn.empty;
    jQuery.fn.empty = function () {
        return this.raw_empty().trigger("empty", this)
    }
}
