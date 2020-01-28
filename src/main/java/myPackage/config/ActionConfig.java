package myPackage.config;

import myPackage.entities.User;
import myPackage.entities.VkCallback;
import myPackage.enums.Action;
import myPackage.services.handlers.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.BiConsumer;

@Configuration
public class ActionConfig {

    @Autowired
    private AttachmentHandlerService attachmentHandlerService;

    @Autowired
    private SetAttachmentTopicByKeyboardService setTopicByKeyboardService;

    @Autowired
    private AddTopicByCommandService addTopicByCommandService;

    @Autowired
    private UnknownActionHandlerService unknownActionHandlerService;

    @Autowired
    private SetAttachmentNameService setAttachmentNameService;

    /*
     key - enum Action
     value - executable action
     */
    @Bean
    public HashMap<Action, BiConsumer<VkCallback.BodyMessage, User>> actionMap() {
        HashMap<Action, BiConsumer<VkCallback.BodyMessage, User>> actions = new HashMap<>();
        actions.put(Action.ATTACHMENT_HANDLER, attachmentHandlerService::handle);

        actions.put(Action.SET_ATTACHMENT_NAME, setAttachmentNameService::set);

        actions.put(Action.SET_ATTACHMENT_TOPIC_BY_KEYBOARD, setTopicByKeyboardService::set);
        actions.put(Action.ADD_TOPIC_BY_COMMAND, addTopicByCommandService::add);

        actions.put(Action.UNKNOWN_ACTION, unknownActionHandlerService::handle);
        return actions;
    }

    /*
   key - user_id
   value - previous user action
    */
    @Bean
    public ConcurrentHashMap<Integer, Action> prevUserActionMap() {
        return new ConcurrentHashMap<>();
    }

}
