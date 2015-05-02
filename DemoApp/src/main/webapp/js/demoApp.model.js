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
      cid_serial: 0,
      solar_body_cid_map: {},
      solar_body_db: TAFFY(),
      user: null,
      is_connected: false
    },
    isFakeData = false,
    
    personProto, makeCid, completeLogin,
    makePerson, removePerson, people, initModule,
    setDataMode;
  
  personProto = {
    get_is_user: function() {
      return this.cid === stateMap.user.cid;
    },
    get_is_guest: function() {
      return this.cid === stateMap.guest_user.cid;
    }
  };
  
  makeCid = function() {
    return 'c' + stateMap.cid_serial++;
  };
  
  completeLogin = function( user_list) {
    console.log( "completeLogin");
    var user_map = user_list[ 0];
    delete stateMap.people_cid_map[ user_map.cid];
    
    stateMap.user.cid = user_map._id;
    stateMap.user.id = user_map._id;
    stateMap.user.css_map = user_map.css_map;
    stateMap.people_cid_map[ user_map._id] = stateMap.user;
    //chat.join();
    //$.gevent.publish( 'spa-login', [ stateMap.user]);
  };

  makePerson = function( person_map) {
    var person,
      cid = person_map.cid,
      css_map = person_map.css_map,
      id = person_map.id,
      name = person_map.name;
    
    if( cid === undefined || ! name) {
      throw 'Client id and name required';
    }
    
    person = Object.create( personProto);
    person.cid = cid;
    person.name = name;
    person.css_map = css_map;
    
    if( id ) { person.id = id; }
    
    stateMap.people_cid_map[ cid] = person;
    stateMap.people_db.insert( person);
    return person;
  };

  removePerson = function( person) {
    if( ! person) { return false; }
    if( person.id === configMap.anon_id) { return false; }
    
    stateMap.people_db( {cid: person.cid}).remove();
    if( person.cid) {
      delete stateMap.people_cid_map[ person.cid];
    }
    return true;
  };

  people = (function() {
    var get_by_cid, get_db, get_user, login, logout;
    
    get_by_cid = function( cid) {
      return stateMap.people_cid_map[ cid];
    };

    get_db = function() { return stateMap.people_db; };

    get_user = function() { return stateMap.user; };
    
    login = function( name) {
      console.log( "isFakeData: " + isFakeData);
      var sio = isFakeData ? spa.fake.mockSio : spa.data.getSio();
      console.log( "sio: " , sio);
      
      stateMap.user = makePerson({
        cid: makeCid(),
        css_map: { top: 25, left: 25, 'background-color': '#8f8'},
        name: name
      });
      
      sio.on( 'userupdate', completeLogin);
      sio.emit( 'adduser', {
        cid: stateMap.user.cid,
        css_map: stateMap.user.css_map,
        name: stateMap.user.name
      });
    };

    logout = function() {
      var user = stateMap.user;
      
      chat._leave();
      stateMap.user = stateMap.anon_user;
      clearPeopleDb();
      
      $.gevent.publish( 'spa-logout', [user]);
    };
   
    return {
      get_by_cid: get_by_cid,
      get_db: get_db,
      get_user: get_user,
      login: login,
      logout: logout
    };
  }());

  
  initModule = function() {
    var i, solar_body_list, person_map;
    stateMap.guest_user = makePerson({
      cid : configMap.anon_id,
      id : configMap.anon_id,
      name: 'Anonymous'
    });
    stateMap.user = stateMap.anon_user;
  };

  setDataMode = function( arg_str ) {
    isFakeData = arg_str === 'fake' ? true : false;
  };
  
  return{
    initModule: initModule,
    chat: chat,
    people: people,
    setDataMode: setDataMode
  };
}());


