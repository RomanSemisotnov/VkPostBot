package myPackage.services.handlers;

import myPackage.DAO.AttachmentDao;
import myPackage.entities.Keyboard;
import myPackage.entities.User;
import myPackage.entities.VkCallback;
import myPackage.enums.Action;
import myPackage.enums.Command;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Iterator;
import java.util.List;
import java.util.regex.Pattern;

@Service
public class SetAttachmentNameService extends BaseHandler {

    @Autowired
    private AttachmentDao attachmentDao;

    @Autowired
    private Pattern commaPattern;

    public void set(VkCallback.BodyMessage body, User user) {
        System.out.println("Устанавливаем имя вложения");

        String topicName = body.getText();
        if (topicName.trim().equals("")) {
            messageSenderService.send(user.getVkId(), "Введите непустое сообщение.");
            return;
        }
        List<Integer> attachmentIds = lastIncommingAttachmentsMap.get(user.getId());
        Iterator<Integer> attachmentIdsIter = attachmentIds.iterator();
                   // исправить баг с неправильными наименованиями после запятой
        long commaCount = topicName.chars().filter(num -> num == ',').count();
        if (commaCount > 0) {
            String[] names = commaPattern.split(topicName);
            for (String name : names) {
                if (attachmentIdsIter.hasNext()) {
                    attachmentDao.update(attachmentIdsIter.next(),
                            (criteriaUpdate) -> criteriaUpdate.set("name", name));
                }
            }
        } else {
            attachmentDao.update(attachmentIds.get(0),
                    (criteriaUpdate) -> criteriaUpdate.set("name", topicName));
        }

        Keyboard keyboard = new Keyboard(user.getTopics());
        String message = "Укажите тему, к которой хотите отнести этот пост, " +
                "если такой темы нету, " +
                "то введите новую тему в сообщении, используя в начале символ '" + Command.ADD_TOPIC.getValue() +
                "' , например ' + Новая тема '";
        messageSenderService.send(user.getVkId(), message, keyboard);
        prevUserActionMap.put(user.getId(), Action.SET_ATTACHMENT_NAME);
    }

}
