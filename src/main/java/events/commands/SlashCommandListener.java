package events.commands;

import Api.ApiRetriever;
import Api.Model.Card;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.MessageEmbed;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.dv8tion.jda.api.interactions.commands.OptionMapping;
import net.dv8tion.jda.api.utils.messages.MessageCreateData;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;

public class SlashCommandListener extends ListenerAdapter {

    @Override
    public void onSlashCommandInteraction(SlashCommandInteractionEvent event) {
        if (event.getName().equals("deck")) {
            OptionMapping option = event.getOption("archetype");
            event.deferReply().queue();
            String tempString = null;
            if (option != null) {
                tempString = CapitalizeString.getCapitalizedString(option.getAsString());
            }

            //Build and fill embed TODO: make a separate method that does this.
            EmbedBuilder eb = new EmbedBuilder();
            eb.setDescription(tempString);
            MessageEmbed finishedEmbed = eb.build();

            event.getHook().sendMessage("Here is all the build information about your chosen archetype:" + "\n\n")
                    .setEmbeds(finishedEmbed).setEphemeral(true).queue();
        }
        if(Objects.equals(event.getSubcommandName(), "search")){
            String name = event.getOption("name", OptionMapping::getAsString);
            String type = event.getOption("type", OptionMapping::getAsString);
            String atk = event.getOption("atk", OptionMapping::getAsString);
            String def = event.getOption("def", OptionMapping::getAsString);
            String level = event.getOption("level", OptionMapping::getAsString);
            String race = event.getOption("race", OptionMapping::getAsString);
            String attribute = event.getOption("attribute", OptionMapping::getAsString);
            Integer link = event.getOption("link", OptionMapping::getAsInt);
            String linkMarker = event.getOption("linkMarker", OptionMapping::getAsString);
            Integer scale = event.getOption("scale", OptionMapping::getAsInt);
            String cardSet = event.getOption("cardSet", OptionMapping::getAsString);
            String archetype = event.getOption("archetype", OptionMapping::getAsString);
            event.deferReply().queue();
            ApiRetriever api = new ApiRetriever();
            try {
                List<Card> result = api.getCards(name,type,atk,def,level,race,attribute,link,linkMarker,scale,cardSet,archetype);
                System.out.println(result);
//               event.getHook().sendMessage(result.toString())
//                        .queue();
                event.getHook().sendMessage("your result has internally been printed, I hope that satisfies you!")
                        .queue();
            } catch (InterruptedException | IOException | URISyntaxException e) {
                e.printStackTrace();
            }
        }
    }
}
