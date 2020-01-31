package myPackage.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import myPackage.deserializers.AttachmentDeserializer;
import myPackage.enums.AllowedAttachments;

import javax.persistence.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Entity
@Table(name = "attachments")
@JsonDeserialize(using = AttachmentDeserializer.class)
public class Attachment {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column(name = "name")
    private String name;
    @Column(name = "vk_id")
    private int vkId;
    @Column(name = "owner_id")
    private int ownerId;
    @Column(name = "type")
    private String type;
    @Column(name = "topic_id")
    private Integer topicId;
    @Column(name = "isRead")
    private boolean isRead;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "topic_id", insertable = false, updatable = false)
    @JsonIgnore
    private Topic topic;

    public Attachment() {
    }

    public Attachment(Integer id, Integer topicId) {
        this.id = id;
        this.topicId = topicId;
    }

    public Attachment(int vkId, String type, int ownerId) {
        if (!Stream.of(AllowedAttachments.values()).map(AllowedAttachments::toString)
                .collect(Collectors.toList()).contains(type.toUpperCase())) {
            throw new IllegalArgumentException("this attachment type not supported");
        }

        this.vkId = vkId;
        this.type = type;
        this.ownerId = ownerId;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public int getVkId() {
        return vkId;
    }

    public void setVkId(int vkId) {
        this.vkId = vkId;
    }

    public int getOwnerId() {
        return ownerId;
    }

    public void setOwnerId(int ownerId) {
        this.ownerId = ownerId;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        if (!Stream.of(AllowedAttachments.values()).map(AllowedAttachments::toString)
                .collect(Collectors.toList()).contains(type.toUpperCase())) {
            throw new IllegalArgumentException("this attachment type not supported");
        }

        this.type = type;
    }

    public boolean isRead() {
        return isRead;
    }

    public void setRead(boolean read) {
        isRead = read;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getTopicId() {
        return topicId;
    }

    public void setTopicId(Integer topicId) {
        this.topicId = topicId;
    }

    public Topic getTopic() {
        return topic;
    }

    public void setTopic(Topic topic) {
        this.topic = topic;
    }

    @Override
    public String toString() {
        return "Attachment id: " + id + ", isRead: " + isRead + ", type: " + type +
                ", ownerId: " + ownerId + ", vkId: " + vkId + ", topic_id: " + topicId;
    }

}

