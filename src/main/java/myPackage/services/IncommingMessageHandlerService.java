package myPackage.services;

import myPackage.entities.VkCallbackRequest;
import myPackage.vkApiMessageSenderServices.DefineTopicService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class IncommingMessageHandlerService {

    @Autowired
    private DefineTopicService defineTopicService;

    public void handle(VkCallbackRequest callbackRequest) {
        if (callbackRequest.getBodyMessage().getAttachments().size() != 0) { //  значит это сообщение со вложением, значит мы должны предложить список тем
            defineTopicService.send(callbackRequest.getBodyMessage().getUser_id());
        }
    }

}
