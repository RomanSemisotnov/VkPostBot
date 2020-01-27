package myPackage.services.handlers;

import myPackage.DAO.AttachmentDao;
import myPackage.DAO.UserDao;
import myPackage.entities.User;
import myPackage.entities.VkCallback;
import myPackage.services.vkMessageSenders.MessageSenderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class UpdateAttachmentTypeByKeyboardService {

    @Autowired
    private AttachmentDao attachmentDao;

    @Autowired
    private UserDao userDao;

    @Autowired
    private MessageSenderService messageSenderService;

    @Autowired
    private ConcurrentHashMap<Integer, List<Integer>> lastIncommingAttachmentsMap;

    public void update(VkCallback.BodyMessage bodyMessage) {
        System.out.println("Обновление клавиатуры с вложений");
        User user = userDao.findOrCreateByVkId(bodyMessage.getVkUserId());

        Integer topicId = (Integer) bodyMessage.getPayload().get("topic_id");
        List<Integer> attachment_ids = (List<Integer>) bodyMessage.getPayload().get("attachment_ids");

        int updatedCount = attachmentDao.update(attachment_ids, (criteriaUpdate) -> {
            criteriaUpdate.set("topicId", topicId);
        });

        lastIncommingAttachmentsMap.remove(user.getId());

        String msg = updatedCount == 1 ? "Вложение успешно сохраненно." : "Вложения успешно сохраненны.";
        messageSenderService.send(user.getVkId(), msg);
    }

}
