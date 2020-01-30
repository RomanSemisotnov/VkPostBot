package myPackage.enums;

public enum Action {

    // first actions chain
    ATTACHMENT_HANDLER,
    SET_ATTACHMENT_NAME,
    SET_ATTACHMENT_TOPIC_BY_KEYBOARD, ADD_TOPIC_BY_COMMAND,

    //second actions chain
    GET_TOPIC_BY_COMMAND,
    GET_ATTACHMENTS_BY_KEYBOARD,
    GET_ATTACHMENT_BY_INDEX,
    //-------------------------------
    UNKNOWN_ACTION;

}
