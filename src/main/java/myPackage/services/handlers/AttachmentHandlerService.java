package myPackage.services.handlers;

import myPackage.DAO.AttachmentDao;
import myPackage.entities.User;
import myPackage.entities.VkCallback;
import myPackage.enums.Action;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AttachmentHandlerService extends BaseHandler {

    @Autowired
    private AttachmentDao attachmentDao;

    public void handle(VkCallback.BodyMessage bodyMessage, User user) {
        System.out.println("Обработка добавления attachment");

        List<Integer> ids = attachmentDao.saveAll(bodyMessage.getAttachments());
        lastIncommingAttachmentsMap.put(user.getId(), ids);

        String message;
        System.out.println(ids.size());
        if (ids.size() > 1) {
            message = "Как вы хотите назвать данные вложения? Напишите имя для каждого через запятую.";
        } else {
            message = "Как вы хотите назвать данное вложение?";
        }
        messageSenderService.send(user.getVkId(), message);
        prevUserActionMap.put(user.getId(), Action.ATTACHMENT_HANDLER);
    }

}
