package myPackage.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "topics")
public class Topic {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "name")
    private String name;

    @Column(name = "user_id")
    private int userId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", insertable = false, updatable = false)
    @JsonIgnore
    private User user;

    @OneToMany(mappedBy = "topic", fetch = FetchType.LAZY)
    private List<Attachment> attachments;

    public Topic() {
    }

    public Topic(String name, int userId) {
        this.name = name;
        this.userId = userId;
    }

  /*  @Override
    public boolean equals(Object obj) {
        if (obj == this)
            return true;

        if (!(obj instanceof Topic))
            return false;

        Topic topic = (Topic) obj;
        return name.equals(topic.name)
                && user_id == topic.user_id && id == topic.id;
    }

    @Override
    public int hashCode() {
        return super.hashCode();
    }*/

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

}
