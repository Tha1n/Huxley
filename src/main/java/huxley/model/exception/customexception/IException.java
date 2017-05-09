package huxley.model.exception.customexception;

import sx.blah.discord.handle.obj.IMessage;

/**
 * Global exception.
 * Created by alxqu on 22/04/2017.
 */
public interface IException {
    /**
     * Throw exception through Discord channel.
     * @param message The source message to retrieve channel.
     */
    public void throwException(IMessage message);
}
