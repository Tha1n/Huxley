package huxley.commands;

import huxley.HuxleyApp;
import huxley.model.discord.DiscordClient;
import huxley.model.discord.DiscordMessageUtils;
import sx.blah.discord.handle.obj.IMessage;

import java.util.regex.Pattern;

/**
 * Discord Command for global Huxley requests
 * Created by alxqu on 02/05/2017.
 */
public class HuxleyCommand extends AbstractCommand {

    private static final int HELP_GROUP = 1;
    private static final int HELP_DETAILED_GROUP = 2;
    private static final int COMMAND_NAME_GROUP = 3;

    /**
     * Constructor.
     */
    public HuxleyCommand() {
        super(COMMANDS_PROPERTIES.getProperty("huxley.name"),
                Pattern.compile(String.format("^%s%s(\\shelp(\\+\\+)?)?\\s*(\\w+)?", COMMANDS_PROPERTIES.getProperty("commands.prefix"), COMMANDS_PROPERTIES.getProperty("huxley.prefix"))));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean request(IMessage message) {
        if (super.request(message)) {
            String gameName = matcher.group(COMMAND_NAME_GROUP);

            if (matcher.group(HELP_DETAILED_GROUP) != null || matcher.group(HELP_GROUP) != null) {
                ICommand cmd;
                if (gameName == null ||
                        (cmd = DiscordClient.getCommands().stream().filter(c -> c.getName().equalsIgnoreCase(gameName)).findFirst().orElse(null)) == null) {
                    String content = HuxleyApp.getLanguage().getProperty("command.huxley.unknown.command");
                    DiscordMessageUtils.sendMessage(message.getChannel(), content);
                    return false;
                }

                String content;
                if (matcher.group(HELP_DETAILED_GROUP) != null) {
                    content = cmd.helpDetailed();
                } else {
                    content = cmd.help();
                }
                DiscordMessageUtils.sendMessage(message.getChannel(), content);
                return true;
            } else {
                String content = String.format(HuxleyApp.getLanguage().getProperty("command.huxley.global"),
                        formatCommandsNameDisplay());
                DiscordMessageUtils.sendMessage(message.getChannel(), content);
                return true;
            }
        }
        return false;
    }

    /**
     * Format a displayable list of commands.
     * @return
     */
    private String formatCommandsNameDisplay() {
        StringBuilder result = new StringBuilder();

        for (ICommand cmd: DiscordClient.getCommands()) {
            result.append(String.format("- %s\n", cmd.getName()));
        }

        return result.toString();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String help() {
        return HuxleyApp.getLanguage().getProperty("command.huxley.help");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String helpDetailed() {
        return String.format(HuxleyApp.getLanguage().getProperty("command.huxley.help.detailed"),
                COMMANDS_PROPERTIES.getProperty("commands.prefix"), COMMANDS_PROPERTIES.getProperty("huxley.prefix"),
                COMMANDS_PROPERTIES.getProperty("commands.prefix"), COMMANDS_PROPERTIES.getProperty("huxley.prefix"));
    }
}
