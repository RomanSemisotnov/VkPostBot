package myPackage;

import myPackage.entities.IncommingMessage;
import myPackage.entities.VkCallbackRequest;
import myPackage.services.IncommingMessageService;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/")
public class MessageHandlerController {

    @Autowired
    private IncommingMessageService incommingMessageService;

    @PostMapping
    public VkCallbackRequest execute(@RequestBody VkCallbackRequest vkCallbackRequest) {

        int user_id = vkCallbackRequest.getBodyMessage().getUser_id();
        IncommingMessage lastUserMessage = incommingMessageService.getLastByUserId(user_id);


        return vkCallbackRequest;
    }

}
