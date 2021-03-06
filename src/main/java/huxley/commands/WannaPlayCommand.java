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
 * Discord Command to set a game planning for a {@link sx.blah.discord.handle.obj.IUser}.
 * Created by alxqu on 18/04/2017.
 */
public class WannaPlayCommand extends AbstractCommand {

    private static final Logger LOGGER = LoggerFactory.getLogger(WannaPlayCommand.class);
    DateTimeFormatter dtf;

    private static int GROUP_ALIAS_NUMBER = 3;
    private static int GROUP_HOUR_START_NUMBER = 4;
    private static int GROUP_HOUR_END_NUMBER = 5;
    private static int GROUP_DATE_NUMBER = 6;
    private static int GROUP_YEAR_NUMBER = 7;

    /**
     * Constructor.
     */
    public WannaPlayCommand() {
        super("WannaPlay",
                Pattern.compile(String.format("^(%s%s)\\s((\\w{%s,%s})\\s(\\d{2}:\\d{2})\\s->\\s(\\d{2}:\\d{2})(\\s\\d{2}/\\d{2}(/\\d{4})?)?)", COMMANDS_PROPERTIES.getProperty("commands.prefix"), COMMANDS_PROPERTIES.getProperty("wanna.play.prefix"), COMMANDS_PROPERTIES.getProperty("min.size.aliases"), COMMANDS_PROPERTIES.getProperty("max.size.aliases"))));

        dtf = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");
    }

    /**
     * {@inheritDoc}
     */
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
                    DecimalFormat mFormat = new DecimalFormat("00");
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
                Long guildId = message.getGuild().getLongID();
                // Register player to planning
                DiscordClient.getGamecalendar().addPlanningForPlayer(emitter, gameAlias, start, end, guildId);

                // Get available players for this game for this time
                List<IUser> availablePlayers = DiscordClient.getGamecalendar().getAvailablePlayers(emitter, gameAlias, start, end, guildId);

                // Send message with players list
                if (!availablePlayers.isEmpty()) {
                    String content = formatMessageToSend(emitter, gameAlias, availablePlayers);
                    DiscordMessageUtils.sendMessage(message.getChannel(), content);
                } else {
                    String content = String.format(HuxleyApp.getLanguage().getProperty("command.wp.user.planning.registered"), emitter.mention());
                    DiscordMessageUtils.sendMessage(message.getChannel(), content);
                }
            } catch (NotFoundGameException e) {
                DiscordMessageUtils.sendMessage(message.getChannel(), String.format("%s", e));
                LOGGER.error(String.format("%s", e));
                return false;
            }
            return true;
        }
        return false;
    }

    /**
     * Build the Huxley response for the command.
     *
     * @param emitter          {@link sx.blah.discord.handle.obj.IUser} object that is the émitter of the original message.
     * @param gameAlias        String that is the game alias.
     * @param availablePlayers List of {@link sx.blah.discord.handle.obj.IUser}.
     * @return Huxley response string.
     */
    private String formatMessageToSend(IUser emitter, String gameAlias, List<IUser> availablePlayers) {
        StringBuilder result = new StringBuilder();

        result.append(String.format("%s\n", emitter.mention()));
        for (IUser player :
                availablePlayers) {
            result.append(String.format("%s, ", player.mention()));
        }
        result.append(String.format(HuxleyApp.getLanguage().getProperty("command.wp.users.available.play"), gameAlias.toUpperCase()));

        return result.toString();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String help() {
        return HuxleyApp.getLanguage().getProperty("command.wp.help");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String helpDetailed() {
        return String.format(HuxleyApp.getLanguage().getProperty("command.wp.help.detailed"), COMMANDS_PROPERTIES.getProperty("commands.prefix"), COMMANDS_PROPERTIES.getProperty("wanna.play.prefix"));
    }
}
