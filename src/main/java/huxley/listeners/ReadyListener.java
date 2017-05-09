package huxley.listeners;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import huxley.HuxleyApp;
import huxley.model.discord.DiscordClient;
import sx.blah.discord.api.IDiscordClient;
import sx.blah.discord.api.events.EventSubscriber;
import sx.blah.discord.handle.impl.events.ReadyEvent;
import sx.blah.discord.util.Image;

/**
 * Discord Listener when bot is ready to work.
 * Created by alxqu on 24/04/2017.
 */
public class ReadyListener {
    private static Logger LOGGER = LoggerFactory.getLogger(ReadyListener.class);

    @EventSubscriber
    public void onReady(ReadyEvent event) throws IOException, URISyntaxException {
        LOGGER.info("Huxley is connected.");

        IDiscordClient client = DiscordClient.getDiscordClient();
        URL imgUrl = ReadyListener.class.getResource("/Huxley.png");
        File img = new File(imgUrl.toURI());
        LOGGER.info("Set visual parameters for the bot.");
        client.changeUsername(HuxleyApp.getConfiguration().getProperty("huxley.name"));
        client.changeAvatar(Image.forFile(img));
        client.changePlayingText(HuxleyApp.getLanguage().getProperty("huxley.playing.to"));
        client.idle();

        LOGGER.info("Register message listener.");
        client.getDispatcher().registerListener(new MessageListener());
    }
}
