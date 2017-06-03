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
import java.net.URLDecoder;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Class that allow to manipulate GameList in safe way.
 * Created by alxqu on 16/04/2017.
 */
public class GamesHandler {

    private static final Logger LOGGER = LoggerFactory.getLogger(GamesHandler.class);
    private static final String filePath = "/games.json";
    private Map<Long, GameList> gameListByGuildID;

    /**
     * Constructor
     */
    public GamesHandler(List<Long> guildIDs) {
        super();
        gameListByGuildID = new HashMap<>();
        for (Long id : guildIDs) {
            gameListByGuildID.put(id, new GameList());
            try {
                this.load(id);
            } catch (IOException e) {
                LOGGER.error(String.format("%s", e));
            }
        }
    }

    /**
     * Add a game in the list.
     *
     * @param game    Game to add.
     * @param guildId The guild ID.
     * @throws ExistingGameException If the game already exists.
     */
    private void addGame(Game game, Long guildId) throws ExistingGameException {
        LOGGER.debug(String.format("Adding game : %s.", game.getName()));

        if (this.gameListByGuildID.get(guildId).getGames().stream().anyMatch(g -> g.getName().equalsIgnoreCase(game.getName()))) {
            throw new ExistingGameException(game);
        } else {
            this.gameListByGuildID.get(guildId).getGames().add(game);
        }
    }

    /**
     * Load game list from disk.
     *
     * @throws IOException If we cannot load from the disk.
     */
    void load(Long guildId) throws IOException {
        LOGGER.debug("Loading games list data.");

        String path = getGameListFilePath(guildId);
        File gameFile = new File(path);
        // If a file does not exist for the guild -> Create it ! (Bit of time consumed due to creation for the first time)
        if (!gameFile.exists()) {
            LOGGER.info(String.format("No game file exists for the guild %d. Creating it.", guildId));
            ObjectMapper originalGameFileMapper = new ObjectMapper();
            GameList originalGameList = originalGameFileMapper.readValue(GamesHandler.class.getResourceAsStream(filePath), GameList.class);
            OutputStream outputStream = new FileOutputStream(gameFile);
            originalGameFileMapper.writeValue(outputStream, originalGameList);
        }

        ObjectMapper mapper = new ObjectMapper();
        gameListByGuildID.put(guildId, mapper.readValue(gameFile, GameList.class));
    }

    /**
     * Save game list on disk.
     *
     * @param guildId The guild ID.
     * @throws URISyntaxException If we cannot find the file.
     * @throws IOException        If we cannot save on disk.
     */
    void save(Long guildId) throws URISyntaxException, IOException {
        LOGGER.debug("Saving games list data.");

        ObjectMapper mapper = new ObjectMapper();
        OutputStream outputStream = new FileOutputStream(getGameListFilePath(guildId));

        mapper.writeValue(outputStream, this.gameListByGuildID);
    }

    /**
     * Find a game in the list with his alias and return it.
     *
     * @param alias   Alias of the searching game.
     * @param guildId The guild ID.
     * @return The founded game.
     * @throws NotFoundGameException If the game was not found.
     */
    public Game findGameByAlias(String alias, Long guildId) throws NotFoundGameException {
        LOGGER.debug(String.format("Searching game with alias %s.", alias));

        Game foundGame = this.gameListByGuildID.get(guildId).getGames().stream().filter(
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
     * @param guildId The guild ID.
     * @throws ExistingGameException If the game already exists.
     */
    private void addGame(String name, List<String> aliases, Long guildId) throws ExistingGameException {
        LOGGER.debug(String.format("Searching game : %s.", name));

        Game g = new Game();
        g.setName(name);
        g.setAliases(aliases);
        this.addGame(g, guildId);
    }

    /**
     * Adding game in the list and save it.
     *
     * @param name    Name of the game.
     * @param aliases List of aliases for this game.
     * @param guildId The guild ID.
     * @throws ExistingGameException If the game already exists.
     * @throws IOException           If we cannot save on disk.
     * @throws URISyntaxException    If we cannot retrieve the file.
     */
    public void addGameAndSave(String name, List<String> aliases, Long guildId) throws ExistingGameException, IOException, URISyntaxException {
        LOGGER.debug(String.format("Adding game : %s and save content.", name));

        this.addGame(name, aliases, guildId);
        this.save(guildId);
    }

    /**
     * Remove a game in the list.
     *
     * @param g       The game to remove.
     * @param guildId The guild ID.
     */
    private void removeGame(Game g, Long guildId) {
        this.gameListByGuildID.get(guildId).getGames().remove(g);
    }

    /**
     * Remove a game and save.
     *
     * @param g       The game to remove.
     * @param guildId The guild ID.
     * @throws IOException        If saving on disk failed.
     * @throws URISyntaxException If cannot retrieve file URI.
     */
    public void removeGameAndSave(Game g, Long guildId) throws IOException, URISyntaxException {
        LOGGER.debug(String.format("Removing game : %s and save content.", g.getName()));

        this.removeGame(g, guildId);
        this.save(guildId);
    }

    /**
     * Return the Games list in String format.
     *
     * @param guildId The guild ID.
     * @return The Games list.
     */
    public String getGameListString(Long guildId) {
        LOGGER.debug("Building Game list string.");

        StringBuilder result = new StringBuilder();
        for (Game g : this.gameListByGuildID.get(guildId).getGames()) {
            StringBuilder aliases = new StringBuilder();
            for (String alias : g.getAliases()) {
                aliases.append(alias);
                aliases.append(StringUtils.SPACE);
            }
            result.append(String.format(HuxleyApp.getLanguage().getProperty("model.games.handler.game.view"), g.getName(), aliases));
        }
        return result.toString();
    }

    /**
     * Get the Gamefile path based on Guild ID.
     *
     * @param guildId The Guild ID.
     * @return The path of Gamefile.
     * @throws UnsupportedEncodingException If the URL cannot be decode.
     */
    private String getGameListFilePath(Long guildId) throws UnsupportedEncodingException {
        String path = System.getProperty("user.dir");
        path = URLDecoder.decode(String.format("%s/games%d.json", path, guildId), "UTF-8");
        return path;
    }

}
