@Authentication
Feature: Authentication
  In order to see the correct landing page
  As a user
  I want to be able to login and logout of web site

  @Authentication_defaultUser
  Scenario: The default user is a guest and does not have edit permission 
    Given user accesses the web site
    When does not login
    Then should be guest user
    And the main menu does not have a Edit option
    And the main menu does have a Login option
    And the main menu does not have a Logout option

  @Authentication_adminLogin
  Scenario: Login as an admin user then the user has edit permission 
    Given user accesses the web site
    When login as admin user with correct password
    Then should be admin user
    And the main menu does have a Edit option
    And the main menu does not have a Login option
    And the main menu does have a Logout option

  @Authentication_adminLogout
  Scenario: When an admin user logs out they return to a guest user 
    Given logged in as admin user
    When logout
    Then should be guest user
    And the main menu does not have a Edit option
    And the main menu does have a Login option
    And the main menu does not have a Logout option

  @Authentication_noPassword
  Scenario: Login as an admin user without a password then an error message is displayed 
    Given user accesses the web site
    When login as admin user with no password
    Then the login dialog displays an error message

  @Authentication_incorrectPassword
  Scenario: Login as an admin user with an incorrect password then an error message is displayed 
    Given user accesses the web site
    When login as admin user with incorrect password
    Then the login dialog displays an error message
    