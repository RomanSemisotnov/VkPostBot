package myPackage.config;

import myPackage.entities.VkCallback;
import myPackage.enums.Action;
import myPackage.services.AddTopicService;
import myPackage.services.SendTopicKeyboardForUserService;
import myPackage.services.UpdateAttachmentTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.function.Consumer;

@Configuration
public class ActionConfig {

    @Autowired
    private SendTopicKeyboardForUserService sendKeyboardTopic;

    @Autowired
    private UpdateAttachmentTypeService updateAttachment;

    @Autowired
    private AddTopicService addTopicService;

    /*
     key - enum Action
     value - executable action
     */
    @Bean
    public HashMap<Action, Consumer<VkCallback.BodyMessage>> actionMap() {
        HashMap<Action, Consumer<VkCallback.BodyMessage>> actions = new HashMap<>();
        actions.put(Action.SEND_KEYBOARD_TOPIC_FOR_USER, sendKeyboardTopic::send);
        actions.put(Action.UPDATE_ATTACHMENT_TYPE, updateAttachment::update);
        actions.put(Action.ADD_TOPIC, addTopicService::add);
        //  actions.put(Action.UNKNOWN_ACTION);
        return actions;
    }

}
