package huxley.model.user;

import java.time.LocalDateTime;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * TODO --> Adding Comment
 * Created by alxqu on 22/04/2017.
 */
public class GameTimeSlot {

    @SuppressWarnings("unused")
	private static Logger LOGGER = LoggerFactory.getLogger(GameTimeSlot.class);
    private LocalDateTime beginDate;
    private LocalDateTime endingDate;

    public GameTimeSlot(LocalDateTime start, LocalDateTime end) {
        this.beginDate = start;
        this.endingDate = end;
    }

    public LocalDateTime getBeginDate() {
        return beginDate;
    }

    public LocalDateTime getEndingDate() {
        return endingDate;
    }

}
