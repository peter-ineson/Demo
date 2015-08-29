/* jshint undef: true, unused: true, strict: true */
/* global demoApp.dialog.viewPlanet */

demoApp.dialog.solarBody = (function( ) {
  'use strict';
  var 
    configMap = {
        restUrl: undefined,  
        CLASS_HIGHLIGHT: 'ui-state-highlight',
        FIELD_ERROR: 'ui-state-error',
        ERROR_TEXT: 'ui-state-error-text',
        DEFAULT_MESSAGE: 'Enter your username and password.'
    },
    stateMap = {
        $container: undefined,
        $dialogContainer: undefined,
        $dialog: undefined,
        $template: undefined,
        $form: undefined,
        model: undefined
    },
    jQueryMap = {},
    setJqueryMap,
    initModule,
    openDialog,
    closeDialog,
    updateTips, populateForm, resetForm, checkLength, checkMadatory, loginUser
    ;

  // ---- DOM methods
  setJqueryMap = function() {
    var
      $container = stateMap.$container,
      $dialogContainer = stateMap.$dialogContainer,
      $dialog = stateMap.$dialog,
      $template = stateMap.$template,
      $form = stateMap.$form,
      $username = $form.find( '#username' ),
      $password = $form.find( '#password' );
	  
    jQueryMap = {
      $container : $container,
      $dialogContainer : $dialogContainer,
      $dialog : $dialog,
      $template : $template,
      $form : $form,
      $username: $username,
      $password: $password,
      $tips: $container.find( '.validateTips'),
      $allFields: $( [] ).add( $username ).add( $password )
    };
  };

  updateTips = function( t ) {
	jQueryMap.$tips
	  .text( t )
	  .addClass( configMap.CLASS_HIGHLIGHT )
	  .addClass( configMap.ERROR_TEXT );
	setTimeout(function() {
	    jQueryMap.$tips.removeClass( configMap.CLASS_HIGHLIGHT, 2000 );
 	  }, 750 );
  }

  populateForm = function( solarBodyId, editMode) {
	  var solarBodyDao = demoApp.model.solarBody.get_by_id(solarBodyId);
	  demoApp.util.log( "solarBodyDao count: ", solarBodyDao.count());
	  if( solarBodyDao.count() === 0) {
		  alert( "Error: Failed to find a solar body with an id of " + solarBodyId);
		  return;
	  }
	  var solarBody = solarBodyDao.first();
	  demoApp.util.log( "solarBody", solarBody);
	  
      stateMap.model = new Object();
      $.extend( stateMap.model, solarBody);
      stateMap.model.editMode = editMode;
      stateMap.model.restUrl = configMap.restUrl;	  
	  jQueryMap.$dialog.dialog( "option", "title", (editMode ? "Edit details of " : "View details for ") + solarBody.name);

	  demoApp.util.log( "model", stateMap.model);
/*	  
	  var template = $.templates("#theTmpl");
	  template.link("#result", data);

	  var htmlOutput = jQueryMap.$template.link( stateMap.model);

*/
	  var htmlOutput = jQueryMap.$template.link( "#solarBody-dialog", stateMap.model);
	  
	  /*
	  demoApp.util.log( "htmlOutput", htmlOutput);
	  jQueryMap.$dialogContainer.html( htmlOutput);
*/
	  
	  return;
  }

  resetForm = function() {
    jQueryMap.$tips
      .text( configMap.DEFAULT_MESSAGE )
      .removeClass( configMap.CLASS_HIGHLIGHT)
      .removeClass( configMap.ERROR_TEXT);
    jQueryMap.$username.val("");
    jQueryMap.$password.val("");
    jQueryMap.$allFields.removeClass( configMap.FIELD_ERROR );
  }

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

  loginUser = function() {
    var valid = true;

    jQueryMap.$allFields.removeClass( configMap.FIELD_ERROR );

    valid = valid && checkMadatory( jQueryMap.$username, "Username");
    valid = valid && checkMadatory( jQueryMap.$password, "Password");

    if ( valid ) {
      $.blockUI({ message: '<h1>Logging in...</h1>' }); 

      var datastring = jQueryMap.$form.serialize();
      $.ajax({
        type: "POST",
        dataType: "text",
        url: "app/login",
        data: datastring,
        success: function(data) {
          if( data == "") {
            location.reload();
          } else {
        	$.unblockUI();  
            updateTips( data);
          }
        },
      	error: function(){
      	  alert('ToDo: error handing here');
        }
      });          	
    }

    return valid;
  }

    
  // --- Public methods.    
  closeDialog = function() {
  	jQueryMap.$dialog.dialog( "close" );
  }
    
  openDialog = function( solarBodyId, editMode) {
	if( editMode == undefined) {
		editMode = false;
	}
    populateForm( solarBodyId, editMode);
    jQueryMap.$dialog.dialog( "open" );
  }
      
    
  // ---- Init
  initModule = function( $container, restUrl) {
    var data_mode_str;

    stateMap.$dialogContainer = $container.find( "#solarBody-dialog");

    var $dialogDom = $container.find(  );

    stateMap.$dialog = stateMap.$dialogContainer.dialog({
        autoOpen: false,
        height: 450,
        width: 600,
        modal: true,
        buttons: {
          "Login": {
          	text: "Login",
          	id: "login-dialog-button-login", 
          	click: loginUser
          },
          Cancel: function() {
        	  closeDialog();
          }
        },
        close: function() {
          resetForm();
        }
      });

      stateMap.$form = $dialogDom.find( "form" ).on( "submit", function( event ) {
        event.preventDefault();
        loginUser();
      });

      stateMap.$container = $container;
      stateMap.$template = $.templates( "#templateSolarBodyDialog");
      configMap.restUrl = restUrl;
      
	  setJqueryMap();
		  
  };
    
  return {
      initModule : initModule,
      open : openDialog,
      close : closeDialog
  }
}());


