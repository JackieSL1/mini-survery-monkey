Feature: create survey
    Scenario: the client updates the survey title
        Given the client has a survey with id 1
        When the client updates the title of survey 1 to "Cucumbers vs. Bananas"
        And the client calls "/home"
        Then a survey with title "Cucumbers vs. Bananas" is displayed on homepage