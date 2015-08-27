/*jslint         browser: true,
  devel  : true, indent : 2,    maxerr   : 50,
  newcap : true, nomem  : true, plusplus : true,
  regexp : true, sloppy : true, vars     : true,
  white  : true
*/
/* global $, demoApp,true */
demoApp = (function () {
  'use strict';
  var logout;
  
  logout = function () {
      $.blockUI({ message: '<h1>Logging out...</h1>' }); 

      $.ajax({
          type: "POST",
          url: "app/logout",
          complete: function() {
            location.reload();
          }
      });           
    }

  var initModule = function ($container, baseUrl) {
	  
    var restUrl = baseUrl + "/rest";

    demoApp.dialog.initModule();
    if( demoApp.dialog.login ) {
      demoApp.dialog.login.initModule( $container.find( "#login-dialog") );
    }

    demoApp.shell.initModule( $container, restUrl + "/solarBodies");
    demoApp.model.initModule();
    demoApp.data.initModule();

    demoApp.data.loadSun();
    demoApp.data.loadPlanets();

  };
    
  return {
	  initModule : initModule,
	  logout : logout
  };
}());

var debug = true;
