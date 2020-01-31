package myPackage.services.handlers.textual;

import myPackage.DAO.AttachmentDao;
import myPackage.entities.Keyboard;
import myPackage.entities.User;
import myPackage.entities.VkCallback;
import myPackage.enums.Action;
import myPackage.services.handlers.BaseHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SetAttachmentNameService extends BaseHandler {

    @Autowired
    private AttachmentDao attachmentDao;

    public void set(VkCallback.BodyMessage body, User user) {
        System.out.println("Устанавливаем имя вложения");

        String topicName = body.getText();
        if (topicName.trim().equals("")) {
            messageSenderService.send(user.getVkId(), "Введите непустое сообщение.");
            return;
        }
        Integer attachmentId = lastIncommingAttachmentMap.get(user.getId());

        attachmentDao.update(attachmentId,
                (criteriaUpdate) -> criteriaUpdate.set("name", topicName));

        Keyboard keyboard = Keyboard.keyboardForSetTopic(user.getTopics());
        String message = "Укажите тему, к которой хотите отнести этот пост, " +
                "если такой темы нету, " +
                "то введите новую тему в сообщении, используя в начале символ '" + Action.ADD_TOPIC_BY_COMMAND.getCommand() +
                "' , например ' + Новая тема '";

        messageSenderService.send(user.getVkId(), message, keyboard);
        userActionMap.put(user.getId(), Action.SET_ATTACHMENT_TOPIC);
    }

}
