package myPackage.config;

import myPackage.entities.VkCallback;
import myPackage.enums.Action;
import myPackage.services.handlers.AddTopicByCommandService;
import myPackage.services.handlers.AttachmentHandlerService;
import myPackage.services.handlers.UnknownActionHandlerService;
import myPackage.services.handlers.UpdateAttachmentTypeByKeyboardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Collections;
import java.util.HashMap;
import java.util.function.Consumer;

@Configuration
public class ActionConfig {

    @Autowired
    private AttachmentHandlerService attachmentHandlerService;

    @Autowired
    private UpdateAttachmentTypeByKeyboardService updateAttachment;

    @Autowired
    private AddTopicByCommandService addTopicByCommandService;

    @Autowired
    private UnknownActionHandlerService unknownActionHandlerService;

    /*
     key - enum Action
     value - executable action
     */
    @Bean
    public HashMap<Action, Consumer<VkCallback.BodyMessage>> actionMap() {
        HashMap<Action, Consumer<VkCallback.BodyMessage>> actions = new HashMap<>();
        actions.put(Action.ATTACHMENT_HANDLER, attachmentHandlerService::handle);
        actions.put(Action.UPDATE_ATTACHMENT_TYPE_BY_KEYBOARD, updateAttachment::update);
        actions.put(Action.ADD_TOPIC_BY_COMMAND, addTopicByCommandService::add);
        actions.put(Action.UNKNOWN_ACTION, unknownActionHandlerService::handle);
        return (HashMap<Action, Consumer<VkCallback.BodyMessage>>) Collections.unmodifiableMap(actions);
    }

}
