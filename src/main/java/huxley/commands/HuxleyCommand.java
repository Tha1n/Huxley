package huxley.commands;

import sx.blah.discord.handle.obj.IMessage;

import java.util.regex.Pattern;

/**
 * Discord Command for global Huxley requests
 * Created by alxqu on 02/05/2017.
 */
public class HuxleyCommand extends AbstractCommand {

	/**
	 * Constructor.
	 */
    public HuxleyCommand() {
        super(COMMANDS_PROPERTIES.getProperty("huxley.name"),
        		Pattern.compile(String.format("^%s%s", COMMANDS_PROPERTIES.getProperty("commands.prefix"), COMMANDS_PROPERTIES.getProperty("huxley.prefix"))));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean request(IMessage message) {
        if (super.request(message)) {
            // TODO
        }
        return false;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String help() {
        // TODO
        return null;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String helpDetailed() {
        // TODO
        return null;
    }
}
