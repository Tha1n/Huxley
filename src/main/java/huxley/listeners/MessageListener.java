package huxley.listeners;

import huxley.commands.ICommand;
import huxley.model.discord.DiscordClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import sx.blah.discord.api.events.EventSubscriber;
import sx.blah.discord.handle.impl.events.MessageReceivedEvent;

/**
 * TODO --> Adding Comment
 * Created by alxqu on 24/04/2017.
 */
public class MessageListener {
    private Logger LOGGER = LoggerFactory.getLogger(MessageListener.class);

    @EventSubscriber
    public void onReady(MessageReceivedEvent event) {
        // If the author is a bot, message get ignored
        if (!event.getMessage().getAuthor().isBot()) {
            for(ICommand command : DiscordClient.getCommands()) {
                command.request(event.getMessage());
            }
        }
    }
}
