<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
  <head>
    <title>Getting Started: Serving Web Content</title> 
    <script src="webjars/jquery/2.1.1/jquery.js"></script>
    <script type="text/javascript">
        $(document).ready(function() {
            $('p').animate({
                fontSize: '48px'
            }, "slow");
        });
    </script>
</head>
  </head>
  <body>
    <p>Yeah!!! the application jsp - ${firstGo}</p>
  </body>
</html>