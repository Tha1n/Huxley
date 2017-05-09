package huxley.model.games;

import com.fasterxml.jackson.databind.ObjectMapper;
import huxley.HuxleyApp;
import huxley.model.exception.classicexception.ExistingGameException;
import huxley.model.exception.classicexception.NotFoundGameException;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.net.URISyntaxException;
import java.util.List;

/**
 * Class that allow to manipulate GameList in safe way.
 * Created by alxqu on 16/04/2017.
 */
public class GamesHandler {

    private static final Logger LOGGER = LoggerFactory.getLogger(GamesHandler.class);
    private static final String filePath = "/games.json";
    private GameList gameList;

    /**
     * Constructor
     */
    public GamesHandler() {
        super();
        gameList = new GameList();
    }

    /**
     * Add a game in the list.
     *
     * @param game Game to add.
     * @throws ExistingGameException If the game already exists.
     */
    private void addGame(Game game) throws ExistingGameException {
        LOGGER.debug(String.format("Adding game : %s.", game.getName()));

        if (this.gameList.getGames().stream().anyMatch(g -> g.getName().equalsIgnoreCase(game.getName()))) {
            throw new ExistingGameException(game);
        } else {
            this.gameList.getGames().add(game);
        }
    }

    /**
     * Load game list from disk.
     * @throws IOException If we cannot load from the disk.
     */
    public void load() throws IOException {
        LOGGER.debug("Loading games list data.");

        ObjectMapper mapper = new ObjectMapper();
        gameList = mapper.readValue(GamesHandler.class.getResourceAsStream(filePath), GameList.class);
    }

    /**
     * Save game list on disk.
     * @throws URISyntaxException If we cannot find the file.
     * @throws IOException If we cannot save on disk.
     */
    public void save() throws URISyntaxException, IOException {
        LOGGER.debug("Saving games list data.");

        ObjectMapper mapper = new ObjectMapper();
        File outputFile = new File(GamesHandler.class.getResource(filePath).toURI());
        OutputStream outputStream = new FileOutputStream(outputFile);
        mapper.writeValue(outputStream, this.gameList);
    }

    /**
     * Find a game in the list with his alias and return it.
     *
     * @param alias Alias of the searching game.
     * @return The founded game.
     * @throws NotFoundGameException If the game was not found.
     */
    public Game findGameByAlias(String alias) throws NotFoundGameException {
        LOGGER.debug(String.format("Searching game with alias %s.", alias));

        Game foundGame = this.gameList.getGames().stream().filter(
                g -> g.getAliases().stream().anyMatch(a -> a.equalsIgnoreCase(alias))
        ).findFirst().orElse(null);
        if (foundGame == null) {
            throw new NotFoundGameException(alias);
        }
        return foundGame;
    }

    /**
     * Adding game in the list.
     *
     * @param name    Name of the game.
     * @param aliases List of aliases for this game.
     * @throws ExistingGameException If the game already exists.
     */
    private void addGame(String name, List<String> aliases) throws ExistingGameException {
        LOGGER.debug(String.format("Searching game : %s.", name));

        Game g = new Game();
        g.setName(name);
        g.setAliases(aliases);
        this.addGame(g);
    }

    /**
     * Adding game in the list and save it.
     *
     * @param name    Name of the game.
     * @param aliases List of aliases for this game.
     * @throws ExistingGameException If the game already exists.
     * @throws IOException If we cannot save on disk.
     * @throws URISyntaxException If we cannot retrieve the file.
     */
    public void addGameAndSave(String name, List<String> aliases) throws ExistingGameException, IOException, URISyntaxException {
        LOGGER.debug(String.format("Adding game : %s and save content.", name));

        this.addGame(name, aliases);
        this.save();
    }

    /**
     * Remove a game in the list.
     * @param g The game to remove.
     */
    private void removeGame(Game g) {
        this.gameList.getGames().remove(g);
    }

    /**
     * Remove a game and save.
     * @param g The game to remove.
     * @throws IOException
     * @throws URISyntaxException
     */
    public void removeGameAndSave(Game g) throws IOException, URISyntaxException {
        LOGGER.debug(String.format("Removing game : %s and save content.", g.getName()));

        this.removeGame(g);
        this.save();
    }

    /**
     * Return the Games list in String format.
     * @return The Games list.
     */
    public String getGameListString() {
        LOGGER.debug("Building Game list string.");

        StringBuilder result = new StringBuilder();
        for (Game g: this.gameList.getGames()) {
            StringBuilder aliases = new StringBuilder();
            for (String alias: g.getAliases()) {
            	aliases.append(alias);
            	aliases.append(StringUtils.SPACE);
            }
            result.append(String.format(HuxleyApp.getLanguage().getProperty("model.games.handler.game.view"), g.getName(), aliases));
        }
        return result.toString();
    }

}
