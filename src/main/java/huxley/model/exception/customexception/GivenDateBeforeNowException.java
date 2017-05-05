package huxley.model.exception.customexception;

import java.time.LocalDateTime;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import huxley.HuxleyApp;
import huxley.model.discord.DiscordMessageUtils;
import sx.blah.discord.handle.obj.IMessage;

/**
 * TODO --> Adding Comment
 * Created by alxqu on 26/04/2017.
 */
public class GivenDateBeforeNowException implements IException {

	private static final Logger LOGGER = LoggerFactory.getLogger(GivenDateBeforeNowException.class);
    LocalDateTime start;
    LocalDateTime end;

    public GivenDateBeforeNowException(LocalDateTime start, LocalDateTime end) {
        this.start = start;
        this.end = end;
    }

    @Override
    public void throwException(IMessage message) {
		LOGGER.debug(String.format("%s is thrown", GivenDateBeforeNowException.class.getName()));
        DiscordMessageUtils.sendMessage(message.getChannel(), String.format(HuxleyApp.LANGUAGE.getProperty("exception.date.before.now"), start, end));
    }
}
