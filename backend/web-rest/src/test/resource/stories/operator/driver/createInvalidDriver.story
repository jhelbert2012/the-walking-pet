Scenario:  As the operator I want to create a new driver in the system
 
Given I am in the driver administration page
When I try to create an invalid driver
Then Driver should not be created in the system
And I should get a validation error message from the system

