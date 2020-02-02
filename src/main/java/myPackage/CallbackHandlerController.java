package myPackage;

import myPackage.config.VkConfig;
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
    /*
    @Autowired
    private ConcurrentHashMap<Integer, Action> prevUserActionMap;

    @Autowired
    private ConcurrentHashMap<Integer, Integer> lastIncommingAttachmentMap;

    @Autowired
    private ConcurrentHashMap<Integer, Map<Integer, Integer>> lastAttachmentsByOrderMap;

    @GetMapping("/prevUserActionMap")
    public ConcurrentHashMap<Integer, Action> get1() {
        return prevUserActionMap;
    }

    @GetMapping("/prevUserActionMap")
    public ConcurrentHashMap<Integer, Integer> get2() {
        return lastIncommingAttachmentMap;
    }

    @GetMapping("/prevUserActionMap")
    public ConcurrentHashMap<Integer, Map<Integer, Integer>> get3() {
        return lastAttachmentsByOrderMap;
    }*/

    @Autowired
    private MainMessageHandlerService mainMessageHandlerService;

    @Autowired
    private VkConfig vkConfig;

    @PostMapping
    public String execute(@RequestBody VkCallback callback) throws Exception {

        if (!callback.getSecret().equals(vkConfig.getSecret()))
            throw new Exception();

        if (callback.getType().equals("message_new")) {
            mainMessageHandlerService.handle(callback.getBodyMessage());
        }

        return "ok";
    }

}
