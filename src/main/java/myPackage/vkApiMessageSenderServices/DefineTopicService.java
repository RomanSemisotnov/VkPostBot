package myPackage.vkApiMessageSenderServices;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.vk.api.sdk.exceptions.ApiException;
import com.vk.api.sdk.exceptions.ClientException;
import myPackage.entities.Keyboard;
import myPackage.entities.TextButton;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class DefineTopicService extends AbstractMessageService {

    @Override
    public void send(int user_id) {
        List<TextButton> buttons=new ArrayList<>();
        TextButton button1=new TextButton("кнопка 1");
        TextButton button2=new TextButton("кнопка 2");
        TextButton button3=new TextButton("кнопка 3");
        buttons.add(button1);
        buttons.add(button2); // сделать ответ в зависимости от наличных тем у пользователя
        buttons.add(button3);
        Keyboard keyboard=new Keyboard(buttons);
        ObjectMapper mapper = new ObjectMapper();
        String  keyboardJson= null;
        try {
            keyboardJson = mapper.writeValueAsString(keyboard);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            return;
        }
        try {
            vkApiClient.messages().send(myGroupActor).userId(user_id).message("Ответ епты").
                    unsafeParam("keyboard", keyboardJson).execute();
        } catch (ApiException e) {
            e.printStackTrace();
        } catch (ClientException e) {
            e.printStackTrace();
        }
    }

}
