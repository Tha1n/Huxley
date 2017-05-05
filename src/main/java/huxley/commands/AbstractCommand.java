package huxley.commands;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import sx.blah.discord.handle.obj.IMessage;

import java.io.IOException;
import java.util.Properties;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * An abstract Discord comment that partially implement {@link huxley.commands.ICommand}
 * Created by alxqu on 17/04/2017.
 */
public abstract class AbstractCommand implements ICommand {
    private static Logger LOGGER = LoggerFactory.getLogger(AbstractCommand.class);

    private String name;
    private Pattern regex;
    protected Matcher matcher;
    protected static Properties COMMANDS_PROPERTIES = new Properties();
    static {
        try {
            COMMANDS_PROPERTIES.load(AbstractCommand.class.getResourceAsStream("/commands.properties"));
        } catch (IOException e) {
            LOGGER.error(String.format("Error occurred while loading commands properties. Huxley will exit. Error: %s", e));
            System.exit(-1);
        }
    }

    /**
     * Constructor.
     * @param name Command name.
     * @param regex Command regex pattern.
     */
    public AbstractCommand(String name, Pattern regex) {
        super();

        this.name = name;
        this.regex = regex;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean request(IMessage message) {
        matcher = regex.matcher(message.getContent());
        boolean patternFound = matcher.find();

        return patternFound;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getName() {
        return this.name;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Pattern getPattern() {
        return this.regex;
    }

}
