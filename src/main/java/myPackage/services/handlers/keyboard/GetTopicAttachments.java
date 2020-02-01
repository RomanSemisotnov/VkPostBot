package myPackage.services.handlers.keyboard;

import myPackage.DAO.AttachmentDao;
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
public class GetTopicAttachments extends BaseHandler {

    @Autowired
    private AttachmentDao attachmentDao;

    @Autowired
    private ConcurrentHashMap<Integer, Map<Integer, Integer>> lastAttachmentsByOrderMap;

    public void get(VkCallback.BodyMessage body, User user) {
        System.out.println("Получаем вложения с клавиатуры");

        Integer topicId = (Integer) body.getPayload().get("topic_id");

        List<Attachment> attachments = attachmentDao.getNotRead(topicId);

        if (attachments.isEmpty()) {
            messageSenderService.send(user.getVkId(), "В этом топике непрочитанные вложения отсутствуют.");
            userActionMap.remove(user.getId());
            return;
        }

        StringBuilder message = new StringBuilder("Введите номер вложения, которое хотите просмотреть: <br>");

        AtomicInteger i = new AtomicInteger(0);
        Map<Integer, Integer> indexForAttachmentId = new HashMap<>();
        System.out.println(attachments);
        attachments.forEach((attachment) -> {
            i.incrementAndGet();
            message.append(i.get()).append(". ").append(attachment.getName() != null ?
                    attachment.getName() : "Без названия").append("<br>");
            indexForAttachmentId.put(i.get(), attachment.getId());
        });

        messageSenderService.send(user.getVkId(), message.toString());
        userActionMap.put(user.getId(), Action.GET_ATTACHMENT_BY_INDEX);
        lastAttachmentsByOrderMap.put(user.getId(), indexForAttachmentId);
    }

}
