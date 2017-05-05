package huxley.model.games;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import huxley.model.exception.classicexception.NotFoundGameException;
import huxley.model.user.Player;
import sx.blah.discord.handle.obj.IUser;

/**
 * TODO --> Adding Comment
 * Created by alxqu on 22/04/2017.
 */
public class GameCalendar {

    private static final Logger LOGGER = LoggerFactory.getLogger(GameCalendar.class);
    private List<Player> players;

    public GamesHandler games;

    public GameCalendar() {
        players = new ArrayList<>();
        games = new GamesHandler();
        try {
            games.load();
        } catch (IOException e) {
            LOGGER.error("IOException found. " + e.getMessage());
        }
    }

    public void addPlanningForPlayer(IUser user, String gameAlias, LocalDateTime start, LocalDateTime end) throws NotFoundGameException {
        LOGGER.debug(String.format("Adding planning for player %s for the game %s.", user, gameAlias));

        Player player = players.stream().filter(p -> p.getUser().getStringID().equals(user.getStringID())).findFirst().orElse(null);
        // If players list does not contain player
        if (player == null) {
            player = new Player(user);
            this.players.add(player);
        }
        // Retrieve Game Data
        Game g = games.findGameByAlias(gameAlias);
        // Add to player planning
        player.addPlanning(g.getName(), start, end);
    }

    public GamesHandler getGames() {
        return games;
    }

    public List<IUser> getAvailablePlayers(IUser user, String gameAlias, LocalDateTime start, LocalDateTime end) throws NotFoundGameException {
        LOGGER.debug(String.format("Get available players for game %s.", gameAlias));

        List<IUser> plyrs = new ArrayList<>();
        // Retrieve Game Data
        Game g = games.findGameByAlias(gameAlias);
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

    public Player getPlayerBasedOnDiscordID(IUser user) {
        return this.players.stream().filter(p -> p.getUser().getStringID().equals(user.getStringID())).findFirst().orElse(null);
    }

}
