Narrative:
Meta:
@viewPlanetDetails
In order learn about about planets in the solar system
As a guest user
I want to view information about a planet

Scenario: View details navigating via the menu

Given open landing page as guest
When select View->Mars from the menu
Then planet view page for Mars is displayed 

Scenario: View details navigating via the home page

Given open landing page as guest
When clicking on Mars icon on the home page
Then planet view page for Mars is displayed 
