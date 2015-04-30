Narrative:
Meta:
@suggestPlanetDetailsUpdate
In order to keep the planet information up to date
As a guest user
I want to inform an admin user changes to the planet data 

Scenario: Submit a suggestion for update to planet data
Meta:
@suggestPlanetDetailsUpdate_submit
Given guest user selects View->Mars on the menu 
When on the planet page submit suggestion for 'update Mars5' data
Then suggestion submitted message is displayed
And admin user receives a suggestion notifcation 
