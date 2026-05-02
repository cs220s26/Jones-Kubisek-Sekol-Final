# DevOps Final Project
### Joshua Jones, Lilian Kubisek, Nora Sekol

## Overview
Jeopardy Bot is a discord-based application which mimics a single round of the TV show Jeopardy, where players answer trivia facts in the form of a question (the 'facts' will be referred to as the question now on). The game consists of 4 categories, each with 4 questions that correspond to various price point values. Players take turns selecting questions to answer, and depending on their answer, the points get added or subtracted to their overall score. After all questions are answered (or when a player decides to terminate the game early), the game ends, and each player is scored according to how many points they have.
Previously we have run this project from IntelliJ by clicking the run button--the goal of this project is to execute it in new, more efficient and more continuous ways.
## Development Setup/Execution
<note: this section needs more detail>
To launch the bot, firstly you will need to add your discord token to AWS Secrets Manager. Then you must set up your AWS ec2 instance and allow access to secretes managr (located in the second dropdown under Advanced Details). Then paste the entire userdata.sh file into the user data box (located at the very bottom of the Advanced Details section). 
## Production Setup/Execution
(tba)
## CI/CD Setup
#### Setting up CI:
To set up Continuous Integration in our project, we used Github Actions workflows. We made two workflows in total: one to execute the bot's JUnit tests, and the other to run Maven's Checkstyle.
To replicate this setup, go to the repo on Github and click the `Actions` tab. This will bring you to a view of all workflows--if you have none, click `set up a workflow yourself`; if you have a few workflows already, click the green `New Workflows` button. You will be brought to a .yml file editor.
The first line of each file is `name: <name of workflow>`. The next, lines 3-7 (viewable in the `.github/workflows` folder), designate that `on:` each `push:` to the `branches:` that are named `- main`, this workflow will execute. For optional manual execution, `workflow_dispatch:` has been added at the same indentation level as `push:`, as both are triggers for execution.
The next section of the file is `jobs:`; both .yml files' jobs are named based on their corresponding purpose and run on `ubuntu_latest`. To designate the commands executed in this workflow, we have `steps:` at the same indentation level as `runs-on:`, with the subsequent steps indented once from there: the first step must be running the latest version of `Checkout`, which fetches the most recent commit so that the workflow can access it. From there, the two workflows diverge: `checkstyleworkflow.yml` contains the line `- run: mvn checkstyle:check` to execute that line of code and run checkstyle, whereas `junit.yml` contains an additional section for execution: before running `mvn test` (a command that executes the bot's JUnit files), it first ensures that the JDK is at version 21, as this is what is specified in our `pom.xml` file and is thus compatible.

###Setting up CD:

## CI/CD Execution
Continuous Integration can be executed in two different ways:
1. `push` to the repo on the `main` branch (automatically executes both workflows). Beside the commit message on Github there will either be a green checkmark or a red x, displaying the rate of succcess.
2. Go to the `Actions` tab of the repo on Github, selecting the workflow you wish to execute, and clicking the `Run workflow` button on the workflow page.

Continuous Deployment: 
1. 

## Technologies Used
- Discord
- git
- AWS Academy Learner Lab
- Redis
## Background
(tba)
