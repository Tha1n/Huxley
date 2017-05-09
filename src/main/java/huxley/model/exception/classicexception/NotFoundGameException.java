package huxley.model.exception.classicexception;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import huxley.HuxleyApp;

/**
 * Exception if a game cannot be found.
 * Created by alxqu on 19/04/2017.
 */
public class NotFoundGameException extends Exception {

    /**
	 * 
	 */
	private static final long serialVersionUID = 4581187618519614208L;
	private static final Logger LOGGER = LoggerFactory.getLogger(NotFoundGameException.class);

    /**
     * Constructor.
     * @param gameAlias Game alias.
     */
    public NotFoundGameException(String gameAlias) {
        super(String.format(HuxleyApp.getLanguage().getProperty("exception.game.not.found"), gameAlias));
        LOGGER.debug(String.format("%s exception make.", NotFoundGameException.class.getName()));
    }
    
    @Override
    public String toString() {
    	return super.toString();
    }
}
