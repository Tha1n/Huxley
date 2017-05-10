package huxley.commands;

import java.util.regex.Pattern;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import huxley.HuxleyApp;
import huxley.model.discord.DiscordClient;
import huxley.model.discord.DiscordMessageUtils;
import huxley.model.user.Player;
import sx.blah.discord.handle.obj.IMessage;
import sx.blah.discord.handle.obj.IUser;

/**
 * Discord Command to have the game planning for a player.
 * Created by alxqu on 26/04/2017.
 */
public class ShowPlanningCommand extends AbstractCommand {

    private static final Logger LOGGER = LoggerFactory.getLogger(ShowPlanningCommand.class);
    
    /**
     * Constructor.
     */
    public ShowPlanningCommand() {
        super(COMMANDS_PROPERTIES.getProperty("show.planning.name"),
        		Pattern.compile(String.format("^%s%s", COMMANDS_PROPERTIES.getProperty("commands.prefix"), COMMANDS_PROPERTIES.getProperty("show.planning.prefix"))));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean request(IMessage message) {
        if (super.request(message)) {
            IUser emitter = message.getAuthor();
            Player p = DiscordClient.getGamecalendar().getPlayerBasedOnDiscordID(emitter);
            if (p != null) {
                // Clean planning before print it
                p.cleanPlanning();

                String content = String.format("%s : %s", emitter.mention(), p.formatPlanning());
                DiscordMessageUtils.sendMessage(emitter.getOrCreatePMChannel(), content);
                return true;
            } else {
            	LOGGER.debug(String.format("Unknwon user %s.", emitter.getName()));
                String content = String.format("%s", HuxleyApp.getLanguage().getProperty("command.sp.unknown.player"));
                DiscordMessageUtils.sendMessage(emitter.getOrCreatePMChannel(), content);
            }
        }
        return false;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String help() {
        return HuxleyApp.getLanguage().getProperty("command.sp.help");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String helpDetailed() {
        return String.format(HuxleyApp.getLanguage().getProperty("command.sp.help.detailed"), COMMANDS_PROPERTIES.getProperty("commands.prefix"), COMMANDS_PROPERTIES.getProperty("show.planning.prefix"));
    }
}
