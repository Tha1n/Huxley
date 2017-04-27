package huxley.model.games;

import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * TODO --> Adding Comment
 * Created by alxqu on 16/04/2017.
 */
public class Game {

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
