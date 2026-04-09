package edu.moravian;

import io.github.cdimascio.dotenv.Dotenv;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.dv8tion.jda.api.requests.GatewayIntent;
import org.jetbrains.annotations.NotNull;

public class JeopardyBot
{
    public static void main(String[] args)
    {
        Dotenv dotenv = Dotenv.load();
        String token = dotenv.get("DISCORD_TOKEN");

        JDA api = JDABuilder.createDefault(token).enableIntents(GatewayIntent.MESSAGE_CONTENT).build();

        api.addEventListener(new ListenerAdapter()
        {
            @Override
            public void onMessageReceived(@NotNull MessageReceivedEvent event)
            {
                if (event.getAuthor().isBot())
                    return;

                if (!event.getChannel().getName().equals("kubisek-bot"))
                    return;

                Player player = new Player(event.getAuthor().getName());
                String message = event.getMessage().getContentRaw();
                String server = event.getGuild().getName();
                String channel = event.getChannel().getName();

                BotResponder responder = new BotResponder();
                String response = responder.respond(player, message, server, channel);
                if (!response.isEmpty())
                    event.getChannel().sendMessage(response).queue();
            }
        });
    }
}
