/*jslint         browser: true,
  devel  : true, indent : 2,    maxerr   : 50,
  newcap : true, nomem  : true, plusplus : true,
  regexp : true, sloppy : true, vars     : true,
  white  : true
*/
/* global $, spa:true */
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

  var initModule = function ($container) {
    //demoApp.data.initModule();
    //demoApp.model.initModule();
    //if( demoApp.shell && $container) {
    //  demoApp.shell.initModule($container);
    //}
    demoApp.dialog.initModule();
    if( demoApp.dialog.login ) {
      demoApp.dialog.login.initModule( $( "#login-dialog" ) );
    }
    demoApp.model.initModule();
    demoApp.data.initModule();

    demoApp.data.loadPlanets();

  };
    
  return {
	  initModule : initModule,
	  logout : logout
  };
}());


