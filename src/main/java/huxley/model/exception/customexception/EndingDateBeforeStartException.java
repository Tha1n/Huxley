package huxley.model.exception.customexception;

import huxley.HuxleyApp;
import huxley.model.discord.DiscordMessageUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import sx.blah.discord.handle.obj.IMessage;

import java.time.LocalDateTime;

/**
 * Exception if ending date is before starting date.
 * Created by alxqu on 22/04/2017.
 */
public class EndingDateBeforeStartException implements IException {

	private static final Logger LOGGER = LoggerFactory.getLogger(EndingDateBeforeStartException.class);
	private final LocalDateTime begin;
	private final LocalDateTime end;

	/**
	 * Constructor
	 * @param begin Begin hour.
	 * @param end Ending hour.
	 */
	public EndingDateBeforeStartException(LocalDateTime begin, LocalDateTime end) {
		this.begin = begin;
		this.end = end;
	}

	@Override
	public void throwException(IMessage message) {
		LOGGER.debug(String.format("%s is thrown.", EndingDateBeforeStartException.class.getName()));
		DiscordMessageUtils.sendMessage(message.getChannel(), String.format(HuxleyApp.getLanguage().getProperty("exception.ending.before.start"), end, begin));
	}
}
