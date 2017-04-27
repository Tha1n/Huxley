package huxley.model.exception.customexception;

import huxley.commands.ICommand;
import sx.blah.discord.handle.obj.IMessage;

/**
 * TODO --> Adding Comment
 * Created by alxqu on 22/04/2017.
 */
public interface IException {
    public void throwException(IMessage message);
}
