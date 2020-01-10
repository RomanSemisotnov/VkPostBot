package myPackage;

import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/")
public class MessageHandlerController {

    @PostMapping
    public VkCallbackRequest execute(@RequestBody VkCallbackRequest callback) {
        return callback;
    }

}
