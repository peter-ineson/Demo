/*jslint         browser : true,
  devel  : true, indent : 2,    maxerr   : 50,
  newcap : true, nomem  : true, plusplus : true,
  regexp : true, sloppy : true, vars     : true,
  white  : true
*/
/*global $, io, spa */
demoApp.data = (function() {
  'use strict';
  var
    stateMap = { sio: null },
    loadSun, loadPlanets, initModule;
  
/*
  makeSio = function() {
    var socket = io.connect( '/chat');
    
    return {
      emit : function ( event_name, data ) {
        socket.emit( event_name, data);
        },
      on : function ( event_name, callback ) {
        socket.on( event_name, function() {
          callback( arguments );
        });
      }
    };    
  };

  getSio = function() {
    if ( ! stateMap.sio ) { stateMap.sio = makeSio(); }
    return stateMap.sio;
  };
*
*
*/
  
  //encodeURIComponent( "bodyType=PLANET");
  loadPlanets = function() {
	$.getJSON( "rest/solarBodies", { where: "bodyType=PLANET"} , function( planets ) {
      //console.log( "planets = ", planets )
      for (var planetIdx in planets) {
        //console.log( "planetIdx = ", planetIdx );
        //console.log( "Planet = ",  planets[ planetIdx]);
		demoApp.model.makeSolarBody(planets[ planetIdx]);
      }
      events.emit('onPlanetsLoaded');
    });
  };

  loadSun = function() {
		$.getJSON( "rest/solarBodies", { where: "bodyType=SUN"} , function( bodies ) {
	      //console.log( "sun = ", bodies )
	      for (var sunIdx in bodies) {
	        //console.log( "sunIdx = ", sunIdx );
	        //console.log( "Sun = ",  bodies[ sunIdx]);
			demoApp.model.makeSolarBody(bodies[ sunIdx]);
	      }
	      events.emit('onPlanetsLoaded');
	    });
	  };
  
  initModule = function() { };
  
  return {
	loadSun: loadSun,
	loadPlanets: loadPlanets,
    initModule: initModule
  };

}());

