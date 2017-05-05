package huxley.commands;

/**
 * TODO --> Adding Comment
 * Created by alxqu on 04/05/2017.
 */
public enum EGameListChoice {
    ALL("all"),
    ADD("+"),
    DELETE("-"),
    UNKNOWN("UNKNOWN");

    private String choice;
    EGameListChoice(String choice) {
        this.choice = choice;
    }

    public String getChoice() {
        return choice;
    }

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
