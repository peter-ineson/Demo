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
        $container: undefined,
        $displayContainer: undefined,
        $template: undefined,
        restUrl: undefined
    },
	jQueryMap = {},
	
	setJqueryMap,
    displaySolarSystem, initModule;
	  

	// ---- DOM methods
	setJqueryMap = function() {
	  var $container = stateMap.$container;
	  jQueryMap = {
	    $container : $container,
	    $displayContainer : stateMap.$displayContainer,
	    $template : stateMap.$template,
	    restUrl : stateMap.restUrl
	  };
	};

	displaySolarSystem = function() { 
	  demoApp.util.log( "displaySolarSystem");
	  var dao = demoApp.model.solarBody;
	  var sunDao = dao.get_by_body_type(dao.BODY_TYPE_SUN);
	  demoApp.util.log( "sun count: ", sunDao.count());
	  if( sunDao.count() === 0) {
		  return;
	  }
	  var sun = sunDao.first();
	  demoApp.util.log( "sun", sun);
	  
	  var planetDao = dao.get_by_body_type(dao.BODY_TYPE_PLANET);
	  demoApp.util.log( "planets count: ", planetDao.count());
	  if( planetDao.count() === 0) {
		  return;
	  }
	  
	  var planets = planetDao.order( "orbitDistance").get();
	  demoApp.util.log( "planets", planets);
	  
	  var lastPlanet = planets[ planets.length - 1];
	  var fullWidth = lastPlanet.orbitDistance + lastPlanet.radius;
	  demoApp.util.log( "lastPlanet.orbitDistance", lastPlanet.orbitDistance);
	  demoApp.util.log( "fullWidth", fullWidth);

	  var templateData = {
	    restUrl: jQueryMap.restUrl,
	    sun: sun,
	    planets: planets
	  };

	  var htmlOutput = jQueryMap.$template.render(templateData);
	  demoApp.util.log( "htmlOutput", htmlOutput);
	  jQueryMap.$displayContainer.html(htmlOutput);

	  demoApp.util.log( "done ....................");
	  
	  /*
	  templateShellData.restUrl = "<c:url value='/rest/solarBodies' />";

	  var template = $.templates("#templateShell");

	  */
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
		
  initModule = function( $container, restUrl) {
    
    stateMap.$container = $container;
    stateMap.$displayContainer = $container.find( "#shellContainer");
    stateMap.$template = $.templates( "#templateShell");
    stateMap.restUrl = restUrl;

    setJqueryMap();

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

