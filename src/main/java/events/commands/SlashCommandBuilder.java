package events.commands;

import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.MessageEmbed;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.dv8tion.jda.api.interactions.commands.OptionMapping;

public class SlashCommandBuilder extends ListenerAdapter {

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
    }
}
