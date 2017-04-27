package huxley.model.exception.classicexception;

import huxley.HuxleyApp;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import sx.blah.discord.handle.obj.IMessage;

/**
 * TODO --> Adding Comment
 * Created by alxqu on 19/04/2017.
 */
public class NotFoundGameException extends Exception {

    private final static Logger LOGGER = LoggerFactory.getLogger(NotFoundGameException.class);

    public NotFoundGameException(String gameAlias) {
        super(String.format(HuxleyApp.LANGUAGE.getProperty("exception.game.not.found"), gameAlias));
    }
}
