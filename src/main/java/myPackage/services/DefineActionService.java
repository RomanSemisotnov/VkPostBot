package myPackage.services;

import myPackage.entities.VkCallback;
import myPackage.enums.Action;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.regex.Pattern;

@Service
public class DefineActionService {

    @Autowired
    private Pattern addTopicCommandPattern;

    @Autowired
    private Pattern anyCommandPattern;

    @Autowired
    private Pattern getTopicCommandPattern;

    public Action define(VkCallback.BodyMessage bodyMessage, Action prevAction) {
        if (bodyMessage.getAttachment() != null) // we got attachment
            return Action.ATTACHMENT_HANDLER;

        String message = bodyMessage.getText();
        if (anyCommandPattern.matcher(message).matches()) {  // we got any command

            if (addTopicCommandPattern.matcher(message).matches())
                return Action.ADD_TOPIC_BY_COMMAND;

            if (getTopicCommandPattern.matcher(message.toLowerCase()).matches())
                return Action.GET_TOPIC_BY_COMMAND;

        }

        if (bodyMessage.getPayload().size() != 0 && bodyMessage.getPayload().containsKey("prevAction")) {

            Action action = Action.valueOf((String) bodyMessage.getPayload().get("prevAction"));
            if (action == Action.SET_ATTACHMENT_NAME)
                return Action.SET_ATTACHMENT_TOPIC_BY_KEYBOARD;

            if (action == Action.GET_TOPIC_BY_COMMAND)
                return Action.GET_ATTACHMENTS_BY_KEYBOARD;

        }

        if (prevAction == Action.ATTACHMENT_HANDLER)
            return Action.SET_ATTACHMENT_NAME;

        if (prevAction == Action.GET_ATTACHMENTS_BY_KEYBOARD)
            return Action.GET_ATTACHMENT_BY_INDEX;

        return Action.UNKNOWN_ACTION;
    }

}
