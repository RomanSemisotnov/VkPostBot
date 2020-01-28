package myPackage.services.handlers;

import myPackage.DAO.AttachmentDao;
import myPackage.entities.User;
import myPackage.entities.VkCallback;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SetAttachmentTopicByKeyboardService extends BaseHandler {

    @Autowired
    private AttachmentDao attachmentDao;

    public void set(VkCallback.BodyMessage bodyMessage, User user) {
        System.out.println("Обновление клавиатуры с вложений");

        Integer topicId = (Integer) bodyMessage.getPayload().get("topic_id");
        List<Integer> attachmentIds = lastIncommingAttachmentsMap.remove(user.getId());

        int updatedCount = attachmentDao.update(attachmentIds, (criteriaUpdate) -> {
            criteriaUpdate.set("topicId", topicId);
        });

        String msg = updatedCount == 1 ? "Вложение успешно сохраненно." : "Вложения успешно сохраненны.";
        messageSenderService.send(user.getVkId(), msg);
        prevUserActionMap.remove(user.getId());
    }

}
