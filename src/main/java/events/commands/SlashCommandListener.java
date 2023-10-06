package events.commands;

import Api.ApiRetriever;
import Api.Model.Card;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.dv8tion.jda.api.interactions.commands.OptionMapping;
import net.dv8tion.jda.api.utils.FileUpload;

import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.List;
import java.util.Objects;

public class SlashCommandListener extends ListenerAdapter {

    @Override
    public void onSlashCommandInteraction(SlashCommandInteractionEvent event) {
        if (Objects.equals(event.getSubcommandName(), "search")) {
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
            EmbedBuilder errorEb = new EmbedBuilder();
            //If all variables are null, give user the Usage guide.
            if (name == null && type == null && atk == null && def == null && level == null && race == null && attribute == null && link == null && linkMarker == null && scale == null && cardSet == null && archetype == null) {
                errorEb.addField("Usage", "To use this bot, type '/card search' and select at least one of the options, " +
                                "by clicking or typing the name of the option(s) and typing the value(s) for the option(s). " +
                                "Read the description of the options for information about using that option." + "\n" +
                                "Usage example: To find 'Dark Magician' cards with 2500 or more attack, use: " + "\n" +
                                " `/card search [name] Dark magician [attack] >=2500`" + "\n" +
                                "`[name]` and `[attack]` being the options given by the program and chosen by you."
                        , true);
                event.getHook().sendMessageEmbeds(errorEb.build()).queue();
            } else {
                ApiRetriever api = new ApiRetriever();

                try {
                    List<Card> result = api.getCards(name, type, atk, def, level, race, attribute, link, linkMarker, scale, cardSet, archetype);
                    //TODO: remove this sout.
                    System.out.println(result);
                    if (result.isEmpty()) {
                        errorEb.addField("Error", "No cards found. Please check if your input is correct" +
                                        " and if a card exists with your given parameters." + "\n" +
                                        "Use '/card search' to get the usage guide."
                                , true);
                        event.getHook().sendMessageEmbeds(errorEb.build()).queue();
                    }
                    if (result.size() > 100) {
                        errorEb.addField("Error", "Too many cards found, 100+" + "\n" +
                                        "Please check if you gave a valid card name or add parameters to narrow your search down" + "\n" +
                                        "Use '/card search' to get the usage guide."
                                , true);
                        event.getHook().sendMessageEmbeds(errorEb.build()).queue();
                    }
                    String relativeTargetFolder = "src/main/resources/images/images_normal/";
                    String currentWorkingDirectory = System.getProperty("user.dir");
                    for (int i = 0; i < result.size() && i <= 4; i++) {
                        EmbedBuilder ebCounter = new EmbedBuilder();
                        EmbedBuilder eb = new EmbedBuilder();
                        try {
                            // Create the target folder if it doesn't exist
                            Path targetFolderPath = Paths.get(currentWorkingDirectory, relativeTargetFolder);
                            if (!Files.exists(targetFolderPath)) {
                                Files.createDirectories(targetFolderPath);
                            }

                            // Get the image file name from the URL
                            String imageUrl = result.get(i).getCardImages().get(0).getImageUrl();
                            String[] urlParts = imageUrl.split("/");
                            String fileName = urlParts[urlParts.length - 1];

                            // Construct the full path for the target file
                            Path targetFilePath = targetFolderPath.resolve(fileName);

                            // Check if the file already exists in the target folder
                            if (!Files.exists(targetFilePath)) {
                                // File does not exist, proceed to download
                                try (InputStream in = new URL(imageUrl).openStream()) {
                                    Files.copy(in, targetFilePath, StandardCopyOption.REPLACE_EXISTING);
                                    System.out.println("Image downloaded and saved to: " + targetFilePath);
                                }
                            } else {
                                System.out.println("Image already exists: " + targetFilePath);
                            }

                            // Now that the image is downloaded, create and send the embed;
                            Path imagePath = Paths.get(relativeTargetFolder, fileName);  // Full path to the downloaded image
                            File imageFile = imagePath.toFile();
                            eb.setTitle(result.get(i).getName());
                            eb.setImage("attachment://card.jpg");
                            //If,else if logic depending on the card type.
                            if (result.get(i).getFrameType().equals("spell")) {
                                eb.addField("Card information",
                                        result.get(i).getType() + "\n" +
                                                "Type of spell card: " + result.get(i).getRace() + "\n" +
                                                "Effect: " + "\n" + result.get(i).getDesc() + "\n" + "\n" +
                                                "Cheapest version on cardmarket: " + "\u20ac" + result.get(i).getCardPrices().get(0).getCardmarketPrice()
                                        , true);
                                Color spellColor = new Color(22, 128, 120, 255);
                                eb.setColor(spellColor);
                            } else if (result.get(i).getFrameType().equals("trap")) {
                                if (result.get(i).getRace() == null) {
                                    result.get(i).setRace("normal");
                                }
                                eb.addField("Card information",
                                        result.get(i).getType() + "\n" +
                                                "Type of trap card: " + result.get(i).getRace() + "\n" +
                                                "Effect: " + "\n" + result.get(i).getDesc() + "\n" + "\n" +
                                                "Cheapest version on cardmarket: " + "\u20ac" + result.get(i).getCardPrices().get(0).getCardmarketPrice()
                                        , true);
                                Color trapColor = new Color(155, 42, 114, 255);
                                eb.setColor(trapColor);
                            } else if (result.get(i).getFrameType().equals("effect")) {
                                eb.addField("Card information",
                                        result.get(i).getType() + "\n" +
                                                "Level: " + result.get(i).getLevel().toString() + "\n" +
                                                "Attribute: " + result.get(i).getAttribute() + "\n" +
                                                "Type: " + result.get(i).getRace() + "\n" +
                                                "Effect: " + "\n" + result.get(i).getDesc() + "\n" +
                                                "Attack: " + result.get(i).getAtk() + "\n" +
                                                "Defence: " + result.get(i).getDef() + "\n" + "\n" +
                                                "Cheapest version on cardmarket: " + "\u20ac" + result.get(i).getCardPrices().get(0).getCardmarketPrice()
                                        , true);
                                Color effectColor = new Color(185, 134, 115, 255);
                                eb.setColor(effectColor);
                            } else if (result.get(i).getFrameType().equals("ritual")) {
                                eb.addField("Card information",
                                        result.get(i).getType() + "\n" +
                                                "Level: " + result.get(i).getLevel().toString() + "\n" +
                                                "Attribute: " + result.get(i).getAttribute() + "\n" +
                                                "Type: " + result.get(i).getRace() + "\n" +
                                                "Effect: " + "\n" + result.get(i).getDesc() + "\n" +
                                                "Attack: " + result.get(i).getAtk() + "\n" +
                                                "Defence: " + result.get(i).getDef() + "\n" + "\n" +
                                                "Cheapest version on cardmarket: " + "\u20ac" + result.get(i).getCardPrices().get(0).getCardmarketPrice()
                                        , true);
                                Color ritualColor = new Color(102, 134, 195, 255);
                                eb.setColor(ritualColor);
                            } else if (result.get(i).getFrameType().equals("normal")) {
                                eb.addField("Card information",
                                        result.get(i).getType() + "\n" +
                                                "Level: " + result.get(i).getLevel().toString() + "\n" +
                                                "Attribute: " + result.get(i).getAttribute() + "\n" +
                                                "Type: " + result.get(i).getRace() + "\n" +
                                                "Effect: " + "\n" + result.get(i).getDesc() + "\n" +
                                                "Attack: " + result.get(i).getAtk() + "\n" +
                                                "Defence: " + result.get(i).getDef() + "\n" + "\n" +
                                                "Cheapest version on cardmarket: " + "\u20ac" + result.get(i).getCardPrices().get(0).getCardmarketPrice()
                                        , true);
                                Color normalColor = new Color(208, 159, 66, 255);
                                eb.setColor(normalColor);
                            } else if (result.get(i).getFrameType().equals("xyz")) {
                                eb.addField("Card information",
                                        result.get(i).getType() + "\n" +
                                                "Rank: " + result.get(i).getLevel().toString() + "\n" +
                                                "Attribute: " + result.get(i).getAttribute() + "\n" +
                                                "Type: " + result.get(i).getRace() + "\n" +
                                                "Effect: " + "\n" + result.get(i).getDesc() + "\n" +
                                                "Attack: " + result.get(i).getAtk() + "\n" +
                                                "Defence: " + result.get(i).getDef() + "\n" + "\n" +
                                                "Cheapest version on cardmarket: " + "\u20ac" + result.get(i).getCardPrices().get(0).getCardmarketPrice()
                                        , true);
                                Color xyzColor = new Color(29, 19, 30, 255);
                                eb.setColor(xyzColor);
                            } else if (result.get(i).getFrameType().equals("normal_pendulum") || result.get(i).getFrameType().equals("effect_pendulum") || result.get(i).getFrameType().equals("ritual_pendulum") || result.get(i).getFrameType().equals("fusion_pendulum") || result.get(i).getFrameType().equals("synchro_pendulum")) {
                                eb.addField("Card information",
                                        result.get(i).getType() + "\n" +
                                                "Level: " + result.get(i).getLevel().toString() + "\n" +
                                                "Attribute: " + result.get(i).getAttribute() + "\n" +
                                                "Type: " + result.get(i).getRace() + "\n" +
                                                "Scale: " + result.get(i).getScale() + "\n" +
                                                "Effect: " + "\n" + result.get(i).getMonsterDesc() + "\n" +
                                                "Pendulum effect: " + "\n" + result.get(i).getPendDesc() + "\n" +
                                                "Attack: " + result.get(i).getAtk() + "\n" +
                                                "Defence: " + result.get(i).getDef() + "\n" + "\n" +
                                                "Cheapest version on cardmarket: " + "\u20ac" + result.get(i).getCardPrices().get(0).getCardmarketPrice()
                                        , true);
                                Color pendulumColor = new Color(117, 125, 112, 255);
                                eb.setColor(pendulumColor);
                            } else if (result.get(i).getFrameType().equals("fusion")) {
                                eb.addField("Card information",
                                        result.get(i).getType() + "\n" +
                                                "Level: " + result.get(i).getLevel().toString() + "\n" +
                                                "Attribute: " + result.get(i).getAttribute() + "\n" +
                                                "Type: " + result.get(i).getRace() + "\n" +
                                                "Effect: " + "\n" + result.get(i).getDesc() + "\n" +
                                                "Attack: " + result.get(i).getAtk() + "\n" +
                                                "Defence: " + result.get(i).getDef() + "\n" + "\n" +
                                                "Cheapest version on cardmarket: " + "\u20ac" + result.get(i).getCardPrices().get(0).getCardmarketPrice()
                                        , true);
                                Color fusionColor = new Color(137, 76, 170, 255);
                                eb.setColor(fusionColor);
                            } else if (result.get(i).getFrameType().equals("synchro")) {
                                eb.addField("Card information",
                                        result.get(i).getType() + "\n" +
                                                "Level: " + result.get(i).getLevel().toString() + "\n" +
                                                "Attribute: " + result.get(i).getAttribute() + "\n" +
                                                "Type: " + result.get(i).getRace() + "\n" +
                                                "Effect: " + "\n" + result.get(i).getDesc() + "\n" +
                                                "Attack: " + result.get(i).getAtk() + "\n" +
                                                "Defence: " + result.get(i).getDef() + "\n" + "\n" +
                                                "Cheapest version on cardmarket: " + "\u20ac" + result.get(i).getCardPrices().get(0).getCardmarketPrice()
                                        , true);
                                Color synchroColor = new Color(232, 228, 225, 255);
                                eb.setColor(synchroColor);
                            } else if (result.get(i).getFrameType().equals("link")) {
                                eb.addField("Card information",
                                        result.get(i).getType() + "\n" +
                                                "Link: " + result.get(i).getLinkval().toString() + "\n" +
                                                "Link: " + result.get(i).getLinkmarkers().toString() + "\n" +
                                                "Attribute: " + result.get(i).getAttribute() + "\n" +
                                                "Type: " + result.get(i).getRace() + "\n" +
                                                "Effect: " + "\n" + result.get(i).getDesc() + "\n" +
                                                "Attack: " + result.get(i).getAtk() + "\n" + "\n" +
                                                "Cheapest version on cardmarket: " + "\u20ac" + result.get(i).getCardPrices().get(0).getCardmarketPrice()
                                        , true);
                                Color linkColor = new Color(1, 108, 178, 255);
                                eb.setColor(linkColor);
                            } else if (result.get(i).getFrameType().equals("xyz_pendulum")) {
                                eb.addField("Card information",
                                        result.get(i).getType() + "\n" +
                                                "Rank: " + result.get(i).getLevel().toString() + "\n" +
                                                "Attribute: " + result.get(i).getAttribute() + "\n" +
                                                "Type: " + result.get(i).getRace() + "\n" +
                                                "Scale: " + result.get(i).getScale() + "\n" +
                                                "Effect: " + "\n" + result.get(i).getMonsterDesc() + "\n" +
                                                "Pendulum effect: " + "\n" + result.get(i).getPendDesc() + "\n" +
                                                "Attack: " + result.get(i).getAtk() + "\n" +
                                                "Defence: " + result.get(i).getDef() + "\n" + "\n" +
                                                "Cheapest version on cardmarket: " + "\u20ac" + result.get(i).getCardPrices().get(0).getCardmarketPrice()
                                        , true);
                                Color xyzColor = new Color(29, 19, 30, 255);
                                eb.setColor(xyzColor);
                            } else if (result.get(i).getFrameType().equals("token")) {
                                eb.addField("Card information",
                                        result.get(i).getType() + "\n" +
                                                "Level: " + result.get(i).getLevel().toString() + "\n" +
                                                "Attribute: " + result.get(i).getAttribute() + "\n" +
                                                "Type: " + result.get(i).getRace() + "\n" +
                                                "Effect: " + "\n" + result.get(i).getDesc() + "\n" +
                                                "Attack: " + result.get(i).getAtk() + "\n" +
                                                "Defence: " + result.get(i).getDef() + "\n" + "\n" +
                                                "Cheapest version on cardmarket: " + "\u20ac" + result.get(i).getCardPrices().get(0).getCardmarketPrice()
                                        , true);
                                Color xyzColor = new Color(29, 19, 30, 255);
                                eb.setColor(xyzColor);
                            }
                            if (result.size()== 1){
                                ebCounter.setTitle(result.size() + " card found. " +" ["+(i+1)+"/"+result.size()+"]");
                            } else if(1 < result.size() && result.size() <= 5) {
                                ebCounter.setTitle(result.size()+" cards found. "+" ["+ (i + 1) + "/" + result.size() + "]");
                            } else{
                                ebCounter.setTitle(result.size()+" cards found. "+" ["+ (i + 1) + "/" + 5 + "] (max=5)");
                            }
                            event.getHook().sendMessageEmbeds(eb.build()).addFiles(FileUpload.fromData(imageFile, "card.jpg")).addEmbeds(ebCounter.build()).queue();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                } catch (InterruptedException | IOException | URISyntaxException e) {
                    e.printStackTrace();
                }
            }

        }

}

}

