package myPackage.services.handlers.keyboard;

import myPackage.DAO.AttachmentDao;
import myPackage.entities.User;
import myPackage.entities.VkCallback;
import myPackage.services.handlers.BaseHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SetAttachmentTopicService extends BaseHandler {

    @Autowired
    private AttachmentDao attachmentDao;

    public void set(VkCallback.BodyMessage bodyMessage, User user) {
        System.out.println("Обновление вложений с клавиатуры");

        Integer topicId = (Integer) bodyMessage.getPayload().get("topic_id");
        Integer attachmentId = lastIncommingAttachmentMap.remove(user.getId());

        attachmentDao.update(attachmentId, (criteriaUpdate) -> {
            criteriaUpdate.set("topicId", topicId);
        });

        messageSenderService.send(user.getVkId(), "Вложение успешно сохраненно.");
        userActionMap.remove(user.getId());
    }

}
