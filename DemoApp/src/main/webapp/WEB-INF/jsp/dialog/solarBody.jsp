
<div id="solarBody-dialog" title="Solar Body" class="hidden">
  
</div>
<script id="templateSolarBodyDialog" type="text/x-jsrender">
  <div class="dataForm">
    {{if editMode}}
      <p class="validateTips">Enter your username and password.</p>
    {{/if}}
    <form id="solarBody-form" name="solarBody-form" >
      <div class="inputFieldBlock">
        <div class="inputFieldBlock">
          <img id="solarBodyForm-solarBodyImage" src="{{:restUrl}}/{{:id}}/image" alt="{{>name}}" title="{{>name}}"
            {{if imageWidth > imageHeight}}width="150px"{{else}}height="150px"{{/if}}
          >
        </div>
        <div class="inputFieldBlock">
          <div class="inputField">
            <label for="solarBodyForm-name" class="width100">Name:</label>
            <input {{if editMode != true}}disabled{{/if}}
                  class="text ui-widget-content ui-corner-all width150"
                  id="solarBodyForm-name" data-link="name" />
          </div>
          <div class="inputField">
            <label for="solarBodyForm-bodyType" class="width100">Type:</label>
            <select class="text ui-widget-content ui-corner-all width150"
                    id="solarBodyForm-bodyType" 
                    data-link="bodyType disabled{:editMode != true}">
              <option value="">Please select</option>
              {^{for BODY_TYPES}}
                <option data-link="value{:id} {:description} selected{:id == ~root.bodyType}"></option>
              {{/for}}
`          </select>
          </div>
          <div class="inputField">
            <label for="solarBodyForm-radius" class="width100">Radius:</label>
            <input {{if editMode != true}}disabled{{/if}}
                  class="text ui-widget-content ui-corner-all width150 number"
                   id="solarBodyForm-radius" data-link="radius" /> km
          </div>
          <div class="inputField">
            <label for="solarBodyForm-mass" class="width100">Mass:</label>
            <input {{if editMode != true}}disabled{{/if}} id="solarBodyForm-mass" data-link="mass"
                   class="text ui-widget-content ui-corner-all width150 number" /> kg
          </div>
          <div class="inputField">
            <label for="solarBodyForm-orbitDistance" class="width100">Orbit distance:</label>
            <input {{if editMode != true}}disabled{{/if}} id="solarBodyForm-orbitDistance" data-link="orbitDistance"
                   class="text ui-widget-content ui-corner-all width150 number" /> km
          </div>
        </div>
      </div>
      <div>
        <p>Description</p>
        <textarea {{if editMode != true}}readonly{{/if}} 
                  class="textarea ui-widget-content ui-corner-all"
                  id="solarBodyForm-description" data-link="description"></textarea>
      </div>
    </form>
  </div>
</script>
