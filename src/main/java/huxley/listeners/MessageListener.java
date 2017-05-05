package huxley.listeners;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import huxley.commands.ICommand;
import huxley.model.discord.DiscordClient;
import sx.blah.discord.api.events.EventSubscriber;
import sx.blah.discord.handle.impl.events.guild.channel.message.MessageReceivedEvent;

/**
 * A Message Listener for all Discord message.
 * Created by alxqu on 24/04/2017.
 */
public class MessageListener {
    @SuppressWarnings("unused")
	private Logger LOGGER = LoggerFactory.getLogger(MessageListener.class);

    @EventSubscriber
    public void onReady(MessageReceivedEvent event) {
        // If the author is a bot, message is ignored
        if (!event.getMessage().getAuthor().isBot()) {
            for(ICommand command : DiscordClient.getCommands()) {
                command.request(event.getMessage());
            }
        }
    }
}
