Scenario:  As a visitor I want to see the available routes
 
Given I visit agency (id:<agencyId>) website
When I search for the available routes
Then I should get all the available routes
And I should see the name of the route
And I should see the days of the week that run
And I should see the start time of the route

