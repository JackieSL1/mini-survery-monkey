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

- [Description](#description)
- [Deployment](#deployment)
- [UML class Diagram](#uml-class-diagram-for-models)
- [DB schema](#db-schema)

</details>

## Description
As defined in the project requirements, Mini Survey Monkey is a survey management system where:
* Surveyors can create a survey with a list of questions.
* Questions can be open-ended (text), asking for a number within a range, or asking to choose among many options.
* Users fill out a survey that is a form generated based on the type of questions in the survey.
* Surveyor can close the survey whenever they want (thus not letting in new users to fill out the survey)
* Generate survey results, compiling the answers: for open-ended questions, the answers are just listed as-is, for number questions a histogram of the answers is generated, for choice questions a pie chart is generated

Additionally, users can:
* Create accounts using unique usernames and log in to their accounts using their corresponding passwords.
* Share surveys with a unique, generated link for collecting responses.

## Deployment
The application is deployed on Azure with GitHub Actions handling deployment processes.

## UML class Diagram for Models
The following diagram can be updated by editing the corresponding drawio file in diagrams and then exporting the drawing
to png.

![UML class Diagram for Models](diagrams/models-class-diagrams-final.png)

## DB schema
The following diagram can be updated using the [Diagrams.net Integration IntelliJ Plugin](https://plugins.jetbrains.com/plugin/15635-diagrams-net-integration).

![DB schema](diagrams/db-schema-milestone-2.png)

