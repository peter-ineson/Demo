/*jslint         browser : true,
  devel  : true, indent : 2,    maxerr   : 50,
  newcap : true, nomem  : true, plusplus : true,
  regexp : true, sloppy : true, vars     : true,
  white  : true
*/
/*global $, io, spa */
demoApp.util = (function() {
  'use strict';
  var log;

  log = function( p1, p2) { 
    if( debug) {
      if( p2 != undefined) {
        console.log( p1, p2);
      } else {
        console.log( p1);
      }
    }
  };
  
  return {
	log: log
  };

}());

