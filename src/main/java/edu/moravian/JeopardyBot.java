package edu.moravian;

import io.github.cdimascio.dotenv.Dotenv;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.dv8tion.jda.api.requests.GatewayIntent;
import org.jetbrains.annotations.NotNull;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.secretsmanager.SecretsManagerClient;
import software.amazon.awssdk.services.secretsmanager.model.GetSecretValueRequest;
import software.amazon.awssdk.services.secretsmanager.model.GetSecretValueResponse;

public class JeopardyBot
{
    public static void main(String[] args)
    {

        String token;

        String secretName = "220_Discord_Token";
        String secretKey = "DISCORD_TOKEN";

        edu.moravian.secrets.Secrets secrets = new edu.moravian.secrets.Secrets();

        token = secrets.getSecret(secretName, secretKey);

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
    
