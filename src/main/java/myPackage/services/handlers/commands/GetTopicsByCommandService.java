package myPackage.services.handlers.commands;

import myPackage.entities.Keyboard;
import myPackage.entities.Topic;
import myPackage.entities.User;
import myPackage.entities.VkCallback;
import myPackage.enums.Action;
import myPackage.services.handlers.BaseHandler;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GetTopicsByCommandService extends BaseHandler {

    public void get(VkCallback.BodyMessage body, User user) {
        System.out.println("Обрабатываем получение топиков по команде");

        Keyboard keyboard = Keyboard.keyboardForGetTopic(user.getTopics());

        messageSenderService.send(user.getVkId(),
                "Выберите топик, который хотите посмотреть", keyboard);

        lastIncommingAttachmentMap.remove(user.getVkId());
        userActionMap.put(user.getVkId(), Action.GET_TOPIC_ATTACHMENTS);
    }

}
