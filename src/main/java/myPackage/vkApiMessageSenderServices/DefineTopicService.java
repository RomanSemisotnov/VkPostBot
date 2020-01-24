package myPackage.vkApiMessageSenderServices;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.vk.api.sdk.exceptions.ApiException;
import com.vk.api.sdk.exceptions.ClientException;
import myPackage.entities.Keyboard;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DefineTopicService extends AbstractMessageService {

    @Autowired
    private ObjectMapper mapper;

    public void send(int vkUserId, Keyboard keyboard) {
        String msg = "Укажите тему, к которой хотите отнести этот пост, " +
                "если такой темы нету, то введите новую тему в сообщении, не используя клавиатуру";

        try {
            vkApiClient.messages().send(myGroupActor).userId(vkUserId).message(msg).
                    unsafeParam("keyboard", mapper.writeValueAsString(keyboard)).execute();
        } catch (ApiException | JsonProcessingException | ClientException e) {
            e.printStackTrace();
        }
    }

}
