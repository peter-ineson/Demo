<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
  <head>
    <title>Demo Application</title> 

    <link href='https://fonts.googleapis.com/css?family=Lato|Josefin+Sans|Orbitron|Nunito|Play' rel='stylesheet' type='text/css'>
    
    <link rel="stylesheet" href='<c:url value="/webjars/jquery-ui-themes/1.11.0/vader/jquery-ui.min.css" />' >

    <link rel="stylesheet" href='<c:url value="/css/demoApp.css" />' >
    <link rel="stylesheet" href='<c:url value="/css/cssMenu.css" />' >
    
    <script src='<c:url value="/webjars/jquery/2.1.1/jquery.min.js" />'></script>
    <script src='<c:url value="/webjars/jquery-ui/1.11.1/jquery-ui.min.js" />'></script>
    <script src='<c:url value="/webjars/jquery-blockui/2.65/jquery.blockUI.js" />'></script>
     
    <script src='<c:url value="/js/lib/jsviews-1.0.0-alpha.min.js" />'></script>
    <script src='<c:url value="/js/lib/taffy-2.7.js" />'></script>
    <script src='<c:url value="/webjars/EventEmitter/4.2.7/EventEmitter.js" />'></script>

    <script src='<c:url value="/js/demoApp.js" />'></script>
    <script src='<c:url value="/js/demoApp.util.js" />'></script>
    <script src='<c:url value="/js/demoApp.model.js" />'></script>
    <script src='<c:url value="/js/demoApp.data.js" />'></script>

    <script src='<c:url value="/js/cssMenu.js" />'></script>
    <script src='<c:url value="/js/demoApp.dialog.js" />'></script>
    <script src='<c:url value="/js/demoApp.shell.js" />'></script>
    <c:if test="${security.guest}">
      <script src='<c:url value="/js/demoApp.dialog.login.js" />'></script>
    </c:if>
    <script src='<c:url value="/js/demoApp.dialog.solarBody.js" />'></script>
     
    <script type="text/javascript">

    function Events(){
        EventEmitter.call(this);
        // custom initialization here
      }
    Events.prototype = Object.create(EventEmitter.prototype);
    //Job.prototype = new EventEmitter;
    events = new Events();
    
      $(document).ready(function() {
    	  
    	  var baseUrl = "<%=request.getContextPath()%>";

    	  demoApp.initModule( $('#demoApp'), baseUrl);
    	  
        $( "#menuOption_logout" ).on("click", demoApp.logout);
        if( demoApp.dialog.login ) {
          $( "#menuOption_login" ).on( "click", demoApp.dialog.login.open);
        }
      });

      </script>

  </head>
  <body>
    <div id="demoApp">
	  <div id='cssmenu'>
		<ul>
		   <li><a href='#'><span>Home</span></a></li>
		   <li class='active has-sub'><a href='#'><span>View</span></a>
		      <ul>
		         <li><a href='#' onclick="demoApp.dialog.solarBody.open( ${sun.id});"><span><c:out value="${sun.name}"/></span></a></li>
		         <c:forEach items="${planets}" var="planet">
               <li><a href='#' onclick="demoApp.dialog.solarBody.open( ${planet.id});"><span><c:out value="${planet.name}"/></span></a></li>
		         </c:forEach>
		      </ul>
		   </li>
		   <c:if test="${security.admin}">
	       <li class='active has-sub'><a href='#'><span>Edit</span></a>
	          <ul>
	             <li><a href='#' onclick="demoApp.dialog.solarBody.open( ${sun.id});"><span><c:out value="${sun.name}"/></span></a></li>
	             <c:forEach items="${planets}" var="planet">
	               <li><a href='#' onclick="demoApp.dialog.solarBody.open( ${planet.id});"><span><c:out value="${planet.name}"/></span></a></li>
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
      <div id="menuBackground"></div>  
    
      <div id="shellContainer"></div>

      <c:if test="${security.guest}">
        <jsp:include page="dialog/login.jsp"/>
      </c:if>

      <jsp:include page="shell.jsp"/>
      <jsp:include page="dialog/solarBody.jsp"/>
      
    </div>  

  </body>
</html>