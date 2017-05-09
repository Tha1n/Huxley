package huxley.model.games;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * POJO class to represent json file that hold game data.
 * Created by alxqu on 16/04/2017.
 */
public class GameList {

    @SuppressWarnings("unused")
	private static final Logger LOGGER = LoggerFactory.getLogger(GameList.class);
    private List<Game> games;

    /**
     * Constructor.
     */
    public GameList() {
        super();
        games = new ArrayList<Game>();
    }

    /**
     * Get {@link Game} list.
     * @return A list of {@link Game}
     */
    public List<Game> getGames() {
        return games;
    }

}
