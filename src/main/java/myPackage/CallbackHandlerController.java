package myPackage;

import myPackage.config.VkConfig;
import myPackage.entities.VkCallback;
import myPackage.services.MainMessageHandlerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/")
public class CallbackHandlerController {

    @Autowired
    private MainMessageHandlerService mainMessageHandlerService;

    /*  @Autowired
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
  */

    @PostMapping
    public String success() {
        return "877dd7b6";
    }

    @Autowired
    private VkConfig vkConfig;

  /*  @PostMapping
    public String execute(@RequestBody VkCallback callback) throws Exception {

        if (!callback.getSecret().equals(vkConfig.getSecret()))
            throw new Exception();

        if (callback.getType().equals("message_new")) {
            mainMessageHandlerService.handle(callback.getBodyMessage());
        }

        return "ok";
    }*/

}
