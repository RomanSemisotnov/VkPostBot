package myPackage.entities;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;
import java.util.Map;

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

    @JsonIgnoreProperties(ignoreUnknown = true)
    public class BodyMessage {
        @JsonProperty("from_id")
        private int user_id;
        @JsonProperty("text")
        private String text;
        @JsonProperty("attachments")
        private List<Map<String, Object>> attachments;

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

        public List<Map<String, Object>> getAttachments() {
            return attachments;
        }

        public void setAttachments(List<Map<String, Object>> attachments) {
            this.attachments = attachments;
        }

    }

}
