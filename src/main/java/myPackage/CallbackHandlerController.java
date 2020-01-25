package myPackage;

import myPackage.entities.VkCallback;
import myPackage.services.MainMessageHandlerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/")
public class CallbackHandlerController {

    @Autowired
    private MainMessageHandlerService mainMessageHandlerService;

    @PostMapping
    public VkCallback execute(@RequestBody VkCallback callback) {

        if (callback.getType().equals("message_new")) {
            mainMessageHandlerService.handle(callback.getBodyMessage());
        }

        return callback;
    }

}
