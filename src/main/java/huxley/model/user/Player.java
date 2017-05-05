package huxley.model.user;

import huxley.HuxleyApp;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import sx.blah.discord.handle.obj.IUser;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.Map.Entry;
import java.util.stream.Collectors;

/**
 * TODO --> Adding Comment
 * Created by alxqu on 22/04/2017.
 */
public class Player {

    private static Logger LOGGER = LoggerFactory.getLogger(Player.class);
    private IUser user;
    private Map<String, List<GameTimeSlot>> gamePlanning;

    private Player() {
        this.user = null;
        gamePlanning = new HashMap<>();
    }

    public Player(IUser user) {
        this();
        this.user = user;
    }

    public IUser getUser() {
        return user;
    }

    public void addPlanning(String game, LocalDateTime start, LocalDateTime end) {
        LOGGER.debug("Adding planning for player " + this.user.getStringID() + " for game " + game);

        GameTimeSlot gts = new GameTimeSlot(start, end);
        if (!gamePlanning.containsKey(game)) {
            gamePlanning.put(game, new ArrayList<>());
        }
        List<GameTimeSlot> lgts = gamePlanning.get(game);
        lgts.add(gts);
        gamePlanning.put(game, lgts);
    }

    public void cleanPlanning() {
        LOGGER.debug("Cleaning planning for player " + this.user.getStringID());

        LocalDateTime now = LocalDateTime.now();
        Set<String> gToClean = new HashSet<>();
        for (Entry<String, List<GameTimeSlot>> gamePlanning : this.gamePlanning.entrySet()) {
            List<GameTimeSlot> gtsToClean = gamePlanning.getValue().stream().filter(gts -> now.isAfter(gts.getEndingDate())).collect(Collectors.toList());
            gamePlanning.getValue().removeAll(gtsToClean);
            if (gamePlanning.getValue().size() == 0) { // Remove game from the list
                gToClean.add(gamePlanning.getKey());
            }
        }
        this.gamePlanning.keySet().removeAll(gToClean);
    }

    public String formatPlanning() {
        StringBuilder result = new StringBuilder();
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("[dd/MM] HH:mm");

        if (this.gamePlanning.size() > 0) {
            for (Entry<String, List<GameTimeSlot>> gameTimeSlot: this.gamePlanning.entrySet()) {
                result.append(String.format(HuxleyApp.LANGUAGE.getProperty("model.player.have.game"), gameTimeSlot.getKey()));
                for (GameTimeSlot timeSlot: gameTimeSlot.getValue()) {
                    result.append(String.format("%s -> %s", dtf.format(timeSlot.getBeginDate()), dtf.format(timeSlot.getEndingDate())));
                }
                result.append("\n");
            }
        } else {
            result.append(HuxleyApp.LANGUAGE.getProperty("model.player.no.game"));
        }
        return result.toString();
    }

    public boolean canPlay(String game, LocalDateTime st, LocalDateTime end) {
        LOGGER.debug("Check if player " + this.user.getStringID() + " can play game " + game);

        boolean isReadyToPlay = false;
        for (GameTimeSlot timeSlot: this.gamePlanning.get(game)) {
            if (!st.isAfter(timeSlot.getEndingDate()) && !end.isBefore(timeSlot.getBeginDate())) {
                isReadyToPlay = true;
                break; // Job done
            }
        }
        return isReadyToPlay;
    }
}
