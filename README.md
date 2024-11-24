<p align="center">
<img src="https://upload.wikimedia.org/wikipedia/commons/9/93/Typing_monkey_768px.png" height="150">
</p>
<h1 align="center">
Mini Survey Monkey

![App Deployed](https://github.com/JackieSL1/mini-survey-monkey/actions/workflows/main_mini-survey-monkey.yml/badge.svg)
  
</h1>

<p align="center">
The world's second most popular survey platform.
<p>

<p align="center">
<a href="https://mini-survey-monkey-eyd6fhfyesf6ezay.canadaeast-01.azurewebsites.net/">🌐 Web App</a> |
<a href="https://mini-survey-monkey-eyd6fhfyesf6ezay.canadaeast-01.azurewebsites.net/banana">🍌 Banana</a>
</p>

## Table of contents

<details>
<summary>Expand contents</summary>

- [Current State](#current-state)
- [Scope](#scope)
- [Use cases](#use-cases)
- [DB schema](#db-schema)
- [UML class Diagram](#uml-class-diagram-for-models)

</details>

## Current State
The project now includes a survey management system with user authentication. 
Users can create accounts using unique usernames and log in to their accounts using their corresponding passwords. 
Once logged in, users are directed to a homepage displaying their surveys with the option to create new surveys. 
Surveys include titles, comments, multiple-choice questions, and scale questions. 
Each survey has a defined state (Draft, Open, or Closed).
When a survey is opened a unique, shareable link is generated for collecting responses. 
The system supports storing comment responses in a database.
The summary page for a survey displays questions and all comment responses. 
The application is deployed on Azure with GitHub Actions handling deployment processes.

In Milestone 3, the project will fully support collecting and storing responses for scale questions and multiple-choice questions in the database. 
The analytics page will be completed to include detailed insights for all question types, displaying results and visualizations for scale and multiple-choice questions.

## Scope
*As stated in the project description.*
* Surveyor can create a survey with a list of Questions.
* Questions can be open-ended (text), asking for a number within a range, or asking to choose among many options.  
* Users fill out a survey that is a form generated based on the type of questions in the survey.
* Surveyor can close the survey whenever they want (thus not letting in new users to fill out the survey), and at that point a survey result is generated, compiling the answers: for open-ended questions, the answers are just listed as-is, for number questions a histogram of the answers is generated, for choice questions a pie chart is generated

## Use cases
<details>
<summary>Expand contents</summary>


### Create account
New user should be able to create an account.

**Steps:**
* On the `login` page, user clicks `Don't have an account? Sign up` button
* User is prompted to give `username` and `password`
* System should fail to create account and prompt user to enter a different `username` if `username` already exists
* System displays confirmation if account is created and directs the user back to the `login` page

### Login
Existing user should be able to log into their account.

**Steps:**
* On the `login` page, user enters `username` and `password`
* System should fail to log in and notify user if `username` doesn't exist
* System should fail to log in and notify user if `password` is incorrect
* User is directed to their `home` page upon successful log in

### View surveys
Users should be able to see all their surveys in one view.

**Steps:**
* On the `home` page, user can view all their surveys and their states

**Considerations:**
* States for a survey can be `DRAFT`, `OPEN` or `CLOSED`

### Create survey
User should be able to create a new survey.

**Steps:**
* On the `home` page, user clicks `create survey` button and are directed to the `create` page
* User then clicks the `done` button to save the survey 

**Considerations:**
* User can view their newly created survey on the `home` page dashboard

### Add title
User should be able to add a title to their survey.

### Add comment box question
User should be able to add a comment box question to their survey

### Add rating question
User should be able to add a question asking for a rating to their survey.

### Add multiple-choice question
User should be able to add a multiple-choice question to their survey.

### Collect responses
User should be able to share their survey and collect responses.
</details>

## DB schema
The following diagram can be updated using the [Diagrams.net Integration IntelliJ Plugin](https://plugins.jetbrains.com/plugin/15635-diagrams-net-integration).

![DB schema](diagrams/db-schema.svg)

## UML class Diagram for Models
The following diagram can be updated by editing the corresponding drawio file in diagrams and then exporting the drawing
to png.

![UML class Diagram for Models](diagrams/ModelUML.drawio.svg)