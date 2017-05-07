package huxley.commands;

/**
 * Enum to reprensent all choice available for {@link huxley.commands.GameListCommand}.
 * Created by alxqu on 04/05/2017.
 */
public enum EGameListChoice {
    ALL("all"),
    ADD("+"),
    DELETE("-"),
    UNKNOWN("UNKNOWN");

    private String choice;
    /**
     * Constructor
     * @param choice String for this choice.
     */
    EGameListChoice(String choice) {
        this.choice = choice;
    }

    /**
     * Get the current choice
     * @return
     */
    private String getChoice() {
        return choice;
    }

    /**
     * Get the {@link EGameListChoice} for the input string.
     * @param input Input string that we have to search.
     * @return A {@link EGameListChoice} object.
     */
    public EGameListChoice getTypeByInput(String input) {
        if (ALL.getChoice().equals(input)) {
            return ALL;
        } else if (ADD.getChoice().equals(input)) {
            return ADD;
        } else if (DELETE.getChoice().equals(input)) {
            return DELETE;
        } else {
            return UNKNOWN;
        }
    }
}
