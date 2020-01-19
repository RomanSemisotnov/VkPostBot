package myPackage;

import myPackage.entities.VkCallbackRequest;
import myPackage.services.IncommingMessageHandlerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/")
public class CallbackHandlerController {

    @Autowired
    private IncommingMessageHandlerService incommingMessageHandlerService;

    @PostMapping
    public VkCallbackRequest execute(@RequestBody VkCallbackRequest vkCallbackRequest) {

        if (vkCallbackRequest.getType().equals("message_new")) {
            incommingMessageHandlerService.handle(vkCallbackRequest);
        }

        return vkCallbackRequest;
    }

}
