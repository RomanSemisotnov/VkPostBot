package myPackage.factories;

import com.fasterxml.jackson.databind.JsonNode;
import myPackage.entities.VkAttachment;
import myPackage.enums.ALLOWED_ATTACHMENTS;
import org.springframework.stereotype.Component;

import java.util.stream.Collectors;
import java.util.stream.Stream;

@Component
public class VkAttachmentFactory {

    public VkAttachment getInstance(JsonNode node) {
        String type = node.get("type").asText();

        if (!Stream.of(ALLOWED_ATTACHMENTS.values()).map(ALLOWED_ATTACHMENTS::toString)
                .collect(Collectors.toList()).contains(type)) {
            throw new IllegalArgumentException("this attachment type not supported");
        }

        VkAttachment instance = new VkAttachment();
        if (type.equals(ALLOWED_ATTACHMENTS.wall.name())) {
            instance.setId(node.get(type).get("id").asInt());
            instance.setOwner_id(node.get(type).get("from_id").asInt());
            instance.setType(type);
        }

        return instance;
    }

}
