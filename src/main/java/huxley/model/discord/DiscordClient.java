package huxley.model.discord;

import huxley.HuxleyApp;
import huxley.commands.*;
import huxley.model.games.GameCalendar;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import sx.blah.discord.api.ClientBuilder;
import sx.blah.discord.api.IDiscordClient;
import sx.blah.discord.util.DiscordException;

import java.util.ArrayList;
import java.util.List;

/**
 * Class that represent the Discord client BOT Huxley.
 * Created by alxqu on 16/04/2017.
 */
public class DiscordClient {
	private static final Logger LOGGER = LoggerFactory.getLogger(DiscordClient.class);
    private static DiscordClient instance;

    private IDiscordClient discordClient;
    private List<ICommand> commands;

    private static final GameCalendar gameCalendar = new GameCalendar();

    /**
     * Constructor.
     */
    private DiscordClient() {
        try {
            // Building commands
            commands = new ArrayList<>();
            initCommands();

            // Building Discord client
            discordClient = new ClientBuilder().withToken(HuxleyApp.getConfiguration().getProperty("discord.token")).login();
        } catch (DiscordException e) {
            LOGGER.error(String.format(HuxleyApp.getLanguage().getProperty("exception.discord"), e));
        }
    }

    /**
     * Initialize commands list.
     */
    private void initCommands() {
        commands.add(new WannaPlayCommand());
        commands.add(new ShowPlanningCommand());
        commands.add(new GameListCommand());
        commands.add(new HuxleyCommand());
    }

    /**
     * Get instance of {@link DiscordClient}.
     * @return The instance.
     */
    private static DiscordClient getInstance() {
        if (instance == null) {
            instance = new DiscordClient();
        }
        return instance;
    }

    /**
     * Get {@link IDiscordClient} object.
     * @return The {@link IDiscordClient} object.
     */
    public static IDiscordClient getDiscordClient() {
        return getInstance().discordClient;
    }

    /**
     * Get the list of commands.
     * @return The list of commands.
     */
    public static List<ICommand> getCommands() {
        return getInstance().commands;
    }

    /**
     * Get the game calendar.
     * @return The game calendar.
     */
	public static GameCalendar getGamecalendar() {
		return gameCalendar;
	}

}
