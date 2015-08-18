/*jslint         browser : true,
  devel  : true, indent : 2,    maxerr   : 50,
  newcap : true, nomem  : true, plusplus : true,
  regexp : true, sloppy : true, vars     : true,
  white  : true
*/
demoApp.model = (function () {
  'use strict';
  var
    configMap = { guest_id: 'guest'},
    stateMap = {
      guest_user: null,
      solar_body_db: TAFFY(),
      user: null,
      is_connected: false
    },
    isFakeData = false,
    
    solarBodyProto, makeSolarBody, removePerson, solarBody, initModule,
    setDataMode;
  
  solarBodyProto = {
    get_is_planet: function() {
      return this.bodyType === 'planet';
    }
  };
  
  
  makeSolarBody = function( solarBody_map) {
    var solarBody,
      id = solarBody_map.id,
      name = solarBody_map.name;
    
    solarBody = Object.create( solarBodyProto);
    solarBody.id = id;
    solarBody.name = name;
    solarBody.bodyType = solarBody_map.bodyType;
    solarBody.description = solarBody_map.description;
    solarBody.orbitBodyId = solarBody_map.orbitBodyId;
    solarBody.orbitDistance = solarBody_map.orbitDistance;
    solarBody.radius = solarBody_map.radius;
    solarBody.mass = solarBody_map.mass;
    
    stateMap.solar_body_db.insert( solarBody);
    return solarBody;
  };

  /*
  removePerson = function( person) {
    if( ! person) { return false; }
    if( person.id === configMap.anon_id) { return false; }
    
    stateMap.people_db( {cid: person.cid}).remove();
    if( person.cid) {
      delete stateMap.people_cid_map[ person.cid];
    }
    return true;
  };
  */
  
  solarBody = (function() {
    var get_by_id, get_db, get_by_body_type,
      BODY_TYPE_SUN = "SUN",
      BODY_TYPE_PLANET = "PLANET"
    ;
    
    get_by_id = function( byId) {
      return stateMap.solar_body_db({ id: byId});
    };

    get_by_body_type = function( byBodyType) {
  	  demoApp.util.log( "by: " + byBodyType);

        return stateMap.solar_body_db({ bodyType: byBodyType});
    };

    get_db = function() { return stateMap.solar_body_db; };

   
    return {
      get_by_id: get_by_id,
      get_by_body_type: get_by_body_type,
      get_db: get_db,
      
      BODY_TYPE_SUN: BODY_TYPE_SUN,
      BODY_TYPE_PLANET: BODY_TYPE_PLANET
    };
  }());

  initModule = function() {
    var i, solar_body_list;
  };

  setDataMode = function( arg_str ) {
    isFakeData = arg_str === 'fake' ? true : false;
  };
  
  return{
    initModule: initModule,
    makeSolarBody: makeSolarBody,
    solarBody: solarBody,
    setDataMode: setDataMode
  };
}());


