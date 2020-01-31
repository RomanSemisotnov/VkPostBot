package myPackage.services.handlers;

import myPackage.entities.User;
import myPackage.entities.VkCallback;
import org.springframework.stereotype.Service;

@Service
public class UnknownActionHandlerService extends BaseHandler {

    public void handle(VkCallback.BodyMessage body, User user) {
        System.out.println("Обработка неизвестного действия");
        messageSenderService.send(user.getVkId(), "Неизвестное действие, " +
                "если вы уверенны, что это ошибка, обратитесь к администратору паблика с ботом");
    }

}
