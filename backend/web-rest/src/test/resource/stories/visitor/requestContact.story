Scenario:  As a visitor I want to request to be contacted to book a trip
 
Given I select an itinerary of route A to go from stop Z to stop Y
When I submit a contact request with my contact details
Then The request is captured by the system and a thank you message is displayed

