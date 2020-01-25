package myPackage.services.vkMessageSenders;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.vk.api.sdk.exceptions.ApiException;
import com.vk.api.sdk.exceptions.ClientException;
import com.vk.api.sdk.queries.messages.MessagesSendQuery;
import myPackage.entities.Attachment;
import myPackage.entities.Keyboard;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MessageSenderService extends AbstractMessageService {

    @Autowired
    private ObjectMapper mapper;

    public void send(int vkUserId, String message) {
        send(vkUserId, message, null, null);
    }

    public void send(int vkUserId, String message, Keyboard keyboard) {
        send(vkUserId, message, keyboard, null);
    }

    public void send(int vkUserId, List<Attachment> attachments) {

    }

    public void send(int vkUserId, String message, Keyboard keyboard, List<String> attachments) {
        try {
            MessagesSendQuery query = vkApiClient.messages().send(myGroupActor);
            query.userId(vkUserId);

            if (message != null)
                query.message(message);

            if (attachments != null)
                query.attachment(attachments);

            if (keyboard != null)
                query.unsafeParam("keyboard", mapper.writeValueAsString(keyboard));

            query.execute();
        } catch (ApiException | ClientException | JsonProcessingException e) {
            e.printStackTrace();
        }
    }

}
