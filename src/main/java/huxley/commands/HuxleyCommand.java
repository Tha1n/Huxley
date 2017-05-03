package huxley.commands;

import sx.blah.discord.handle.obj.IMessage;

import java.util.regex.Pattern;

/**
 * TODO --> Adding Comment
 * Created by alxqu on 02/05/2017.
 */
public class HuxleyCommand extends AbstractCommand {

    public HuxleyCommand() {
        // TODO
        super("Huxley", Pattern.compile(String.format("^%shuxley", Utils.COMMAND_PREFIX)));
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
