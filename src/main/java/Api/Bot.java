package Api;

import events.commands.SlashCommandBuilder;
import events.HelloEvent;
import events.commands.Calculate;
import events.commands.embedCommand;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.interactions.commands.OptionType;
import net.dv8tion.jda.api.requests.GatewayIntent;


public class Bot {
    public static void main(String[] args) throws Exception {
        //Make your own environment variable with your discord API key with name(without brackets): [DISCORD_BOT_API_KEY].
        JDA jda = JDABuilder.createDefault(System.getenv("DISCORD_BOT_API_KEY"))
                .enableIntents(GatewayIntent.MESSAGE_CONTENT)
                .addEventListeners(new HelloEvent())
                .addEventListeners(new Calculate())
                .addEventListeners(new embedCommand())
                .addEventListeners(new SlashCommandBuilder())
                .build().awaitReady();

        //Discord server ID, change guildId to your own server ID.
        String guildId = "653639796899577890";
        Guild guild = jda.getGuildById(guildId);
        if (guild != null) {
            guild.upsertCommand("deck", "yugioh decks")
                    .addOption(OptionType.STRING, "archetype", "yugioh archetype", true, true).queue();
        }
    }
}
