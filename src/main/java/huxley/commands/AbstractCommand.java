package huxley.commands;

import sx.blah.discord.handle.obj.IMessage;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * TODO --> Adding Comment
 * Created by alxqu on 17/04/2017.
 */
public abstract class AbstractCommand implements ICommand {

    private String name;
    private Pattern regex;
    protected Matcher matcher;

    public AbstractCommand(String name, Pattern regex) {
        super();

        this.name = name;
        this.regex = regex;
    }

    public boolean request(IMessage message) {
        matcher = regex.matcher(message.getContent());
        boolean patternFound = matcher.find();

        return patternFound;
    }

    @Override
    public String getName() {
        return this.name;
    }

    @Override
    public Pattern getPattern() {
        return this.regex;
    }

}
