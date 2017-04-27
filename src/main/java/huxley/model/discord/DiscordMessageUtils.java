package huxley.model.discord;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import sx.blah.discord.handle.obj.IChannel;
import sx.blah.discord.util.*;

/**
 * TODO --> Adding Comment
 * Created by alxqu on 24/04/2017.
 */
public class DiscordMessageUtils {
    private static Logger LOGGER = LoggerFactory.getLogger(DiscordMessageUtils.class);

    public static void sendMessage(IChannel channel, String content) {
        RequestBuffer.request(() -> {
            try {
                new MessageBuilder(DiscordClient.getDiscordClient())
                        .withChannel(channel)
                        .withContent(content)
                        .build();
            } catch(RateLimitException e){
                LOGGER.warn(e.getMessage());
                throw e;
            } catch (DiscordException e){
                LOGGER.error(e.getErrorMessage());
            } catch(MissingPermissionsException e){
                LOGGER.warn("Huxley does not have enough permission to make request.");
            } catch(Exception e){
               LOGGER.error(e.getMessage());
            }
            return null;
        });
    }
}
