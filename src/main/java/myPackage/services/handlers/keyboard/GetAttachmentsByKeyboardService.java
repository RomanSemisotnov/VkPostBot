package myPackage.services.handlers.keyboard;

import myPackage.DAO.TopicDao;
import myPackage.entities.*;
import myPackage.enums.Action;
import myPackage.services.handlers.BaseHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

@Service
public class GetAttachmentsByKeyboardService extends BaseHandler {

    @Autowired
    private TopicDao topicDao;

    @Autowired
    private ConcurrentHashMap<Integer, Map<Integer, Integer>> lastAttachmentsByOrderMap;

    public void get(VkCallback.BodyMessage body, User user) {
        System.out.println("Получаем вложения с клавиатуры");

        Integer topicId = (Integer) body.getPayload().get("topic_id");

        Topic topic = topicDao.findOne(topicId);

        List<Attachment> attachments = topic.getAttachments();

        StringBuilder message = new StringBuilder("Введите номер вложения, которое хотите просмотреть: <br>");

        AtomicInteger i = new AtomicInteger(0);
        Map<Integer, Integer> indexForAttachmentId = new HashMap<>();
        attachments.forEach((attachment) -> {
            message.append(i.getAndIncrement()).append(". ").append(attachment.getName()).append("<br>");
            indexForAttachmentId.put(i.get(), attachment.getId());
        });

        // написать клаву/текстовый ответ + продумать схему для клавиатуры
        //заменить String выше на StringBuilder
        messageSenderService.send(user.getVkId(), message.toString());
        prevUserActionMap.put(user.getId(), Action.GET_ATTACHMENTS_BY_KEYBOARD);
        lastAttachmentsByOrderMap.put(user.getId(), indexForAttachmentId);
    }

}
