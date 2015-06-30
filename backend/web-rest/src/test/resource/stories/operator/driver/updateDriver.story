Scenario:  As the operator I want to update the information of a driver in the system
 
Given I am in the driver details page
When I try to modify the information of a driver with valid data
Then Driver should be updated in the system
And The Application should display a message saying that the operation was successful



