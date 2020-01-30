package myPackage.services.handlers;

import myPackage.DAO.AttachmentDao;
import myPackage.entities.Attachment;
import myPackage.entities.Keyboard;
import myPackage.entities.User;
import myPackage.entities.VkCallback;
import myPackage.enums.Action;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class GetAttachmentByIndexService extends BaseHandler {

    @Autowired
    private ConcurrentHashMap<Integer, Map<Integer, Integer>> lastAttachmentsByOrderMap;

    @Autowired
    private AttachmentDao attachmentDao;

    public void get(VkCallback.BodyMessage body, User user) {
        System.out.println("Получение вложения по индексу, через текст");

        try {
            Integer index = Integer.parseInt(body.getText().trim());
            Integer attachmentId = lastAttachmentsByOrderMap.get(user.getId()).get(index);

            if (attachmentId == null)
                throw new IndexOutOfBoundsException();

            Attachment attachment = attachmentDao.findOne(attachmentId);
            Keyboard keyboard = new Keyboard(attachmentId);

            messageSenderService.send(user.getVkId(), attachment, keyboard);

            lastAttachmentsByOrderMap.remove(user.getId());
            prevUserActionMap.put(user.getId(), Action.GET_ATTACHMENT_BY_INDEX);
        } catch (NumberFormatException | IndexOutOfBoundsException e) {
            System.out.println(e.getClass().getName());
            messageSenderService.send(user.getVkId(),
                    "Пожалуйста, введите корректный номер вложения");
        }

    }

}
