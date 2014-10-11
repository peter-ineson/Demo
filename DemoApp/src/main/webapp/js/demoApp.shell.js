/*jslint         browser : true
  devel  : true, indent : 2,    maxerr   : 50,
  newcap : true, nomem  : true, plusplus : true,
  regexp : true, sloppy : true, vars     : true,
  white  : true
*/
demoApp.shell = (function( ) {
  'use strict';
  var 
    configMap = {
  	  anchor_schema_map : { 
	    	chat : { opened : true, closed : true}
 	    },
      resize_interval: 200,
	  
		  main_html: String()  
			+ '<div class="spa-shell-head">'
	        + '  <div class="spa-shell-head-logo">'
	        + '    <h1>SPA</h1>'
	        + '    <p>javascipt end to end</p>'
	        + '  </div>'
	        + '  <div class="spa-shell-head-acct"></div>'
	        + '</div>'
	        + '<div class="spa-shell-main">'
	        + '  <div class="spa-shell-main-nav"></div>'
	        + '  <div class="spa-shell-main-content"></div>'
	        + '</div>'
	        + '<div class="spa-shell-foot"></div>'
	        + '<div class="spa-shell-modal"></div>',
	      chat_extend_time: 300,
	      chat_retract_time: 100,
	      chat_extend_height: 350,
	      chat_retract_height: 15,
	      chat_extend_title: 'Click to retract',
	      chat_retract_title: 'Click to extend'
	    },
      stateMap = {
          $container: undefined,
          resize_toid: undefined
        },
	    jQueryMap = {},
	    
	    copyAnchorMap, setJqueryMap,
	    changeAnchorPart, onHashchange, onResize,
	    onTapAcct, onLogin, onLogout,
	    setChatAnchor, initModule;
	  
	    // ----  Utility functions
	    copyAnchorMap = function() {
	    	return $.extend( true, {}, stateMap.anchor_map);
	    };

	    // ---- DOM methods
	    setJqueryMap = function() {
	      var $container = stateMap.$container;
	      jQueryMap = {
	        $container : $container
//	        ,
//	        $acct: $container.find( '.spa-shell-head-acct'),
//          $nav: $container.find( '.spa-shell-main-nav')
	      };
	    };

		
		
		onHashchange = function( event) {
          var
			anchor_map_preposed,
			_s_chat_previous,
			_s_chat_preposed,
			s_chat_preposed,
			is_ok = true,
          anchor_map_previous = copyAnchorMap();
			
          try {
        	  anchor_map_preposed = $.uriAnchor.makeAnchorMap();
          } catch (error) {
            $.uriAnchor.setAnchor( anchor_map_previous, null, true);
            return false;
          }
		  stateMap.anchor_map = anchor_map_preposed;
		  
		  _s_chat_previous = anchor_map_previous._s_chat;
		  _s_chat_preposed = anchor_map_preposed._s_chat;
		  
		  if ( ! anchor_map_previous || _s_chat_previous !== _s_chat_preposed) {
			  s_chat_preposed = anchor_map_preposed.chat;
			  switch (s_chat_preposed) {
				case 'opened':
					is_ok = spa.chat.setSliderPosition( 'opened');
					break;

				case 'closed':
          is_ok = spa.chat.setSliderPosition( 'closed');
					break;
					
				default:
            spa.chat.setSliderPosition( 'closed');
				    delete anchor_map_preposed.chat;
		            $.uriAnchor.setAnchor( anchor_map_preposed, null, true);
					break;
             }
		  }
		  
		  if( ! is_ok) {
		    if ( anchor_map_preposed ) {
		      $.uriAnchor.setAnchor( anchor_map_previous, null, true);
		      stateMap.anchor_map = anchor_map_previous;
		    } else {
		      delete anchor_map_preposed.chat;
		      $.uriAnchor.setAnchor( anchor_map_preposed, null, true);
		    }
		  }
		  return false;
		};
		
		onResize = function() {

      if( stateMap.resize_toid) { return true; }
      
      spa.chat.handleResize();
      stateMap.resize_toid = setTimeout(
          function() { stateMap.resize_toid = undefined; },
          configMap.resize_interval);
      return true;
    };

    onTapAcct = function( event) {
      var acct_text,
        user_name,
        user = spa.model.people.get_user();
      
      if( user.get_is_anon()) {
        user_name = prompt( 'Please sign-in');
        spa.model.people.login( user_name);
        jQueryMap.$acct.text( '... processing ...');
      } else {
        spa.model.people.logout();
      }
      return false;
    };
    
    onLogin = function( event, login_user) {
      jQueryMap.$acct.text( login_user.name);
    };

    onLogout = function( event, logout_user) {
      jQueryMap.$acct.text( 'Please sign-in');
    };

		setChatAnchor = function ( position_type) {
      return changeAnchorPart( { chat: position_type});
    };
    // ---- Init
		
  initModule = function( $container) {
    var data_mode_str;
    
    //data_mode_str = window.location.search === '?fake' ? 'fake' : 'live';
    //data_mode_str = 'fake';
    //spa.model.setDataMode( data_mode_str);
    
	  stateMap.$container = $container;
	  $container.html( configMap.main_html);
	  setJqueryMap();
		  
	  stateMap.is_chat_retracted = true;
		  
    $.uriAnchor.configModule( {schema_map : configMap.anchor_schema_map});
    
    spa.chat.configModule({
      set_chat_anchor : setChatAnchor,
      chat_model: spa.model.chat,
      people_model: spa.model.people
    });
    spa.chat.initModule( jQueryMap.$container);
    
    spa.avtr.configModule( {
      chat_model: spa.model.chat,
      people_model: spa.model.people
    });
    spa.avtr.initModule( jQueryMap.$nav );
    
    $.gevent.subscribe( $container, 'spa-login', onLogin);
    $.gevent.subscribe( $container, 'spa-logout', onLogout);

    jQueryMap.$acct
      .text( 'Please sign-in')
      .bind( 'utap', onTapAcct);
      
    $(window)
      .bind( 'resize', onResize)
    	.bind( 'hashchange', onHashchange)
      .trigger( 'hashchange');
  };
    
  return { initModule : initModule };
}());


