package huxley.model.games;

import java.util.ArrayList;
import java.util.List;

/**
 * TODO --> Adding Comment
 * Created by alxqu on 16/04/2017.
 */
class GameList {

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
