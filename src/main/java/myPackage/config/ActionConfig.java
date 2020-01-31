package myPackage.config;

import myPackage.entities.User;
import myPackage.entities.VkCallback;
import myPackage.enums.Action;
import myPackage.services.handlers.*;
import myPackage.services.handlers.commands.AddTopicByCommandService;
import myPackage.services.handlers.commands.GetTopicsByCommandService;
import myPackage.services.handlers.keyboard.GetTopicAttachments;
import myPackage.services.handlers.keyboard.SetAttachmentTopicService;
import myPackage.services.handlers.keyboard.SetReadStatusService;
import myPackage.services.handlers.textual.GetAttachmentByIndexService;
import myPackage.services.handlers.textual.SetAttachmentNameService;
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
    private SetAttachmentTopicService setAttachmentTopicService;

    @Autowired
    private GetTopicAttachments getTopicAttachments;

    @Autowired
    private AddTopicByCommandService addTopicByCommandService;

    @Autowired
    private GetTopicsByCommandService getTopicsByCommandService;

    @Autowired
    private UnknownActionHandlerService unknownActionHandlerService;

    @Autowired
    private SetAttachmentNameService setAttachmentNameService;

    @Autowired
    private GetAttachmentByIndexService getAttachmentByIndexService;

    @Autowired
    private SetReadStatusService setReadStatusService;

    /*
     key - enum Action
     value - executable action
     */
    @Bean
    public HashMap<Action, BiConsumer<VkCallback.BodyMessage, User>> actionMap() {
        HashMap<Action, BiConsumer<VkCallback.BodyMessage, User>> actions = new HashMap<>();
        actions.put(Action.ATTACHMENT_HANDLER, attachmentHandlerService::handle);
        actions.put(Action.SET_ATTACHMENT_NAME, setAttachmentNameService::set);
        actions.put(Action.SET_ATTACHMENT_TOPIC, setAttachmentTopicService::set);
        actions.put(Action.ADD_TOPIC_BY_COMMAND, addTopicByCommandService::add);

        actions.put(Action.GET_TOPICS_BY_COMMAND, getTopicsByCommandService::get);
        actions.put(Action.GET_TOPIC_ATTACHMENTS, getTopicAttachments::get);
        actions.put(Action.GET_ATTACHMENT_BY_INDEX, getAttachmentByIndexService::get);
        actions.put(Action.SET_READ_STATUS, setReadStatusService::set);

        actions.put(Action.UNKNOWN_ACTION, unknownActionHandlerService::handle);
        return actions;
    }

    /*
   key - user_id
   value - action, which user chould execute
    */
    @Bean
    public ConcurrentHashMap<Integer, Action> userActionMap() {
        return new ConcurrentHashMap<>();
    }

}
