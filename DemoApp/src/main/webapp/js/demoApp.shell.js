/*jslint         browser : true
  devel  : true, indent : 2,    maxerr   : 50,
  newcap : true, nomem  : true, plusplus : true,
  regexp : true, sloppy : true, vars     : true,
  white  : true
*/
/* global demoApp.shell */

demoApp.shell = (function( ) {
  'use strict';
  var 
    configMap = {
      resize_interval: 200
	},
    stateMap = {
        $container: undefined
    },
	jQueryMap = {},
	
	setJqueryMap,
    displaySolarSystem, initModule;
	  

	// ---- DOM methods
	setJqueryMap = function() {
	  var $container = stateMap.$container;
	  jQueryMap = {
	    $container : $container
	  };
	};

	displaySolarSystem = function() { 
		console.log( "displaySolarSystem");
	};	

		/*
		onResize = function() {

      if( stateMap.resize_toid) { return true; }
      
      spa.chat.handleResize();
      stateMap.resize_toid = setTimeout(
          function() { stateMap.resize_toid = undefined; },
          configMap.resize_interval);
      return true;
    };
    */

    // ---- Init
		
  initModule = function( $container) {
    
	  stateMap.$container = $container;
//	  $container.html( configMap.main_html);
//	  setJqueryMap();
		  

    events.on('onPlanetsLoaded', displaySolarSystem);
      
//    $(window)
//      .bind( 'resize', onResize)
//    	.bind( 'hashchange', onHashchange)
//      .trigger( 'hashchange');
  };
    
  return {
	  initModule : initModule
  };
}());

