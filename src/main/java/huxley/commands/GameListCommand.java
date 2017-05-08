package huxley.commands;

import huxley.HuxleyApp;
import huxley.model.discord.DiscordClient;
import huxley.model.discord.DiscordMessageUtils;
import huxley.model.exception.classicexception.ExistingGameException;
import huxley.model.exception.classicexception.NotFoundGameException;
import huxley.model.exception.customexception.UnknownChoiceException;
import huxley.model.games.Game;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import sx.blah.discord.handle.obj.IMessage;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Pattern;

/**
 * Discord Command to see GameList, add or remove games.
 * Created by alxqu on 04/05/2017.
 */
public class GameListCommand extends AbstractCommand {

    private static final Logger LOGGER = LoggerFactory.getLogger(GameListCommand.class);
    private static final int GAME_ALIAS_GROUP = 2;
    private static final int GAME_NAME_GROUP = 4;
    private static final int GAME_ALIASES_GROUP = 5;

    /**
     * Constructor.
     */
    public GameListCommand() {
        super(COMMANDS_PROPERTIES.getProperty("game.list.name"),
                Pattern.compile(String.format("^%s%s\\s(all|\\+|-)(\\s((\\\".+\\\")((\\s(\\w{%s,%s}))+)|(.{%s,%s})))?",
                        COMMANDS_PROPERTIES.getProperty("commands.prefix"), COMMANDS_PROPERTIES.getProperty("game.list.prefix"),
                        COMMANDS_PROPERTIES.getProperty("min.size.aliases"), COMMANDS_PROPERTIES.getProperty("min.size.aliases"),
                        COMMANDS_PROPERTIES.getProperty("min.size.aliases"), COMMANDS_PROPERTIES.getProperty("min.size.aliases"))));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean request(IMessage message) {
        boolean result = false;
        if (super.request(message)) {
            EGameListChoice choice = EGameListChoice.ADD.getTypeByInput(matcher.group(1));
            String content;
            switch (choice) {
                case ALL:
                    content = DiscordClient.getGamecalendar().getGames().getGameListString();
                    DiscordMessageUtils.sendMessage(message.getChannel(), content);
                    result = true;
                    break;
                case ADD:
                    content = addGame();
                    DiscordMessageUtils.sendMessage(message.getChannel(), content);
                    break;
                case DELETE:
                    content = removeGame();
                    DiscordMessageUtils.sendMessage(message.getChannel(), content);
                    break;
                case UNKNOWN:
                    new UnknownChoiceException().throwException(message);
                    result = false;
                    break;
            }
        }
        return result;
    }

    private String addGame() {
        String result = StringUtils.EMPTY;

        String name = matcher.group(GAME_NAME_GROUP).replace("\"", StringUtils.EMPTY);
        String tmpAliases = matcher.group(GAME_ALIASES_GROUP);

        if (name == null) {
            result = HuxleyApp.getLanguage().getProperty("command.gl.no.name.provide");
        } else if (tmpAliases == null) {
            result = HuxleyApp.getLanguage().getProperty("command.gl.no.aliases.provide");
        } else {
            tmpAliases = tmpAliases.trim();
            List<String> aliases = Arrays.asList(tmpAliases.split(StringUtils.SPACE));
            try {
                DiscordClient.getGamecalendar().getGames().addGameAndSave(name, aliases);
                result = HuxleyApp.getLanguage().getProperty("command.gl.game.added");
            } catch (ExistingGameException e) {
                LOGGER.error(String.format("%s", e));
                result = HuxleyApp.getLanguage().getProperty("exception.existing.game");
            } catch (IOException | URISyntaxException e) {
                LOGGER.error(String.format("%s", e));
            }
        }

        return result;
    }

    private String removeGame() {
        String result = StringUtils.EMPTY;

        String alias = matcher.group(GAME_ALIAS_GROUP);
        try {
            Game g = DiscordClient.getGamecalendar().getGames().findGameByAlias(alias);
            DiscordClient.getGamecalendar().getGames().removeGameAndSave(g);
            result = HuxleyApp.getLanguage().getProperty("command.gl.game.remove");
        } catch (NotFoundGameException e) {
            LOGGER.error(String.format("%s", e));
            result = HuxleyApp.getLanguage().getProperty("exception.existing.game");
        } catch (IOException | URISyntaxException e) {
            LOGGER.error(String.format("%s", e));
        }

        return result;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String help() {
        return null;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String helpDetailed() {
        return null;
    }
}
