package myPackage.services.handlers.keyboard;

import myPackage.DAO.AttachmentDao;
import myPackage.entities.User;
import myPackage.entities.VkCallback;
import myPackage.services.handlers.BaseHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SetReadStatusService extends BaseHandler {

    @Autowired
    private AttachmentDao attachmentDao;

    public void set(VkCallback.BodyMessage body, User user) {
        System.out.println("Обновление статуса о прочтении");

        Integer attachmentId = (Integer) body.getPayload().get("attachment_id");
        String isRead = (String) body.getPayload().get("isRead");

        if (isRead.equals("yes"))
            attachmentDao.update(attachmentId, (criteriaUpdate) -> {
                criteriaUpdate.set("isRead", true);
            });

        messageSenderService.send(user.getVkId(), "Успешно, если хотите прочитать что-то еще, " +
                "введите комманду '/темы'");
        userActionMap.remove(user.getId());
    }


}
