package huxley.commands;

import sx.blah.discord.handle.obj.IMessage;

import java.util.regex.Pattern;

/**
 * Interface that represent a Discord command
 * Created by alxqu on 13/04/2017.
 */
public interface ICommand {
	/**
	 * Get the name of the command.
	 * @return Command name.
	 */
    public String getName();
    /**
     * Get the regex pattern of the command.
     * @return Command regex pattern.
     */
    public Pattern getPattern();
    /**
     * Process a Discord message if the command is concerned by the message.
     * @param message The Discord message that need to be processed.
     * @return True if the command have processed the Discord message, false otherwise.
     */
    public boolean request(IMessage message);
    /**
     * Get lightweight help for the command.
     * @return The asked help.
     */
    public String help();
    /**
     * Get detailed help for the command.
     * @return The asked help.
     */
    public String helpDetailed();

}
