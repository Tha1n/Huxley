package huxley.listeners;

import huxley.model.discord.DiscordClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import sx.blah.discord.api.events.EventSubscriber;
import sx.blah.discord.handle.impl.events.ReadyEvent;

/**
 * TODO --> Adding Comment
 * Created by alxqu on 24/04/2017.
 */
public class ReadyListener {
    private Logger LOGGER = LoggerFactory.getLogger(ReadyListener.class);

    @EventSubscriber
    public void onReady(ReadyEvent event) {
        LOGGER.info("Huxley is connected.");

        LOGGER.info("Register message listener.");
        DiscordClient.getDiscordClient().getDispatcher().registerListener(new MessageListener());

    }
}
