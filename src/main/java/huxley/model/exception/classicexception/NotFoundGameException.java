package huxley.model.exception.classicexception;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import huxley.HuxleyApp;

/**
 * TODO --> Adding Comment
 * Created by alxqu on 19/04/2017.
 */
public class NotFoundGameException extends Exception {

    /**
	 * 
	 */
	private static final long serialVersionUID = 4581187618519614208L;
	private static final Logger LOGGER = LoggerFactory.getLogger(NotFoundGameException.class);

    public NotFoundGameException(String gameAlias) {
        super(String.format(HuxleyApp.LANGUAGE.getProperty("exception.game.not.found"), gameAlias));
        LOGGER.debug(String.format("%s exception make.", NotFoundGameException.class.getName()));
    }
}
