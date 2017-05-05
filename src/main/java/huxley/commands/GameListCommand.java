package huxley.commands;

import huxley.model.exception.customexception.UnknownChoiceException;
import sx.blah.discord.handle.obj.IMessage;

import java.util.regex.Pattern;

/**
 * TODO --> Adding Comment
 * Created by alxqu on 04/05/2017.
 */
public class GameListCommand extends AbstractCommand {

    public GameListCommand() {
        super(COMMANDS_PROPERTIES.getProperty("game.list.name"),
                Pattern.compile(String.format("^%s%s\\s(all|\\+|-)(\\s((\\\".+\\\")((\\s(\\w{%s,%s}))+)|(.{%s,%s})))?",
                        COMMANDS_PROPERTIES.getProperty("commands.prefix"), COMMANDS_PROPERTIES.getProperty("game.list.prefix"),
                        COMMANDS_PROPERTIES.getProperty("min.size.aliases"), COMMANDS_PROPERTIES.getProperty("min.size.aliases"),
                        COMMANDS_PROPERTIES.getProperty("min.size.aliases"), COMMANDS_PROPERTIES.getProperty("min.size.aliases"))));
    }

    @Override
    public boolean request(IMessage message) {
        boolean result = false;
        if (super.request(message)) {
            EGameListChoice choice = EGameListChoice.ADD.getTypeByInput(matcher.group(1)); // TODO Num du groupe
            switch (choice) {
                case ALL:
                    break;
                case ADD:
                    break;
                case DELETE:
                    break;
                case UNKNOWN:
                    new UnknownChoiceException().throwException(message);
                    result = false;
                    break;
            }
        }
        return result;
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