package huxley.model.discord;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import sx.blah.discord.handle.obj.IChannel;
import sx.blah.discord.util.*;

/**
 * Utils class to easily send Discord message.
 * Created by alxqu on 24/04/2017.
 */
public class DiscordMessageUtils {
    private static final Logger LOGGER = LoggerFactory.getLogger(DiscordMessageUtils.class);

    /**
     * Sending message to Discord guild.
     * @param channel Targeted channel.
     * @param content Content of message.
     */
    public static void sendMessage(IChannel channel, String content) {
        RequestBuffer.request(() -> {
            try {
                new MessageBuilder(DiscordClient.getDiscordClient())
                        .withChannel(channel)
                        .withContent(content)
                        .build();
            } catch(RateLimitException e){
                LOGGER.warn(String.format("%s", e));
                throw e;
            } catch (DiscordException e){
                LOGGER.error(String.format("%s", e));
            } catch(MissingPermissionsException e){
                LOGGER.warn(String.format("Huxley does not have enough permission to make request. %s", e));
            } catch(Exception e){
               LOGGER.error(String.format("%s", e));
            }
            return null;
        });
    }
}
