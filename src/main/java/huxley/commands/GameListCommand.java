package huxley.commands;

import sx.blah.discord.handle.obj.IMessage;

import java.util.regex.Pattern;

/**
 * TODO --> Adding Comment
 * Created by alxqu on 04/05/2017.
 */
public class GameListCommand extends AbstractCommand {

    public GameListCommand() {
        super("GameList",
                Pattern.compile(String.format("^%s%s\\s(all|+\\s((\\w+)(\\s(\\w{%s,%s}))+)|-\\s(\\w{%s,%s})", Utils.COMMAND_PREFIX, COMMANDS_PREFIX.getProperty("game.list.prefix"), Utils.MIN_SIZE_ALIASES, Utils.MAX_SIZE_ALIASES, Utils.MIN_SIZE_ALIASES, Utils.MAX_SIZE_ALIASES)));
    }

    @Override
    public boolean request(IMessage message) {
        if (super.request(message)) {
            EGameListChoice choice = EGameListChoice.ADD.getTypeByInput(matcher.group()); // TODO Num du groupe
            switch (choice) {
                case ALL:
                    break;
                case ADD:
                    break;
                case DELETE:
                    break;
                case UNKNOWN:
                    break;
            }
        }
        return false;
    }

    @Override
    public String help() {
        return null;
    }

    @Override
    public String helpDetailed() {
        return null;
    }
}
