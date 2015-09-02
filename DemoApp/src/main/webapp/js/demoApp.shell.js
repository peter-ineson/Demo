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
      restUrl: undefined, 
      sun_height: 200,
      min_plant_height: 25,
      max_plant_height: 70,
      orbit_distance_start: 14,
      orbit_distance_end: 87
	},
    stateMap = {
        $container: undefined,
        $displayContainer: undefined,
        $template: undefined
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
	    $template : stateMap.$template
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
	  //demoApp.util.log( "sun", sun);
	  
	  var planetDao = dao.get_by_body_type(dao.BODY_TYPE_PLANET);
	  demoApp.util.log( "planets count: ", planetDao.count());
	  if( planetDao.count() === 0) {
		  return;
	  }

	  
	  var planets = planetDao.order( "orbitDistance").get();
	  //demoApp.util.log( "planets", planets);
	  
	  var lastPlanet = planets[ planets.length - 1];
	  var fullWidth = lastPlanet.orbitDistance + lastPlanet.radius;
	  demoApp.util.log( "lastPlanet.orbitDistance " + lastPlanet.orbitDistance
			  + "fullWidth", fullWidth);

	  var planetsData = new Array();
	  var min_planet_radius = -1;
	  var max_planet_radius = -1;

	  for (var i = 0; i < planets.length; i++) {
        var planet = planets[ i];

		if( planet.imageWidth == undefined && planet.imageHeight == undefined) {
          continue;
		}

		if( min_planet_radius == -1 || planet.radius < min_planet_radius) {
			min_planet_radius = planet.radius;
		}

		if( max_planet_radius == -1 || planet.radius > max_planet_radius) {
			max_planet_radius = planet.radius;
		}

		planetData = {
          planet: planet
		};
        planetsData.push( planetData);
	  }

	  var planet_radius_range = max_planet_radius - min_planet_radius;
	  var planet_radius_range = max_planet_radius - min_planet_radius;

	  var planet_height_range = configMap.max_plant_height - configMap.min_plant_height;
      var orbit_distance_gap = (configMap.orbit_distance_end - configMap.orbit_distance_start) / (planetsData.length - 1);  
	  
	  for (var i = 0; i < planetsData.length; i++) {
        var planetData = planetsData[ i];
		var radiusRatio = (planetData.planet.radius - min_planet_radius) / planet_radius_range;

        var height = demoApp.util.toInt( configMap.min_plant_height + (planet_height_range * radiusRatio));
        var top = - ( demoApp.util.toInt( height / 2));
        var width = demoApp.util.toInt((height / planetData.planet.imageHeight) * planetData.planet.imageWidth);
 
        var orbitDistance = demoApp.util.toInt( configMap.orbit_distance_start + (orbit_distance_gap * i) );
        
        planetData.width = width;
        planetData.height = height;
        planetData.top = top;
        planetData.orbitDistance = orbitDistance;

        demoApp.util.log( "planet " + planetData.planet.name
  		    + ", width = " + width
            + ", height = " + height
            + ", top = " + top
            + ", orbitDistance = " + orbitDistance);
	  }
	  
	  var sunWidth = demoApp.util.toInt( configMap.sun_height * (sun.imageWidth / sun.imageHeight));

      demoApp.util.log( "sun, width = " + sunWidth
              + ", height = " + configMap.sun_height
              + ", sun.imageWidth = " + sun.imageWidth
              + ", sun.imageHeight = " + sun.imageHeight);

      var templateData = {
	    restUrl: configMap.restUrl,
	    sun: sun,
	    sunWidth: sunWidth,
	    sunHeight: configMap.sun_height,
	    planets: planetsData
	  };

	  var htmlOutput = jQueryMap.$template.render(templateData);
	  //demoApp.util.log( "htmlOutput", htmlOutput);
	  jQueryMap.$displayContainer.html(htmlOutput);
	};	

  // ---- Init
		
  initModule = function( $container, restUrl) {
    
    stateMap.$container = $container;
    stateMap.$displayContainer = $container.find( "#shellContainer");
    stateMap.$template = $.templates( "#templateShell");
    configMap.restUrl = restUrl;

    setJqueryMap();

    events.on('onPlanetsLoaded', displaySolarSystem);
  };
    
  return {
	  initModule : initModule
  };
}());

