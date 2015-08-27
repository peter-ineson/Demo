<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<script id="templateShell" type="text/x-jsrender">
  <img id="sun_{{:sun.id}}" src="{{:restUrl}}/{{:sun.id}}/image" alt="Sun" title="Sun"
       class="displaySun" width="{{:sunWidth}}" height="{{:sunHeight}}" >
  <div class="planetContainer" >
    {{for planets ~url=restUrl}}
      <img id="planet_{{:planet.id}}" src="{{:~url}}/{{:planet.id}}/image" alt="{{>planet.name}}" title="{{>planet.name}}"
           width="{{:width}}" height="{{:height}}"
           class="displayPlanet" style="top:{{:top}}px; left: {{:orbitDistance}}%;" > 
    {{/for}}
   </div>
</script>
