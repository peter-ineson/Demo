<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
  <head>
    <title>Demo Application</title> 
    
    <link rel="stylesheet" href='<c:url value="/webjars/jquery-ui-themes/1.11.0/vader/jquery-ui.min.css" />' >

    <link rel="stylesheet" href='<c:url value="/css/demoApp.css" />' >
    <link rel="stylesheet" href='<c:url value="/css/cssMenu.css" />' >
    
    <script src='<c:url value="/webjars/jquery/2.1.1/jquery.min.js" />'></script>
    <script src='<c:url value="/webjars/jquery-ui/1.11.1/jquery-ui.min.js" />'></script>
    <script src='<c:url value="/webjars/jquery-blockui/2.65/jquery.blockUI.js" />'></script>
     

    <style>
	    body { font-size: 62.5%; }
	    label, input { display:block; }
	    input.text { margin-bottom:12px; width:95%; padding: .4em; }
	    fieldset { padding:0; border:0; margin-top:25px; }
	    h1 { font-size: 1.2em; margin: .6em 0; }
/*
	    div#users-contain { width: 350px; margin: 20px 0; }
	    div#users-contain table { margin: 1em 0; border-collapse: collapse; width: 100%; }
	    div#users-contain table td, div#users-contain table th { border: 1px solid #eee; padding: .6em 10px; text-align: left; }
	    */
	    .ui-dialog .ui-state-error { padding: .3em; }
      .validateTips { border: 1px solid transparent; padding: 0.3em; }

    </style>

    <script src='<c:url value="/js/demoApp.js" />'></script>
    <script src='<c:url value="/js/demoApp.dialog.js" />'></script>
    <c:if test="${security.guest}">
      <script src='<c:url value="/js/demoApp.dialog.login.js" />'></script>
    </c:if>
    <script src='<c:url value="/js/cssMenu.js" />'></script>
     
    <script type="text/javascript">

    
      $(document).ready(function() {
    	  demoApp.initModule();
    	  
        $( "#menuOption_logout" ).on("click", demoApp.logout);
        if( demoApp.dialog.login ) {
          $( "#menuOption_login" ).on( "click", demoApp.dialog.login.open);
        }
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
    
    <c:if test="${security.guest}">
      <jsp:include page="dialog/login.html"/>
    </c:if>

  </body>
</html>