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

    public Action define(VkCallback.BodyMessage bodyMessage) {
        if (bodyMessage.getAttachments().size() != 0) {
            return Action.ATTACHMENT_HANDLER;
        } else if (bodyMessage.getPayload().size() != 0
                && bodyMessage.getPayload().containsKey("attachment_ids")
                && bodyMessage.getPayload().containsKey("topic_id")) {
            return Action.UPDATE_ATTACHMENT_TYPE_BY_KEYBOARD;
        } else if (addTopicCommandPattern.matcher(bodyMessage.getText()).matches()) {
            return Action.ADD_TOPIC_BY_COMMAND;
        } else {
            return Action.UNKNOWN_ACTION;
        }
    }

}
