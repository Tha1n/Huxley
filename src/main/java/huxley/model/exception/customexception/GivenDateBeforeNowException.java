package huxley.model.exception.customexception;

import huxley.HuxleyApp;
import huxley.model.discord.DiscordMessageUtils;
import sx.blah.discord.handle.obj.IMessage;

import java.time.LocalDateTime;

/**
 * TODO --> Adding Comment
 * Created by alxqu on 26/04/2017.
 */
public class GivenDateBeforeNowException implements IException {

    LocalDateTime start;
    LocalDateTime end;

    public GivenDateBeforeNowException(LocalDateTime start, LocalDateTime end) {
        this.start = start;
        this.end = end;
    }

    @Override
    public void throwException(IMessage message) {
        DiscordMessageUtils.sendMessage(message.getChannel(), String.format(HuxleyApp.LANGUAGE.getProperty("exception.date.before.now"), start, end));
    }
}
