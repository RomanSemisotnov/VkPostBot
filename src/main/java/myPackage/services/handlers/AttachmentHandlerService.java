package myPackage.services.handlers;

import myPackage.DAO.AttachmentDao;
import myPackage.entities.Attachment;
import myPackage.entities.User;
import myPackage.entities.VkCallback;
import myPackage.enums.Action;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AttachmentHandlerService extends BaseHandler {

    @Autowired
    private AttachmentDao attachmentDao;

    public void handle(VkCallback.BodyMessage body, User user) {
        System.out.println("Обработка добавления attachment");
        Attachment attachment = body.getAttachment();

        Attachment likeAttachment = attachmentDao.getByTypeAndOwnerIdAndVkId(attachment.getType(), attachment.getOwnerId(), attachment.getVkId());

        if (likeAttachment != null) {
            messageSenderService.send(user.getVkId(), "Такое вложение уже сохраненно!");
            return;
        }

        Integer id = attachmentDao.save(body.getAttachment());
        lastIncommingAttachmentMap.put(user.getId(), id);

        messageSenderService.send(user.getVkId(), "Как вы хотите назвать данное вложение?");
        userActionMap.put(user.getId(), Action.SET_ATTACHMENT_NAME);
    }

}
