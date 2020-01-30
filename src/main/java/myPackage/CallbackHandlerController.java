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

    /*
    ЗАДАЧИ НА ЗАВТРА : 1) переделать дизайн Keyboard
                       2) Изменить логику с предыдущего действия, из которого мы определяем что нужно делать
                       на действие, которое мы делаем сразу( prevAction to currentAction)
                       3) так же как и в 2 пункет сделать в Keyboard
                       4) доработать прочитанно/непрочитанно + '/темы (прочитать другое)'
                       5) поставить ограничения на в Postgres
                       6) деплой
     */

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
