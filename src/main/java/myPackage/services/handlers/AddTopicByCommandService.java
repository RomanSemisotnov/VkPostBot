package myPackage.services.handlers;

import myPackage.DAO.AttachmentDao;
import myPackage.DAO.TopicDao;
import myPackage.entities.Topic;
import myPackage.entities.User;
import myPackage.entities.VkCallback;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.regex.Pattern;

@Service
public class AddTopicByCommandService extends BaseHandler {

    @Autowired
    private TopicDao topicDao;

    @Autowired
    private AttachmentDao attachmentDao;

    @Autowired
    private Pattern extraSpacePattern;

    public void add(VkCallback.BodyMessage bodyMessage, User user) {
        System.out.println("Добавление топика через команду");
        String topicName = extraSpacePattern.matcher(bodyMessage.getText()).replaceAll(" ").trim().substring(1).trim();
        Integer topicId = topicDao.save(new Topic(topicName, user.getId()));

        Integer attachmentId = lastIncommingAttachmentMap.remove(user.getId());

        String msg;
        if (attachmentId != null) {
            attachmentDao.update(attachmentId, (criteriaUpdate) -> {
                criteriaUpdate.set("topicId", topicId);
            });

            msg = "Вложение успешно сохраненно.";
        } else {
            msg = "Топик успешно сохранен.";
        }
        messageSenderService.send(user.getVkId(), msg);
        prevUserActionMap.remove(user.getId());
    }

}
