@SingleFeature
Feature: Single Feature
  In order to see the correct landing page
  As a user
  I want to be able to login and logout of web site

  @Authentication_adminLogout
  Scenario: When an admin user logs out they return to a guest user 
    Given logged in as admin user
    When logout
    Then should be guest user
    And the main menu does not have a Edit option
    And the main menu does have a Login option
    And the main menu does not have a Logout option

