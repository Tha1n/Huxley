package huxley.model.games;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * TODO --> Adding Comment
 * Created by alxqu on 16/04/2017.
 */
class GameList {

    @SuppressWarnings("unused")
	private static final Logger LOGGER = LoggerFactory.getLogger(GameList.class);
    private List<Game> games;

    public GameList() {
        super();
        games = new ArrayList<Game>();
    }

    public List<Game> getGames() {
        return games;
    }

    public void setGames(List<Game> games) {
        this.games = games;
    }
}
