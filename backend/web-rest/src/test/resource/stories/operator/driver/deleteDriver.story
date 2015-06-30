Scenario:  As the operator I want to delete a driver in the system
 
Given I am in the driver details page
When I try to delete a driver from the system
Then Driver should be soft deleted from the system
And The Application should display a message saying that the operation was successful
And The driver will be visible for reporting purposes
And The driver could not be used for anything