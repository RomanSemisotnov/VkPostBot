package myPackage.deserializers;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;
import myPackage.entities.Attachment;
import myPackage.enums.AllowedAttachments;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class AttachmentDeserializer extends JsonDeserializer<Attachment> {

    @Override
    public Attachment deserialize(JsonParser jsonParser, DeserializationContext deserializationContext)
            throws IOException, JsonProcessingException {
        JsonNode node = jsonParser.getCodec().readTree(jsonParser);

        Attachment instance = null;
        String type = node.get("type").asText();
        if (type.toUpperCase().equals(AllowedAttachments.WALL.name())) {
            int vkId = node.get(type).get("id").asInt();
            int ownerId = node.get(type).get("from_id").asInt();
            instance = new Attachment(vkId, type, ownerId);
        } else {
            throw new IllegalArgumentException("this attachment type not supported");
        }

        return instance;
    }

}
