package huxley.commands;

import huxley.model.discord.DiscordClient;
import huxley.model.discord.DiscordMessageUtils;
import huxley.model.user.Player;
import sx.blah.discord.handle.obj.IMessage;
import sx.blah.discord.handle.obj.IUser;

import java.util.regex.Pattern;

/**
 * TODO --> Adding Comment
 * Created by alxqu on 26/04/2017.
 */
public class ShowPlanningCommand extends AbstractCommand {

    public ShowPlanningCommand() {
        super("ShowPlanning", Pattern.compile(String.format("^%ssp", Utils.COMMAND_PREFIX)));
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
            } /* else {
                String content = String.format("%s : I don't know you (yet)!", emitter.mention());
                DiscordMessageUtils.sendMessage(message.getChannel(), content);
            } */
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
