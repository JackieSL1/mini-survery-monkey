Scenario: the user wants to go to Banana endpoint
Given the server is running
When the user accesses the "/banana" endpoint
Then the response should be "Banana."