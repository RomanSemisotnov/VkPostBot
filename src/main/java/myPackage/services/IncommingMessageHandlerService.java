package myPackage.services;

import myPackage.DAO.AttachmentDao;
import myPackage.DAO.UserDao;
import myPackage.entities.Attachment;
import myPackage.entities.Keyboard;
import myPackage.entities.User;
import myPackage.entities.VkCallbackRequest;
import myPackage.vkApiMessageSenderServices.DefineTopicService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class IncommingMessageHandlerService {

    @Autowired
    private DefineTopicService defineTopicService;

    @Autowired
    private AttachmentDao attachmentDao;

    @Autowired
    private UserDao userDao;


    public void handle(VkCallbackRequest callbackRequest) {
        VkCallbackRequest.BodyMessage callbackBody = callbackRequest.getBodyMessage();
        User user = userDao.findOrCreateByVkId(callbackBody.getVkUserId());
        if (callbackBody.getAttachments().size() != 0) { //  значит это сообщение со вложением, значит мы должны предложить список тем
            System.out.println("обработка attachment");
            callbackBody.getAttachments().forEach(attachmentDao::save);

            List<Integer> attachmentIds = callbackBody.getAttachments().stream()
                    .map(Attachment::getId).collect(Collectors.toList());

            Keyboard keyboard = new Keyboard(user.getTopics(), attachmentIds);
            defineTopicService.send(user.getVkId(), keyboard);
        } else if (callbackBody.getPayload().size() != 0
                && callbackBody.getPayload().containsKey("attachment_ids")
                && callbackBody.getPayload().containsKey("topic_id")) {
            System.out.println("Обработка payload");

            Integer topicId = (Integer) callbackBody.getPayload().get("topic_id");
            List<Integer> attachment_ids = (List<Integer>) callbackBody.getPayload().get("attachment_ids");

            attachmentDao.update(attachment_ids, (criteriaUpdate) -> {
                criteriaUpdate.set("topicId", topicId);
            }); // написать команду +Топик + ConcurrentHashMap
        }

    }
}
