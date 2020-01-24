package myPackage.entities;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "users")
public class User {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "vk_id")
    private int vkId;

    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY)
    private List<IncommingMessage> incommingMessages;

    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY)
    private List<Topic> topics;

    public User() { }

    public User(int vkId) {
        this.vkId = vkId;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getVkId() {
        return vkId;
    }

    public void setVkId(int vkId) {
        this.vkId = vkId;
    }

    public List<IncommingMessage> getIncommingMessages() {
        return incommingMessages;
    }

    public void setIncommingMessages(List<IncommingMessage> incommingMessages) {
        this.incommingMessages = incommingMessages;
    }

    public List<Topic> getTopics() {
        return topics;
    }

    public void setTopics(List<Topic> topics) {
        this.topics = topics;
    }

    @Override
    public String toString() {
        return "User id: " + id + ", vk_di: " + vkId;
    }

}
