package myPackage.services;

import myPackage.entities.VkCallback;
import myPackage.enums.Action;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.function.Consumer;

@Service
public class MainMessageHandlerService {

    @Autowired
    private DefineActionService defineActionService;

    @Autowired
    private HashMap<Action, Consumer<VkCallback.BodyMessage>> actionMap;

    public void handle(VkCallback.BodyMessage bodyMessage) {
        Action action = defineActionService.define(bodyMessage);
        actionMap.get(action).accept(bodyMessage);
    }

}
