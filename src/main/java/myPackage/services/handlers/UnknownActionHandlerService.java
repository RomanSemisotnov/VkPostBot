package myPackage.services.handlers;

import myPackage.entities.VkCallback;
import org.springframework.stereotype.Service;

@Service
public class UnknownActionHandlerService {

    public void handle(VkCallback.BodyMessage body) {
        System.out.println("Обработка неизвестного действия");
    }

}
