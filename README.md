# Jones-Kubisek-Sekol-Final
## How to create the bot as a .jar file
1. The maven-assembly-plugin has already been added to the `pom.xml` file, so just input `mvn package` to create both the initial .jar file and a .jar file with dependencies.
## How to execute the .jar file properly
1. Create a `.env` file with `nano .env` and put `DISCORD_TOKEN=<discord token>` as the first line. As the second line, type `CHANNEL_NAME=<name of output channel>`
2. Open a new terminal window and, assuming Redis has been installed, input `redis-server` to start running Redis.
3. In your previous terminal window, input `java -jar target/dbot-1.0.0-jar-with-dependencies.jar`. Congrats! Your bot is now running from a .jar file. 
