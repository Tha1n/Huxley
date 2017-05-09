package huxley.model.games;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Class that represent a video game.
 * Created by alxqu on 16/04/2017.
 */
public class Game {

    @SuppressWarnings("unused")
	private static final Logger LOGGER = LoggerFactory.getLogger(Game.class);
    private String name;
    private List<String> aliases;

    /**
     * Constructor.
     */
    public Game() {
        super();
        name = StringUtils.EMPTY;
        aliases = new ArrayList<String>();
    }

    /**
     * Get game name.
     * @return The game name.
     */
    public String getName() {
        return name;
    }

    /**
     * Set game name.
     * @param name The game name.
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Get the list of aliases.
     * @return The list of aliases.
     */
    public List<String> getAliases() {
        return aliases;
    }

    /**
     * Set the list of aliases.
     * @param aliases The list of aliases.
     */
    public void setAliases(List<String> aliases) {
        this.aliases = aliases;
    }
}
