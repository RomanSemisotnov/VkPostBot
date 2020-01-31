package myPackage;

import myPackage.entities.VkCallback;
import myPackage.enums.Action;
import myPackage.services.MainMessageHandlerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@RestController
@RequestMapping("/")
public class CallbackHandlerController {

    @Autowired
    private MainMessageHandlerService mainMessageHandlerService;

    @Autowired
    private ConcurrentHashMap<Integer, Action> prevUserActionMap;

    @Autowired
    private ConcurrentHashMap<Integer, Integer> lastIncommingAttachmentMap;

    @Autowired
    private ConcurrentHashMap<Integer, Map<Integer, Integer>> lastAttachmentsByOrderMap;

    @GetMapping
    public void get() {
        System.out.println(prevUserActionMap);
        System.out.println(lastIncommingAttachmentMap);
        System.out.println(lastAttachmentsByOrderMap);
    }

    @PostMapping
    public VkCallback execute(@RequestBody VkCallback callback) {

        if (callback.getType().equals("message_new")) {
            mainMessageHandlerService.handle(callback.getBodyMessage());
        }

        return callback;
    }

}
