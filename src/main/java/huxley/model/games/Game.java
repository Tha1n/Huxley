package huxley.model.games;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * TODO --> Adding Comment
 * Created by alxqu on 16/04/2017.
 */
public class Game {

    @SuppressWarnings("unused")
	private static final Logger LOGGER = LoggerFactory.getLogger(Game.class);
    private String name;
    private List<String> aliases;

    public Game() {
        super();
        name = StringUtils.EMPTY;
        aliases = new ArrayList<String>();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<String> getAliases() {
        return aliases;
    }

    public void setAliases(List<String> aliases) {
        this.aliases = aliases;
    }
}
