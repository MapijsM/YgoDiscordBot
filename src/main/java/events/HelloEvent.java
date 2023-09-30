package events;

import net.dv8tion.jda.api.events.message.GenericMessageEvent;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

import java.util.Objects;

public class HelloEvent extends ListenerAdapter {
    public void onMessageReceived(MessageReceivedEvent event) {
        String[] messageSent = event.getMessage().getContentRaw().split(" ");

        //Checking if messages in bot-channel AND user != bot
        if (event.getChannel().toString().split("\\(|:")[1].equals("bot-channel")) {
            if (!Objects.requireNonNull(event.getMember()).getUser().isBot()) {

                String userName = Objects.requireNonNull(event.getMessage().getMember()).getUser().getName();
                for (String s : messageSent) {
                    if (s.equalsIgnoreCase("hello")) {
                        event.getChannel().sendMessage("hi" + " " + userName + "!").queue();
                    }
                }
            }
        }
    }
}
