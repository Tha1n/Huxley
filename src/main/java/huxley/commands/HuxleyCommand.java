package huxley.commands;

import sx.blah.discord.handle.obj.IMessage;

import java.util.regex.Pattern;

/**
 * TODO --> Adding Comment
 * Created by alxqu on 02/05/2017.
 */
public class HuxleyCommand extends AbstractCommand {

    public HuxleyCommand() {
        super(COMMANDS_PROPERTIES.getProperty("huxley.name"),
        		Pattern.compile(String.format("^%s%s", COMMANDS_PROPERTIES.getProperty("commands.prefix"), COMMANDS_PROPERTIES.getProperty("huxley.prefix"))));
    }

    @Override
    public boolean request(IMessage message) {
        if (super.request(message)) {
            // TODO
        }
        return false;
    }

    @Override
    public String help() {
        // TODO
        return null;
    }

    @Override
    public String helpDetailed() {
        // TODO
        return null;
    }
}
