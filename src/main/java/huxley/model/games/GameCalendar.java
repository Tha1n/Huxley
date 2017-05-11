package huxley.model.games;

import huxley.model.exception.classicexception.NotFoundGameException;
import huxley.model.user.Player;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import sx.blah.discord.handle.obj.IUser;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * Class that represent the gaming calendar from the Huxley point of view.
 * Created by alxqu on 22/04/2017.
 */
public class GameCalendar {

    private static final Logger LOGGER = LoggerFactory.getLogger(GameCalendar.class);

    private List<Player> players;
    private GamesHandler games;

    /**
     * Constructor.
     */
    public GameCalendar(List<Long> guildIDs) {
        players = new ArrayList<>();
        games = new GamesHandler(guildIDs);
        try {
            for (Long id : guildIDs) {
                games.load(id);
            }
        } catch (IOException e) {
            LOGGER.error(String.format("IOException found. %s", e));
        }
    }

    /**
     * Add a planning for a player.
     *
     * @param user      The {@link IUser} object that emit the message in the first place.
     * @param gameAlias The game alias.
     * @param start     The starting date.
     * @param end       The ending date.
     * @param guildId   The guild ID.
     * @throws NotFoundGameException If the game was not found.
     */
    public void addPlanningForPlayer(IUser user, String gameAlias, LocalDateTime start, LocalDateTime end, Long guildId) throws NotFoundGameException {
        LOGGER.debug(String.format("Adding planning for player %s for the game %s.", user, gameAlias));

        Player player = players.stream().filter(p -> p.getUser().getStringID().equals(user.getStringID())).findFirst().orElse(null);
        // If players list does not contain player
        if (player == null) {
            player = new Player(user);
            this.players.add(player);
        }
        // Retrieve Game Data
        Game g = games.findGameByAlias(gameAlias, guildId);
        // Add to player planning
        player.addPlanning(g.getName(), start, end);
    }

    public GamesHandler getGames() {
        return games;
    }

    /**
     * Get all players that can play between the given date.
     *
     * @param user      The player that emit the message in first place.
     * @param gameAlias The game alias.
     * @param start     The starting date.
     * @param end       The ending date.
     * @param guildId   The guild ID.
     * @return A list of {@link IUser}
     * @throws NotFoundGameException If the game was not found.
     */
    public List<IUser> getAvailablePlayers(IUser user, String gameAlias, LocalDateTime start, LocalDateTime end, Long guildId) throws NotFoundGameException {
        LOGGER.debug(String.format("Get available players for game %s.", gameAlias));

        List<IUser> plyrs = new ArrayList<>();
        // Retrieve Game Data
        Game g = games.findGameByAlias(gameAlias, guildId);
        for (Player player : this.players) {
            // Clean before search
            player.cleanPlanning();

            if (!player.getUser().getStringID().equals(user.getStringID()) &&
                    player.canPlay(g.getName(), start, end)) {
                plyrs.add(player.getUser());
            }
        }
        return plyrs;
    }

    /**
     * Retrieve a {@link Player} object from {@link IUser} object.
     *
     * @param user A {@link IUser} object.
     * @return The {@link Player} object, {@code null} otherwise.
     */
    public Player getPlayerBasedOnDiscordID(IUser user) {
        return this.players.stream().filter(p -> p.getUser().getStringID().equals(user.getStringID())).findFirst().orElse(null);
    }

}
