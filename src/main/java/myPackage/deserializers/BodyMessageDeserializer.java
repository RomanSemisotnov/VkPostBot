package myPackage.deserializers;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.ObjectCodec;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;
import myPackage.entities.VkAttachment;
import myPackage.entities.VkCallbackRequest;
import myPackage.factories.VkAttachmentFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Iterator;

@Component
public class BodyMessageDeserializer extends JsonDeserializer<VkCallbackRequest.BodyMessage> {

    @Autowired
    private VkAttachmentFactory vkAttachmentFactory;

    @Override
    public VkCallbackRequest.BodyMessage deserialize(JsonParser jsonParser, DeserializationContext deserializationContext)
            throws IOException, JsonProcessingException {
        ObjectCodec oc = jsonParser.getCodec();
        JsonNode node = oc.readTree(jsonParser);

        int user_id = node.get("from_id").asInt();
        String text = node.get("text").asText();

        VkCallbackRequest.BodyMessage bodyMessage = new VkCallbackRequest.BodyMessage();
        bodyMessage.setText(text);
        bodyMessage.setUser_id(user_id);

        Iterator<JsonNode> attachmentsIter = node.get("attachments").elements();
        while (attachmentsIter.hasNext()) {
            JsonNode current = attachmentsIter.next();
            VkAttachment vkAttachment = vkAttachmentFactory.getInstance(current);
            bodyMessage.getAttachments().add(vkAttachment);
        }

        return bodyMessage;
    }

}
