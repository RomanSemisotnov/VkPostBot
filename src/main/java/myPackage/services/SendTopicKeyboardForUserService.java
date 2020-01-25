package myPackage.services;

import myPackage.DAO.AttachmentDao;
import myPackage.DAO.UserDao;
import myPackage.entities.Attachment;
import myPackage.entities.Keyboard;
import myPackage.entities.User;
import myPackage.entities.VkCallback;
import myPackage.services.vkMessageSenders.MessageSenderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

@Service
public class SendTopicKeyboardForUserService {

    @Autowired
    private MessageSenderService messageSenderService;

    @Autowired
    private AttachmentDao attachmentDao;

    @Autowired
    private UserDao userDao;

    @Autowired
    private ConcurrentHashMap<Integer, List<Integer>> lastIncommingAttachmentsMap;

    public void send(VkCallback.BodyMessage bodyMessage) {
        System.out.println("обработка attachment");

        User user = userDao.findOrCreateByVkId(bodyMessage.getVkUserId());
        List<Integer> ids = attachmentDao.saveAll(bodyMessage.getAttachments());
        lastIncommingAttachmentsMap.put(user.getId(), ids);

        List<Integer> attachmentIds = bodyMessage.getAttachments().stream()
                .map(Attachment::getId).collect(Collectors.toList());
        Keyboard keyboard = new Keyboard(user.getTopics(), attachmentIds);

        String msg = "Укажите тему, к которой хотите отнести этот пост, " +
                "если такой темы нету, то введите новую тему в сообщении, не используя кнопки.";
        messageSenderService.send(user.getVkId(), msg, keyboard);
    }

}


