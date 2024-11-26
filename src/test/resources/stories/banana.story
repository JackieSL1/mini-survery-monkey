Meta:

Scenario: Banana Test
Given the server is running
When I access the "/banana" endpoint
Then the response should be "Banana."