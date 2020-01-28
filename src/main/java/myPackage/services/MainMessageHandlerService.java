package myPackage.services;

import myPackage.DAO.UserDao;
import myPackage.entities.User;
import myPackage.entities.VkCallback;
import myPackage.enums.Action;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.BiConsumer;

@Service
public class MainMessageHandlerService {

    @Autowired
    private UserDao userDao;

    @Autowired
    private DefineActionService defineActionService;

    @Autowired
    private HashMap<Action, BiConsumer<VkCallback.BodyMessage, User>> actionMap;

    @Autowired
    private ConcurrentHashMap<Integer, Action> prevUserActionMap;

    public void handle(VkCallback.BodyMessage bodyMessage) {
        User user = userDao.findOrCreateByVkId(bodyMessage.getVkUserId());
        Action prevAction = prevUserActionMap.get(user.getId());
        Action currentAction = defineActionService.define(bodyMessage, prevAction);
        actionMap.get(currentAction).accept(bodyMessage, user);
    }

}
