package huxley.model.exception.customexception;

import huxley.HuxleyApp;
import huxley.commands.EGameListChoice;
import huxley.model.discord.DiscordMessageUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import sx.blah.discord.handle.obj.IMessage;

/**
 * TODO --> Adding Comment
 * Created by alxqu on 05/05/2017.
 */
public class UnknownChoiceException implements IException {

    private static final Logger LOGGER = LoggerFactory.getLogger(UnknownChoiceException.class);

    @Override
    public void throwException(IMessage message) {
        LOGGER.debug(String.format("%s is thrown", UnknownChoiceException.class.getName()));
        DiscordMessageUtils.sendMessage(message.getChannel(), String.format(HuxleyApp.LANGUAGE.getProperty("exception.unknown.choice")));
    }
}
