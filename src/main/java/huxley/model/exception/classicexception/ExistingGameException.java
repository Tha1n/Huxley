package huxley.model.exception.classicexception;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import huxley.HuxleyApp;
import huxley.model.games.Game;

/**
 * TODO --> Adding Comment
 * Created by alxqu on 19/04/2017.
 */
public class ExistingGameException extends Exception {

    /**
	 * 
	 */
	private static final long serialVersionUID = 5950274108228622268L;
	private static final Logger LOGGER = LoggerFactory.getLogger(ExistingGameException.class);

    public ExistingGameException(Game game) {
    	super(String.format(HuxleyApp.LANGUAGE.getProperty("exception.existing.game"), game.getName()));
    	LOGGER.debug(String.format("%s exception make.", ExistingGameException.class.getName()));
    }

}
