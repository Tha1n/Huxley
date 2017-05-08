package huxley.model.discord;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import huxley.commands.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import huxley.HuxleyApp;
import huxley.model.games.GameCalendar;
import sx.blah.discord.api.ClientBuilder;
import sx.blah.discord.api.IDiscordClient;
import sx.blah.discord.util.DiscordException;

/**
 * TODO --> Adding Comment
 * Created by alxqu on 16/04/2017.
 */
public class DiscordClient {
	private static final Logger LOGGER = LoggerFactory.getLogger(DiscordClient.class);
    private static DiscordClient instance;

    private IDiscordClient discordClient;
    private List<ICommand> commands;

    private static final GameCalendar gameCalendar = new GameCalendar();

    private DiscordClient() {
        Properties configuration = new Properties();

        try {
            // Loading config
            configuration.load(DiscordClient.class.getResourceAsStream("/configuration.properties"));

            // Building commands
            commands = new ArrayList<>();
            initCommands();

            // Building Discord client
            discordClient = new ClientBuilder().withToken(configuration.getProperty("discord.token")).login();
        } catch (IOException e) {
            LOGGER.error(String.format(HuxleyApp.getLanguage().getProperty("exception.io"), e));
        } catch (DiscordException e) {
            LOGGER.error(String.format(HuxleyApp.getLanguage().getProperty("exception.discord"), e));
        }
    }

    private void initCommands() {
        // TODO Adding other commands further
        commands.add(new WannaPlayCommand());
        commands.add(new ShowPlanningCommand());
        commands.add(new GameListCommand());
        commands.add(new HuxleyCommand());
    }

    private static DiscordClient getInstance() {
        if (instance == null) {
            instance = new DiscordClient();
        }
        return instance;
    }

    public static IDiscordClient getDiscordClient() {
        return getInstance().discordClient;
    }

    public static List<ICommand> getCommands() {
        return getInstance().commands;
    }

	public static GameCalendar getGamecalendar() {
		return gameCalendar;
	}

}
