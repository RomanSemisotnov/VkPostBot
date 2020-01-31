package myPackage.enums;

public enum Action {

    // first actions chain
    ATTACHMENT_HANDLER(null),
    SET_ATTACHMENT_NAME(null),
    SET_ATTACHMENT_TOPIC(null),

    //second actions chain
    GET_TOPICS_BY_COMMAND("/темы"),
    GET_TOPIC_ATTACHMENTS(null),
    GET_ATTACHMENT_BY_INDEX(null),
    SET_READ_STATUS(null),
    //-------------------------------

    ADD_TOPIC_BY_COMMAND("+"),
    UNKNOWN_ACTION(null);

    private String command;

    Action(String command) {
        this.command = command;
    }

    public String getCommand() {
        return command;
    }

}
