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

    public Action define(VkCallback.BodyMessage bodyMessage, Action neededAction) {
        if (bodyMessage.getAttachment() != null) // we got attachment
            return Action.ATTACHMENT_HANDLER;

        String message = bodyMessage.getText();
        if (anyCommandPattern.matcher(message).matches()) {  // we got any command

            if (addTopicCommandPattern.matcher(message).matches())
                return Action.ADD_TOPIC_BY_COMMAND;

            if (getTopicCommandPattern.matcher(message.toLowerCase()).matches())
                return Action.GET_TOPICS_BY_COMMAND;

        }

        if (bodyMessage.getPayload().containsKey("neededAction")) // we got message from keyboard
            return Action.valueOf((String) bodyMessage.getPayload().get("neededAction"));

        if (neededAction != null)
            return neededAction;

        return Action.UNKNOWN_ACTION;
    }

}
