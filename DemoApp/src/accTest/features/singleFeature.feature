@SingleFeature
Feature: Single Feature
  In order to see the correct landing page
  As a user
  I want to be able to login and logout of web site

  Scenario: Login as an admin user then the user has edit permission 
    Given user accesses the web site
    When login as admin user with correct password
    Then should be admin user
    And the main menu does have a Edit option
    And the main menu does not have a Login option
    And the main menu does have a Logout option

