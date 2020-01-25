package myPackage.entities;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import myPackage.deserializers.BodyMessageDeserializer;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;

@JsonIgnoreProperties(ignoreUnknown = true)
public class VkCallback {

    @JsonProperty("type")
    private String type;
    @JsonProperty("object")
    private BodyMessage bodyMessage;
    @JsonProperty("secret")
    private String secret;

    public String getType() {
        return type;
    }

    public BodyMessage getBodyMessage() {
        return bodyMessage;
    }

    public String getSecret() {
        return secret;
    }

    public void setType(String type) {
        this.type = type;
    }

    public void setBodyMessage(BodyMessage bodyMessage) {
        this.bodyMessage = bodyMessage;
    }

    public void setSecret(String secret) {
        this.secret = secret;
    }

    @JsonDeserialize(using = BodyMessageDeserializer.class)
    public static class BodyMessage {
        private int vkUserId;
        private String text;
        private Map<String, Object> payload;
        private List<Attachment> attachments = new ArrayList<>();

        public BodyMessage(int vkUserId, String text, Map<String, Object> payload) {
            this.vkUserId = vkUserId;
            this.text = Objects.requireNonNull(text);
            this.payload = Objects.requireNonNull(payload);
        }

        public int getVkUserId() {
            return vkUserId;
        }

        public void setVkUserId(int vkUserId) {
            this.vkUserId = vkUserId;
        }

        public String getText() {
            return text;
        }

        public void setText(String text) {
            this.text = text;
        }

        public List<Attachment> getAttachments() {
            return attachments;
        }

        public void setAttachments(List<Attachment> attachments) {
            this.attachments = attachments;
        }

        public Map<String, Object> getPayload() {
            return payload;
        }

        public void setPayload(Map<String, Object> payload) {
            this.payload = payload;
        }

        @Override
        public String toString() {
            return "MessageBody, user_id: " + vkUserId + ", text: '" + text + "', payload: " + payload + ", attachments: " + attachments;
        }
    }

}
