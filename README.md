# DevOps Final Project
### Joshua Jones, Lilian Kubisek, Nora Sekol
![Testing](https://github.com/cs220s26/Jones-Kubisek-Sekol-Final/actions/workflows/checkstyleworkflow.yml/badge.svg)
![Testing](https://github.com/cs220s26/Jones-Kubisek-Sekol-Final/actions/workflows/testsjunit.yml/badge.svg)

## Overview
Jeopardy Bot is a discord-based application which mimics a single round of the TV show Jeopardy, where players answer trivia facts in the form of a question (the 'facts' will be referred to as the question now on). The game consists of 4 categories, each with 4 questions that correspond to various price point values. Players take turns selecting questions to answer, and depending on their answer, the points get added or subtracted to their overall score. After all questions are answered (or when a player decides to terminate the game early), the game ends, and each player is scored according to how many points they have.

Previously we have run this project from IntelliJ by clicking the run button--the goal of this iteration of the project is to execute it in new, more efficient and more continuous ways.

## Development Setup/Execution
To run the bot locally, one must do the following:
1. Launch AWS. On the Learner Lab launch page, click on `AWS Details` and copy the information displayed upon clicking "Show" beside "AWS CLI". Open a new terminal window and paste this into the file `~/.aws/credentials`. You must repeat this action every time you launch AWS.
2. Ensure that Secrets Manager is properly set up--if you are not currently storing your Discord token as a Secret, then go to Secrets Manager on AWS and click "Store New Secret", then "Other type of Secret" and set the key as `DISCORD_TOKEN` and the associated value as your token. Name the Secret `220_Discord_Token` and continue selecting `Next` until it allows you to `Store`.
3. Clone the repository and cd into it with:
```bash
git clone https://github.com/cs220s26/Jones-Kubisek-Sekol-Final.git
cd Jones-Kubisek-Sekol-Final
```
4. Make sure redis is running with `brew services start redis` or alternatively `brew services restart redis` is a redis instance is already active.
5. Build the project through `mvn clean package`.
6. After compiling, run the .jar file: `java -jar target/dbot-1.0.0-jar-with-dependencies.jar` The bot now runs locally!

## Production Setup/Execution
To run the bot on an EC2 instance, one must do the following:
1. Repeat step 1 from Development setup.
2. Repeat step 2 from Development setup.
3. Go to the EC2 page of AWS and click the "Launch Instance" button.
4. Name the instance whatever you'd like, select the `vockey` under "Key pair (login)" (assuming that your `labsuser.pem` has been properly configured), and check `SSH` and `HTTP` under "Network Settings". Under "Advanced Details", select the premade `LabInstanceProfile` from "IAM instance profile" copy and paste the contents of the `userdata.sh` file. Launch the instance.
5. It will take a few moments to load, but congrats! You are now running the bot on an EC2 Instance.

## CI/CD Setup
### Setting up CI:
To set up Continuous Integration in our project, we used Github Actions workflows. We made two workflows in total: one to execute the bot's JUnit tests, and the other to run Maven's Checkstyle.

To replicate this setup, go to the repo on Github and click the `Actions` tab. This will bring you to a view of all workflows--if you have none, click `set up a workflow yourself`; if you have a few workflows already, click the green `New Workflows` button. You will be brought to a .yml file editor.

The first line of each file is `name: <name of workflow>`. The next, lines 3-7 (viewable in the `.github/workflows` folder), designate that `on:` each `push:` to the `branches:` that are named `- main`, this workflow will execute. For optional manual execution, `workflow_dispatch:` has been added at the same indentation level as `push:`, as both are triggers for execution.

The next section of the file is `jobs:`; both .yml files' jobs are named based on their corresponding purpose and run on `ubuntu_latest`. To designate the commands executed in this workflow, we have `steps:` at the same indentation level as `runs-on:`, with the subsequent steps indented once from there: the first step must be running the latest version of `Checkout`, which fetches the most recent commit so that the workflow can access it. From there, the two workflows diverge: `checkstyleworkflow.yml` contains the line `- run: mvn checkstyle:check` to execute that line of code and run checkstyle, whereas `junit.yml` contains an additional section for execution: before running `mvn test` (a command that executes the bot's JUnit files), it first ensures that the JDK is at version 21, as this is what is specified in our `pom.xml` file and is thus compatible.

### Setting up CD:
josh fill in

## CI/CD Execution
Continuous Integration can be executed in two different ways:
1. `push` to the repo on the `main` branch (automatically executes both workflows). Beside the commit message on Github there will either be a green checkmark or a red x, displaying the rate of succcess.
2. Go to the `Actions` tab of the repo on Github, selecting the workflow you wish to execute, and clicking the `Run workflow` button on the workflow page.

Continuous Deployment: 
1. josh fill in

## Technologies Used
- Discord: https://discord.com/
- git: https://git-scm.com/
- Github: https://github.com/
- AWS Academy Learner Lab: https://aws.amazon.com/training/awsacademy/
- Redis: https://redis.io/

## Background
- [Github Actions in Action (Chapter 2)](https://learning.oreilly.com/library/view/github-actions-in/9781633437302/OEBPS/Text/02.html): for help with setting up workflows for CI
