/* jshint undef: true, unused: true, strict: true */
/* global demoApp.dialog */

demoApp.dialog = (function( ) {
  'use strict';
  var 
    configMap = {
        DEFAULT_MESSAGE: 'Enter your username and password.'
    },
    stateMap = {
        $container: undefined,
    },
    jQueryMap = {},
    setJqueryMap,
    initModule
    ;

  // ---- DOM methods
  setJqueryMap = function() {
    var
      $container = stateMap.$container,
	  
    jQueryMap = {
      $container : $container
    };
  };

/*
  checkLength = function ( o, n, min, max ) {
    if ( o.val().length > max || o.val().length < min ) {
      o.addClass( configMap.FIELD_ERROR );
      updateTips( "Length of " + n + " must be between " + min + " and " + max + "." );
      return false;
    } else {
      return true;
    }
  }

  checkMadatory = function( o, n) {
    if ( o.val().length < 1) {
      o.addClass( configMap.FIELD_ERROR );
      updateTips( n + " is required");
      return false;
    } else {
      return true;
    }
  }
*/
    
  // --- Public methods.    
    
  // ---- Init
  initModule = function() {
    
    stateMap.$container = $( [] );

    setJqueryMap();
		  
  };
    
  return {
      initModule : initModule
  }
}());


