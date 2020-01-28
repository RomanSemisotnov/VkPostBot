package myPackage.services.handlers;

import myPackage.DAO.AttachmentDao;
import myPackage.entities.User;
import myPackage.entities.VkCallback;
import myPackage.enums.Action;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AttachmentHandlerService extends BaseHandler {

    @Autowired
    private AttachmentDao attachmentDao;

    public void handle(VkCallback.BodyMessage bodyMessage, User user) {
        System.out.println("Обработка добавления attachment");

        Integer id = attachmentDao.save(bodyMessage.getAttachment());
        lastIncommingAttachmentMap.put(user.getId(), id);

        messageSenderService.send(user.getVkId(), "Как вы хотите назвать данное вложение?");
        prevUserActionMap.put(user.getId(), Action.ATTACHMENT_HANDLER);
    }

}
