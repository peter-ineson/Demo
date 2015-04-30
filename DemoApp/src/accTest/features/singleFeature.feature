@SingleFeature
Feature: Single Feature
  In order to see the correct landing page
  As a user
  I want to be able to login and logout of web site

  @Authentication_noPassword
  Scenario: Login as an admin user without a password then an error message is displayed 
    Given user accesses the web site
    When login as admin user with no password
    Then the login dialog displays an error message
