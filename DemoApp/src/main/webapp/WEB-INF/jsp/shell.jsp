<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<script id="templateShell" type="text/x-jsrender">
  <img id="icon_solayBody_{{:sun.id}}" src="{{:restUrl}}/{{:sun.id}}/image" alt="Sun" title="Sun"
       class="displaySun" onclick="demoApp.dialog.solarBody.open( {{:sun.id}});"
   >
  <div class="planetContainer">
    {{for planets ~url=restUrl}}
      <img id="icon_solarBody_{{:planet.id}}" src="{{:~url}}/{{:planet.id}}/image" alt="{{>planet.name}}" title="{{>planet.name}}"
           height="{{:height}}"
           class="displayPlanet" style="top:{{:top}}px; left: {{:orbitDistance}}%;"
           onclick="demoApp.dialog.solarBody.open( {{:planet.id}});"
      >
    {{/for}}
   </div>
</script>
