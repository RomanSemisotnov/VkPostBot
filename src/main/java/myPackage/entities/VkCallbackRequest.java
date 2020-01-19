package myPackage.entities;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import myPackage.deserializers.BodyMessageDeserializer;

import java.util.ArrayList;
import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public class VkCallbackRequest {

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
        private int user_id;
        private String text;

        private List<VkAttachment> attachments = new ArrayList<>();

        public int getUser_id() {
            return user_id;
        }

        public void setUser_id(int user_id) {
            this.user_id = user_id;
        }

        public String getText() {
            return text;
        }

        public void setText(String text) {
            this.text = text;
        }

        public List<VkAttachment> getAttachments() {
            return attachments;
        }

        public void setAttachments(List<VkAttachment> attachments) {
            this.attachments = attachments;
        }

    }

}
