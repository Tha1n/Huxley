package huxley;

import huxley.listeners.ReadyListener;
import huxley.model.discord.DiscordClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import sx.blah.discord.api.IDiscordClient;

import java.io.IOException;
import java.util.Properties;

/**
 * TODO --> Adding Comment
 * Created by alxqu on 24/03/2017.
 */
public class HuxleyApp {
	private static final Logger LOGGER = LoggerFactory.getLogger(HuxleyApp.class);
	public static final Properties CONFIGURATION = new Properties();
	public static final Properties LANGUAGE = new Properties();
	static {
		try {
			CONFIGURATION.load(HuxleyApp.class.getResourceAsStream("/configuration.properties"));
			LANGUAGE.load(HuxleyApp.class.getResourceAsStream(String.format("/MessagesBundle%s.properties", CONFIGURATION.getProperty("huxley.language"))));
		} catch (IOException e) {
			LOGGER.error(String.format("Failed to load configuration. %s", e.getMessage()));
			System.exit(-1);
		}

	}

	public static void main(String[] args) {
		LOGGER.info("HuxleyApp is started.");
		IDiscordClient client = DiscordClient.getDiscordClient();
		client.getDispatcher().registerListener(new ReadyListener());
		LOGGER.info("HuxleyApp is finished.");
	}
}
