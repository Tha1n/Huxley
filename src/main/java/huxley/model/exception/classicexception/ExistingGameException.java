package huxley.model.exception.classicexception;

import huxley.HuxleyApp;
import huxley.model.games.Game;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import sx.blah.discord.handle.obj.IMessage;

/**
 * TODO --> Adding Comment
 * Created by alxqu on 19/04/2017.
 */
public class ExistingGameException extends Exception {

    private final static Logger LOGGER = LoggerFactory.getLogger(ExistingGameException.class);

    public ExistingGameException(Game game) {
        super(String.format(HuxleyApp.LANGUAGE.getProperty("exception.existing.game"), game.getName()));
    }

}
