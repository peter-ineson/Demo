/* jshint undef: true, unused: true, strict: true */
/* global demoApp.dialog.login */

demoApp.dialog.login = (function( ) {
  'use strict';
  var 
    configMap = {
        CLASS_HIGHLIGHT: 'ui-state-highlight',
        FIELD_ERROR: 'ui-state-error',
        ERROR_TEXT: 'ui-state-error-text',
        DEFAULT_MESSAGE: 'Enter your username and password.'
    },
    stateMap = {
        $container: undefined,
        $dialog: undefined,
        $form: undefined
    },
    jQueryMap = {},
    setJqueryMap,
    initModule,
    openDialog,
    closeDialog,
    updateTips, resetForm, checkLength, checkMadatory, loginUser
    ;

  // ---- DOM methods
  setJqueryMap = function() {
    var
      $container = stateMap.$container,
      $dialog = stateMap.$dialog,
      $form = stateMap.$form,
      $username = $form.find( '#loginForm-username' ),
      $password = $form.find( '#loginForm-password' );
	  
    jQueryMap = {
      $container : $container,
      $dialog : $dialog,
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
    
  openDialog = function() {
    resetForm();
    jQueryMap.$dialog.dialog( "open" );
  }
      
    
  // ---- Init
  initModule = function( $container) {
    var data_mode_str;
    
    stateMap.$dialog = $container.dialog({
        autoOpen: false,
        height: 250,
        width: 400,
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

      stateMap.$form = $container.find( "form" ).on( "submit", function( event ) {
        event.preventDefault();
        loginUser();
      });

      stateMap.$container = $container;
      
	  setJqueryMap();
		  
  };
    
  return {
      initModule : initModule,
      open : openDialog,
      close : closeDialog
  }
}());


