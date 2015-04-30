Narrative:
Meta:
@editPlanetDetails
In order to keep the planet information up to date
As a admin user
I want to edit information about a planet

Scenario: Edit details navigating via the menu
Meta:
@editPlanetDetails_editMenu
Given Mars does not have 'update Mars1' data
And admin user selects Edit->Mars on the menu
When enter and save 'update Mars1' data
Then Mars does have 'update Mars1' data 

Scenario: Edit details navigating via the view planet page
Meta:
@editPlanetDetails_editViewPlanet
Given Mars does not have 'update Mars2' data
And admin user selects View->Mars on the menu
And click Edit button on the planet page
When enter and save 'update Mars2' data on the planet page
Then Mars does have 'update Mars2' data 

Scenario: Edit planet information suggested by another user
Meta:
@editPlanetDetails_editSuggestion
Given Mars does not have 'update Mars3' data
And admin user has received update suggestion for 'update Mars3' data 
And click Update button for the suggestion
When enter and save 'update Mars3' data on the planet page
Then Mars does have 'update Mars3' data
And does not have suggestion for 'update Mars3' data

Scenario: Reject a suggested update submitted by another user
Meta:
@editPlanetDetails_suggestionReject
Given Mars does not have 'update Mars4' data
And admin user has received update suggestion for 'update Mars4' data 
When click Reject button for the suggestion
Then Mars does not have 'update Mars4' data 


