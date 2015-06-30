Scenario:  As the operator I want to create a new driver in the system
 
Given I am in the driver administration page
When I try to create a valid driver
Then Driver should be created in the system
And The Application should display a message saying that the operation was successful



