package huxley.model.user;

import java.time.LocalDateTime;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Class that represent a gaming time slot.
 * Created by alxqu on 22/04/2017.
 */
public class GameTimeSlot {

    @SuppressWarnings("unused")
	private static Logger LOGGER = LoggerFactory.getLogger(GameTimeSlot.class);
    private LocalDateTime beginDate;
    private LocalDateTime endingDate;

    /**
     * Constructor.
     * @param start The starting date.
     * @param end The ending date.
     */
    public GameTimeSlot(LocalDateTime start, LocalDateTime end) {
        this.beginDate = start;
        this.endingDate = end;
    }

    /**
     * Get begin date for this time slot.
     * @return The begin date.
     */
    public LocalDateTime getBeginDate() {
        return beginDate;
    }

    /**
     * Get ending date for this time slot.
     * @return The ending date.
     */
    public LocalDateTime getEndingDate() {
        return endingDate;
    }

}
