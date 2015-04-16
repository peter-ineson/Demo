<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
  <head>
    <title>Getting Started: Serving Web Content</title> 
    
    <link rel="stylesheet" href='<c:url value="/webjars/jquery-ui-themes/1.11.0/vader/jquery-ui.min.css" />' >
    
    
    <script src='<c:url value="/webjars/jquery/2.1.1/jquery.min.js" />'></script>
    <script src='<c:url value="/webjars/jquery-ui/1.11.1/jquery-ui.min.js" />'></script>

    <!-- 
    <script src="webjars/jquery-ui-themes/1.11.0/jquery-ui-themes.js"></script>
     -->
     
    <link rel="stylesheet" href='<c:url value="/css/demoApp.css" />' >
    <link rel="stylesheet" href='<c:url value="/css/cssMenu.css" />' >

    <style>
	    body { font-size: 62.5%; }
	    label, input { display:block; }
	    input.text { margin-bottom:12px; width:95%; padding: .4em; }
	    fieldset { padding:0; border:0; margin-top:25px; }
	    h1 { font-size: 1.2em; margin: .6em 0; }
	    div#users-contain { width: 350px; margin: 20px 0; }
	    div#users-contain table { margin: 1em 0; border-collapse: collapse; width: 100%; }
	    div#users-contain table td, div#users-contain table th { border: 1px solid #eee; padding: .6em 10px; text-align: left; }
	    .ui-dialog .ui-state-error { padding: .3em; }
	    .validateTips { border: 1px solid transparent; padding: 0.3em; }

   </style>

   <script src='<c:url value="/js/cssMenu.js" />'></script>
     
    <script type="text/javascript">

      function logoutUser() {
        $.ajax({
            type: "GET",
            url: "app/logout",
            complete: function() {
              location.reload();
            }
        });           
      }
    
      $(document).ready(function() {
        $("#menuOption_logout" ).on("click", logoutUser);
      });
    </script>

  </head>
  <body>
  
		<div id='cssmenu'>
		<ul>
		   <li><a href='#'><span>Home</span></a></li>
		   <li class='active has-sub'><a href='#'><span>View</span></a>
		      <ul>
		         <li><a href='#'><span><c:out value="${sun.name}"/></span></a></li>
		         <c:forEach items="${planets}" var="planet">
               <li><a href='#'><span><c:out value="${planet.name}"/></span></a></li>
		         </c:forEach>
		      </ul>
		   </li>
		   <c:if test="${security.admin}">
	       <li class='active has-sub'><a href='#'><span>Edit</span></a>
	          <ul>
	             <li><a href='#'><span><c:out value="${sun.name}"/></span></a></li>
	             <c:forEach items="${planets}" var="planet">
	               <li><a href='#'><span><c:out value="${planet.name}"/></span></a></li>
	             </c:forEach>
	          </ul>
	       </li>
		   </c:if>
		   <li><a href='#'><span>About</span></a></li>
		   <li class='last'><a href='#'><span>Contact</span></a></li>
		   <li class="position-right">
		     <a id="menuOption_username" href='#'><span id="securityUserName"><c:out value="${security.user.name}"></c:out></span></a>
		      <ul>
		         <c:if test="${security.guest}"><li><a id="menuOption_login" href='#' style="width: 35px;"><span>Login</span></a></li></c:if>
             <c:if test="${not security.guest}"><li><a id="menuOption_logout" href='#' style="width: 35px;"><span>Logout</span></a></li></c:if>
		      </ul>
		   </li>
		</ul>
		</div>
    <div id="menuBackground">
    </div>  

		<div id="login-dialog" title="Login" class="hidden">
		  <p class="validateTips">Enter your username and password.</p>
		  <form id="login-form" name="login">
		    <fieldset>
		      <label for="name">Username</label>
		      <input type="text" name="username" id="username" class="text ui-widget-content ui-corner-all">
		      <label for="password">Password</label>
		      <input type="password" name="password" id="password" class="text ui-widget-content ui-corner-all">
		      <!-- Allow form submission with keyboard without duplicating the dialog button -->
		      <input type="submit" tabindex="-1" style="position:absolute; top:-1000px">
		    </fieldset>
		  </form>
		</div>
		
		<script type="text/javascript">
		
    	$(function() {
        var dialog, form,
          username = $( "#username" ),
          password = $( "#password" ),
          allFields = $( [] ).add( username ).add( password ),

          tips = $( ".validateTips" );

        function updateTips( t ) {
          tips
            .text( t )
            .addClass( "ui-state-highlight" );
          setTimeout(function() {
            tips.removeClass( "ui-state-highlight", 1500 );
          }, 500 );
        }

        function resetForm() {
          tips
            .text( "Enter your username and password." )
            .removeClass( "ui-state-highlight");
          username.val("");
          password.val("");
        }

        function checkLength( o, n, min, max ) {
          if ( o.val().length > max || o.val().length < min ) {
            o.addClass( "ui-state-error" );
            updateTips( "Length of " + n + " must be between " +
              min + " and " + max + "." );
            return false;
          } else {
            return true;
          }
        }

        function checkMadatory( o, n) {
          if ( o.val().length < 1) {
            o.addClass( "ui-state-error" );
            updateTips( n + " is required");
            return false;
          } else {
            return true;
          }
        }

        function loginUser() {
          var valid = true;

          allFields.removeClass( "ui-state-error" );

          valid = valid && checkMadatory( username, "Username");
          valid = valid && checkMadatory( password, "Password");

          if ( valid ) {
          	var datastring = $("#login-form").serialize();
          	$.ajax({
          	            type: "POST",
          	            url: "app/login",
          	            data: datastring,
          	            success: function(data) {
          	            	if( data == "") {
          	            		location.reload();
          	            	} else {
          	                updateTips( data);
          	            	}
          	            },
          	            error: function(){
          	                  alert('error handing here');
          	            }
          	        });          	
          }

          return valid;
        }

        dialog = $( "#login-dialog" ).dialog({
          autoOpen: false,
          height: 300,
          width: 350,
          modal: true,
          buttons: {
            "Login": loginUser,
            Cancel: function() {
              dialog.dialog( "close" );
            }
          },
          close: function() {
            resetForm();
          }
        });

        form = dialog.find( "form" ).on( "submit", function( event ) {
          event.preventDefault();
          loginUser();
        });

    	
        $( "#menuOption_login" ).on( "click", function() {
        	resetForm();
        	dialog.dialog( "open" );
        });
      });

    	
		</script>
		
		:security.admin=${security.admin}:security.guest=${security.guest}:
  </body>
</html>