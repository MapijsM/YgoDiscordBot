package Api;

import Api.Model.CardParameter;
import events.commands.SlashCommandListener;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.interactions.commands.build.Commands;
import net.dv8tion.jda.api.interactions.commands.build.SubcommandData;
import net.dv8tion.jda.api.requests.GatewayIntent;


public class DiscordBot {

public void makeCommands() throws InterruptedException {
    JDA jda = JDABuilder.createDefault(System.getenv("DISCORD_BOT_API_KEY"))
            .enableIntents(GatewayIntent.MESSAGE_CONTENT)
            .addEventListeners(new SlashCommandListener())
            .build().awaitReady();

    SubcommandData sub = new SubcommandData("search", "Find yugioh cards based on parameters given.");
    for (CardParameter parameter : CardParameter.values()) {
        sub.addOptions(parameter.toOption());
    }
    jda.updateCommands().addCommands(Commands.slash("card", "Find yugioh cards based on parameters given.")
            .addSubcommands(sub)).queue();
}
    public static void main(String[] args) throws InterruptedException {
 DiscordBot slashCommandBuilder = new DiscordBot();
        slashCommandBuilder.makeCommands();

        }
    }


