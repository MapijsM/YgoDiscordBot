package Api;

import Api.Model.CardParameter;
import events.commands.SlashCommandBuilder;
import events.HelloEvent;
import events.commands.Calculate;
import events.commands.embedCommand;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.interactions.commands.OptionType;
import net.dv8tion.jda.api.interactions.commands.build.Commands;
import net.dv8tion.jda.api.interactions.commands.build.OptionData;
import net.dv8tion.jda.api.interactions.commands.build.SubcommandData;
import net.dv8tion.jda.api.requests.GatewayIntent;

import java.util.Locale;


public class DiscordBot {

public void makeCommands() throws InterruptedException {
    //TODO: make separate methods for guild or global commands. Or should I not make them separate and just use an if statement?
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
        SubcommandData sub = new SubcommandData("search", "Find yugioh cards based on parameters given.");
        for (CardParameter parameter : CardParameter.values()) {
            sub.addOptions(parameter.toOption());
        }
        guild.updateCommands().addCommands(Commands.slash("card", "Find yugioh cards based on parameters given.")
                .addSubcommands(sub)).queue();
    }
}
    public static void main(String[] args) throws InterruptedException {
 DiscordBot slashCommandBuilder = new DiscordBot();
        slashCommandBuilder.makeCommands();

        }
    }


