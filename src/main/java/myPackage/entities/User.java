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

    @OneToMany(mappedBy = "user", fetch = FetchType.EAGER)
    private List<IncommingMessage> incommingMessages;

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

}
