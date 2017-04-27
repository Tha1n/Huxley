package huxley.model.discord;

import huxley.HuxleyApp;
import huxley.commands.ICommand;
import huxley.commands.ShowPlanningCommand;
import huxley.commands.WannaPlayCommand;
import huxley.model.games.GameCalendar;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import sx.blah.discord.api.ClientBuilder;
import sx.blah.discord.api.IDiscordClient;
import sx.blah.discord.util.DiscordException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

/**
 * TODO --> Adding Comment
 * Created by alxqu on 16/04/2017.
 */
public class DiscordClient {

    private static DiscordClient instance;

    private static final Logger LOGGER = LoggerFactory.getLogger(DiscordClient.class);

    private static IDiscordClient discordClient;
    private List<ICommand> commands;

    public static GameCalendar gameCalendar = new GameCalendar();

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
            LOGGER.error(String.format(HuxleyApp.LANGUAGE.getProperty("exception.io"), e.getMessage()));
        } catch (DiscordException e) {
            LOGGER.error(String.format(HuxleyApp.LANGUAGE.getProperty("exception.discord"), e.getMessage()));
        }
    }

    private void initCommands() {
        // TODO Adding other commands further
        commands.add(new WannaPlayCommand());
        commands.add(new ShowPlanningCommand());
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

}
