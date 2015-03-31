Narrative:
In order to see adminstration features
As a admin user
I want to login succesfully

Scenario: Default user is guest

Given open browser
When browser dashboard displayed
Then user is guest
Then menu does not have Edit option
Then menu does not have Logout option
Then menu does have Login option

Scenario: Login as an admin user

Given open dashboard
When login as user admin
Then user is admin
Then menu does have Edit option

Scenario: Login as an admin user and then logout, retunr to the guest user

Given logged in as user admin
When select logout
Then user is guest
Then menu does not have Edit option
