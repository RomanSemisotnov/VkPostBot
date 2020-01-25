package myPackage.services;

import myPackage.DAO.AttachmentDao;
import myPackage.DAO.TopicDao;
import myPackage.DAO.UserDao;
import myPackage.entities.Topic;
import myPackage.entities.User;
import myPackage.entities.VkCallback;
import myPackage.services.vkMessageSenders.MessageSenderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.regex.Pattern;

@Service
public class AddTopicService {

    @Autowired
    private UserDao userDao;

    @Autowired
    private TopicDao topicDao;

    @Autowired
    private AttachmentDao attachmentDao;

    @Autowired
    private Pattern extraSpacePattern;

    @Autowired
    private ConcurrentHashMap<Integer, List<Integer>> lastIncommingAttachmentsMap;

    @Autowired
    private MessageSenderService messageSenderService;

    public void add(VkCallback.BodyMessage bodyMessage) {
        System.out.println("adding topic");
        String topicName = extraSpacePattern.matcher(bodyMessage.getText()).replaceAll(" ").trim().substring(1).trim();
        User user = userDao.findOrCreateByVkId(bodyMessage.getVkUserId());
        Integer topicId = topicDao.save(new Topic(topicName, user.getId()));

        List<Integer> attachment_ids = lastIncommingAttachmentsMap.remove(user.getId());

        String msg;
        if (attachment_ids != null) {
            int updatedCount = attachmentDao.update(attachment_ids, (criteriaUpdate) -> {
                criteriaUpdate.set("topicId", topicId);
            });

            msg = updatedCount == 1 ? "Вложение успешно сохраненно." : "Вложения успешно сохраненны.";
        } else {
            msg = "Топик успешно сохранен.";
        }
        messageSenderService.send(user.getVkId(), msg);
    }

}
