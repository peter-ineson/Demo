/*jslint         browser: true,
  devel  : true, indent : 2,    maxerr   : 50,
  newcap : true, nomem  : true, plusplus : true,
  regexp : true, sloppy : true, vars     : true,
  white  : true
*/
/* global $, spa:true */
spa = (function () {
  'use strict';
  var initModule = function ($container) {
    //demoApp.data.initModule();
    demoApp.model.initModule();
    if( demoApp.shell && $container) {
      demoApp.shell.initModule($container);
    }
  };
    
  return { initModule : initModule};
}());


