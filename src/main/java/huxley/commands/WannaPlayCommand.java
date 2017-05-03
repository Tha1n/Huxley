package huxley.commands;

import huxley.HuxleyApp;
import huxley.model.discord.DiscordClient;
import huxley.model.discord.DiscordMessageUtils;
import huxley.model.exception.classicexception.NotFoundGameException;
import huxley.model.exception.customexception.EndingDateBeforeStartException;
import huxley.model.exception.customexception.GivenDateBeforeNowException;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import sx.blah.discord.handle.obj.IMessage;
import sx.blah.discord.handle.obj.IUser;

import java.text.DecimalFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.regex.Pattern;

/**
 * TODO --> Adding Comment
 * Created by alxqu on 18/04/2017.
 */
public class WannaPlayCommand extends AbstractCommand {

    private Logger LOGGER = LoggerFactory.getLogger(WannaPlayCommand.class);
    DateTimeFormatter dtf;

    private static int GROUP_ALIAS_NUMBER = 3;
    private static int GROUP_HOUR_START_NUMBER = 4;
    private static int GROUP_HOUR_END_NUMBER = 5;
    private static int GROUP_DATE_NUMBER = 6;
    private static int GROUP_YEAR_NUMBER = 7;

    public WannaPlayCommand() {
        super("WannaPlay",
                Pattern.compile(String.format("^(%s%s)\\s((\\w{%s,%s})\\s(\\d{2}:\\d{2})\\s->\\s(\\d{2}:\\d{2})(\\s\\d{2}/\\d{2}(/\\d{4})?)?)", Utils.COMMAND_PREFIX, COMMANDS_PREFIX.getProperty("wanna.play.prefix"), Utils.MIN_SIZE_ALIASES, Utils.MAX_SIZE_ALIASES)));

        dtf = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");
    }

    @Override
    public boolean request(IMessage message) {
        if (super.request(message)) {
            try {
                LocalDateTime start;
                LocalDateTime end;

                String datePart = this.matcher.group(GROUP_DATE_NUMBER);
                String yearPart = this.matcher.group(GROUP_YEAR_NUMBER);
                // If year was not given
                if (yearPart == null) {
                    yearPart = String.valueOf(LocalDateTime.now().getYear());
                }
                // If date was not given
                if (datePart == null) {
                    LocalDateTime today = LocalDateTime.now();
                    DecimalFormat mFormat= new DecimalFormat("00");
                    datePart = String.format("%s/%s", mFormat.format(today.getDayOfMonth()), mFormat.format(today.getMonthValue()));
                }

                start = LocalDateTime.parse(String.format("%s/%s%s%s", datePart, yearPart, StringUtils.SPACE, this.matcher.group(GROUP_HOUR_START_NUMBER)), dtf);
                end = LocalDateTime.parse(String.format("%s/%s%s%s", datePart, yearPart, StringUtils.SPACE, this.matcher.group(GROUP_HOUR_END_NUMBER)), dtf);

                if (start.isAfter(end)) {
                    new EndingDateBeforeStartException(start, end).throwException(message);
                    return false;
                } else if (start.isBefore(LocalDateTime.now()) || end.isBefore(LocalDateTime.now())) {
                    new GivenDateBeforeNowException(start, end).throwException(message);
                    return false;
                }
                String gameAlias = this.matcher.group(GROUP_ALIAS_NUMBER);

                IUser emitter = message.getAuthor();
                // Register player to planning
                DiscordClient.gameCalendar.addPlanningForPlayer(emitter, gameAlias, start, end);

                // Get available players for this game for this time
                List<IUser> availablePlayers = DiscordClient.gameCalendar.getAvailablePlayers(emitter, gameAlias, start, end);

                // Send message with players list
                if (availablePlayers.size() > 0) {
                    String content = formatMessageToSend(emitter, gameAlias, availablePlayers);
                    DiscordMessageUtils.sendMessage(message.getChannel(), content);
                } else {
                    String content = String.format(HuxleyApp.LANGUAGE.getProperty("command.wp.user.planning.registered"), emitter.mention());
                    DiscordMessageUtils.sendMessage(message.getChannel(), content);
                }
            } catch (NotFoundGameException e) {
                DiscordMessageUtils.sendMessage(message.getChannel(), e.getMessage());
                LOGGER.error(e.getMessage());
                return false;
            }
            return true;
        }
        return false;
    }

    private String formatMessageToSend(IUser emitter, String gameAlias, List<IUser> availablePlayers) {
        StringBuilder result = new StringBuilder();

        result.append(String.format("%s\n", emitter.mention()));
        for (IUser player :
                availablePlayers) {
            result.append(String.format("%s, ", player.mention()));
        }
        result.append(String.format(HuxleyApp.LANGUAGE.getProperty("command.wp.users.available.play"), gameAlias.toUpperCase()));

        return result.toString();
    }

    @Override
    public String help() {
        return HuxleyApp.LANGUAGE.getProperty("command.wp.help");
    }

    @Override
    public String helpDetailed() {
        return HuxleyApp.LANGUAGE.getProperty("command.wp.help.detailed");
    }
}
