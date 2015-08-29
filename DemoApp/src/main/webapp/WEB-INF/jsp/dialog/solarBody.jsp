
<div id="solarBody-dialog" title="Solar Body" class="hidden">
  
</div>
<script id="templateSolarBodyDialog" type="text/x-jsrender">
  <div class="dataForm">
    {{if editMode}}
      <p class="validateTips">Enter your username and password.</p>
    {{/if}}
    <form id="solarBody-form" name="solarBody-form" >
      <div style="display: inline-block;">
        <div style="display: inline-block;">
          <img src="{{:restUrl}}/{{:id}}/image" alt="{{>name}}" title="{{>name}}"
               width="200px" height="200px" >
        </div>
        <div style="display: inline-block; vertical-align: top;">
          <div>
            <label for="xabc" style="float: left; font-family: 'Orbitron', sans-serif;" >Name</label>
            <input {{if editMode}}disabled{{/if}}
                  style="font-family: 'Orbitron', sans-serif;" id="xabc" data-link="name" />
          </div>
          <div>
            <label 
                   for="xabd" style="float: left; font-family: 'Lato', sans-serif;" >Radius</label>
            <input {{if editMode != true}}disabled{{/if}}
                   style="font-family: 'Lato', sans-serif;" id="xabd" data-link="radius" />
          </div>
          <div>
            <label for="xabe" style="float: left; font-family: 'Josefin Sans', sans-serif;" >Mass</label>
            <input style="font-family: 'Josefin Sans', sans-serif;" id="xabe" data-link="mass" />
          </div>
          <div>
            <label for="xabf" style="float: left; font-family: 'Nunito', sans-serif;" >Orbit distance</label>
            <input style="font-family: 'Nunito', sans-serif;" id="xabf" data-link="orbitDistance" />
          </div>
          <div>
            <label for="xabg" style="float: left; font-family: 'Lato', sans-serif;" >Orbit distance</label>
            <input style="font-family: 'Lato', sans-serif;" id="xabg" data-link="orbitDistance" />
          </div>
          <!-- Allow form submission with keyboard without duplicating the dialog button -->
          <input type="submit" tabindex="-1" style="position:absolute; top:-1000px">
        </div>
      </div>
      <div>
        <label for="xabx" style="font-family: 'Play', sans-serif;" >Description</label>
        <textarea {{if editMode != true}}disabled{{/if}} style="font-family: 'Play', sans-serif;" id="xabx" data-link="description" rows="4" cols="80" ></textarea>
      </div>
      <div>
        <label for="xaby" style="font-family: 'Play', sans-serif;" >Description</label>
        <textarea {{if editMode != true}}readonly{{/if}} style="font-family: 'Play', sans-serif;" id="xaby" data-link="description" rows="4" cols="80" ></textarea>
      </div>
    </form>
  </div>
</script>
