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
 * TODO --> Adding Comment
 * Created by alxqu on 26/04/2017.
 */
public class ShowPlanningCommand extends AbstractCommand {

    private static final Logger LOGGER = LoggerFactory.getLogger(ShowPlanningCommand.class);
    
    public ShowPlanningCommand() {
        super(COMMANDS_PROPERTIES.getProperty("show.planning.name"),
        		Pattern.compile(String.format("^%s%s", COMMANDS_PROPERTIES.getProperty("commands.prefix"), COMMANDS_PROPERTIES.getProperty("show.planning.prefix"))));
    }

    @Override
    public boolean request(IMessage message) {
        if (super.request(message)) {
            IUser emitter = message.getAuthor();
            Player p = DiscordClient.gameCalendar.getPlayerBasedOnDiscordID(emitter);
            if (p != null) {
                // Clean planning before print it
                p.cleanPlanning();

                String content = String.format("%s : %s", emitter.mention(), p.formatPlanning());
                DiscordMessageUtils.sendMessage(emitter.getOrCreatePMChannel(), content);
                return true;
            } else {
            	LOGGER.debug(String.format("Unknwon user %s.", emitter.getName()));
                String content = String.format("%s", HuxleyApp.LANGUAGE.getProperty("command.sp.unknow.player"));
                DiscordMessageUtils.sendMessage(emitter.getOrCreatePMChannel(), content);
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
