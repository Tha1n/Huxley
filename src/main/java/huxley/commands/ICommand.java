package huxley.commands;

import sx.blah.discord.handle.obj.IMessage;

import java.util.regex.Pattern;

/**
 * TODO --> Adding Comment
 * Created by alxqu on 13/04/2017.
 */
public interface ICommand {
    public String getName();
    public Pattern getPattern();
    public boolean request(IMessage message);
    public String help();
    public String helpDetailed();

}
