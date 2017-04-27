package huxley.model.exception.customexception;

import huxley.HuxleyApp;
import huxley.model.discord.DiscordMessageUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import sx.blah.discord.handle.obj.IMessage;

import java.time.LocalDateTime;

/**
 * TODO --> Adding Comment
 * Created by alxqu on 22/04/2017.
 */
public class EndingDateBeforeStartException implements IException {

    private final static Logger LOGGER = LoggerFactory.getLogger(EndingDateBeforeStartException.class);
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
        DiscordMessageUtils.sendMessage(message.getChannel(), String.format(HuxleyApp.LANGUAGE.getProperty("exception.ending.before.start"), end, begin));
    }
}
