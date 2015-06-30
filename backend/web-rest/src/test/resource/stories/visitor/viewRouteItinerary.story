Scenario:  As a visitor I want to see the itinerary of a route
 
Given I visit agency (id:<agencyId>) website
When I request the itineraries of a route (by default takes from: today to: 1 month ahead)
Then I should see the date and start time of each itinerary
And I should see the stops of the itinerary in order
And I should see the approximate arrival time for each stop of each itinerary
And I should see the seat availability of each itinerary

