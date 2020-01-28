package myPackage.deserializers;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import myPackage.entities.Attachment;
import myPackage.entities.VkCallback;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

@Component
public class BodyMessageDeserializer extends JsonDeserializer<VkCallback.BodyMessage> {

    @Autowired
    private ObjectMapper mapper;

    @Override
    public VkCallback.BodyMessage deserialize(JsonParser jsonParser, DeserializationContext deserializationContext)
            throws IOException, JsonProcessingException {
        JsonNode node = jsonParser.getCodec().readTree(jsonParser);
        int vkId = node.get("from_id").asInt();
        String text = node.get("text").asText();
        String jsonPayload = node.get("payload") != null ? node.get("payload").asText() : "{}";
        TypeReference<HashMap<String, Object>> typeRef = new TypeReference<HashMap<String, Object>>() {
        };
        Map<String, Object> payload = mapper.readValue(jsonPayload, typeRef);

        VkCallback.BodyMessage bodyMessage = new VkCallback.BodyMessage(vkId, text, payload);

        Iterator<JsonNode> attachmentsIter = node.get("attachments").iterator();
        if (attachmentsIter.hasNext())
            bodyMessage.setAttachment(mapper.readValue(attachmentsIter.next().toString(), Attachment.class));

        return bodyMessage;
    }

}
