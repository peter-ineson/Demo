<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<script id="templateShell" type="text/x-jsrender">
  <div>

    <img src="{{:restUrl}}/{{:sun.id}}/image" alt="Sun" height="100" width="100"> 
    {{for planets ~url=restUrl}}
      <img src="{{:~url}}/{{:id}}/image" alt="{{>name}}" height="50" width="50"> 
    {{/for}}
  </div>
</script>
