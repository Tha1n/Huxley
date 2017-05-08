package huxley.commands;

import sx.blah.discord.handle.obj.IMessage;

import java.util.regex.Pattern;

/**
 * Discord Command for global Huxley requests
 * Created by alxqu on 02/05/2017.
 */
public class HuxleyCommand extends AbstractCommand {

    private static final int HELP_GROUP = 1;
    private static final int HELP_DETAILED_GROUP = 2;

	/**
	 * Constructor.
	 */
    public HuxleyCommand() {
        super(COMMANDS_PROPERTIES.getProperty("huxley.name"),
        		Pattern.compile(String.format("^%s%s(\\shelp(++)?)?", COMMANDS_PROPERTIES.getProperty("commands.prefix"), COMMANDS_PROPERTIES.getProperty("huxley.prefix"))));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean request(IMessage message) {
        if (super.request(message)) {
            if (matcher.group(HELP_DETAILED_GROUP) != null) {
                //TODO
            } else if (matcher.group(HELP_GROUP) != null) {
                //TODO
            } else {
                //TODO
            }
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
